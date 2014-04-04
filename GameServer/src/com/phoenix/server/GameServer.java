/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.server;

import com.phoenix.common.database.DBThreadHandler;
import com.phoenix.common.message.battleMessage.BattleMessage;
import com.phoenix.common.message.protobufMessage.ProtobufMessage;
import com.phoenix.common.message.serverRecvMessage.ExternalPlayerMessage;
import com.phoenix.common.message.serverRecvMessage.InternalPlayerMessage;
import com.phoenix.common.message.serverRecvMessage.ServerRecvMessage;
import com.phoenix.common.messageQueue.BattleMessageQueueList;
import com.phoenix.common.messageQueue.DBMessageQueue;
import com.phoenix.common.messageQueue.ServerRecvMessageQueue;
import com.phoenix.common.messageQueue.ServerSendMessageQueue;
import com.phoenix.common.network.ClientConnectHandler;
import com.phoenix.common.network.ServerSendThreadHandler;
import com.phoenix.common.network.channel.UninitializeChannel;
import com.phoenix.common.network.listenerServer.ClientConnectServer;
import com.phoenix.common.network.pipilineFactory.CommonToServerPipelineFactory;
import com.phoenix.server.actor.Human;
import com.phoenix.server.battleHandler.BattleThreadHandler;
import com.phoenix.server.message.messageBuilder.DBMessageBuilder;
import com.phoenix.server.message.messageBuilder.S2CMessageBuilder;
import com.phoenix.server.message.serverRecvMessage.GetCharDetailRetMessage;
import com.phoenix.server.message.serverRecvMessage.LoginMessage;
import com.phoenix.server.message.serverSendMessage.AddChannelContextNSTMessage;
import com.phoenix.server.player.MapPlayer;
import com.phoenix.server.player.Player;
import com.phoenix.server.player.PlayerContext;
import com.phoenix.server.player.state.NormalPlayerState;
import com.phoenix.server.player.state.UninitPlayerState;
import com.phoenix.server.social.BriefPlayerInfos;
import com.phoenix.server.timer.HumanUpdateTimer;
import com.phoenix.utils.CommonUtil;
import com.phoenix.utils.Consts;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.handler.execution.ExecutionHandler;
import org.jboss.netty.handler.execution.OrderedMemoryAwareThreadPoolExecutor;

/**
 *
 * @author rachel
 */
public class GameServer implements Runnable {
    //Singleton，没有多线程同步的需要，只是方便全局访问

    public static final GameServer INSTANCE = new GameServer();
    // 全局共享的消息队列
    private final LinkedTransferQueue<ServerRecvMessage> messageQueue = ServerRecvMessageQueue.queue();    
    // uninitializeChannel保存刚建立连接但未开始交换玩家帐号信息的channel，key为channel id
    private final HashMap<Integer, UninitializeChannel> uninitializeChannels = new HashMap<>(); 
    // battlePlayerInfos记录战斗过程中玩家所在战斗线程
    private final HashMap<Integer, Integer> battlePlayerInfos = new HashMap<>();    
    // 战斗线程的消息队列
    private final List<LinkedTransferQueue<BattleMessage>> battleMessageQueueList = BattleMessageQueueList.queueList();
    public int serverId;                // 服务器id
    private int port;                   // Map Server监听的端口
    private String internalHost;        // 内网网卡ip
    public String dbHost;               // 数据库地址
    public String dbName;               // 数据库名
    private String logDBHost;           // 日志数据库IP
    private String logDBName;           // 日志数据库
    private int managerPort;            // Manager端口
    private int rpcPort;                // rpc端口
    public String loginCheckUrl;        // 登陆校验的Manager Server的URL
    private ClientConnectServer clientConnectServer; //Game Server
    private Connection dbConnection;    // 数据库连接
    
    // 数据库线程
    private DBThreadHandler mapDBThreadHandle;
    private Thread dbThread;
    
    // 战斗线程
    private BattleThreadHandler[] mapBattleThreadHandlers;
    private Thread[] battleThreads;
    private int[] battleThreadPlayerNum;            // 战斗线程当前人数
    
    // 网络发包线程
    private ServerSendThreadHandler serverSendThreadHandle;
    private Thread serverSendThread;
    
    // 处理低实时要求任务（如：充值、激活码）的线程池
    private ExecutorService ioExecutorService;
    // 处理高实时要求任务的线程池（如：战斗计算）
    private ExecutorService nioExecutorService;
    
    // 以channelID作为索引玩家ID
    public final ConcurrentHashMap<Integer, Integer> channelID2PlayerIDMap = new ConcurrentHashMap<>();
    
    // players以playerID为索引记录玩家对象（包括正在登录的玩家和已进入游戏的玩家）
    public final HashMap<Integer, MapPlayer> mapPlayers = new HashMap<>();
    
    // playerContexts保存玩家上下文，key为indexId。找不到则创建玩家上下文并异步加载玩家数据
    public final HashMap<Integer, PlayerContext> playerContexts = new HashMap<>();
    
    // 玩家定时器
    public final LinkedList<HumanUpdateTimer> humanUpdateTimers = new LinkedList<>();
    // 简略玩家信息表
    public BriefPlayerInfos briefPlayerInfos; 
    
    // 上次清理玩家数据时间
    private long lastCleanCachePlayerTime;
    // 上次记录玩家在线数的时间
    private long lastLogOnlinePlayerCountTime;
    
    // 服务器停服时间
    private long shutdownTime;
    
    private long realPrevTime;
    // 当前服务器时间
    private volatile long realCurrTime;
    // 主线程Sleep时间
    private int prevSleepTime = 0;
    // 标志服务是否正在关闭
    private volatile boolean isShuttingDown = false;

    public boolean isShuttingDown() {
        return isShuttingDown;
    }
    
    public long getCurrentTime() {
        return realCurrTime;
    }
    
    private String parseArg(String[] args, String param) {
        int argsNum = args.length;
        String retVal = null;
        for (int i = 0; i < argsNum; i++) {
            if (args[i].compareTo(param) == 0) {
                if (i + 1 < argsNum) {
                    retVal = args[i + 1];
                }
                break;
            }
        }

        return retVal;
    }

    private boolean parseArgs(String[] args) {
        // parse server id
        String idString = parseArg(args, "-id");
        if (idString != null) {
            Integer id = Integer.valueOf(idString);
            if (id != null) {
                serverId = id;
            } else {
                System.err.println("Value of \"-id\" param must be integer.");
                return false;
            }
        } else {
            System.err.println("Lack \"-id XXX\" param");
            return false;
        }

        // parse client port
        String portString = parseArg(args, "-port");
        if (portString != null) {
            Integer portInteger = Integer.valueOf(portString);
            if (portInteger != null) {
                port = portInteger;
            } else {
                System.err.println("Value of \"-port\" param must be integer.");
                return false;
            }
        } else {
            System.err.println("Lack \"-port XXX\" param");
            return false;
        }

        // parse internal host
        String internalHostString = parseArg(args, "-internalHost");
        if (internalHostString != null) {
            if ((internalHostString.toLowerCase().compareTo("localhost") == 0) || CommonUtil.ValidateIPAddress(internalHostString)) {
                internalHost = internalHostString;
            } else {
                System.err.println("Value of \"-internalHost\" param must be a ip of this machine to the internal network.");
                return false;
            }
        } else {
            System.err.println("Lack \"-internalHost XXX\" param");
            return false;
        }

        // parse private host
        String dbHostString = parseArg(args, "-dbHost");
        if (dbHostString != null) {
            if (dbHostString.compareTo("localhost") == 0 || CommonUtil.ValidateIPAddress(dbHostString)) {
                dbHost = dbHostString;
            } else {
                System.err.println("Value of \"-dbHost\" param must be a ip address.");
                return false;
            }
        } else {
            System.err.println("Lack \"-dbHost XXX\" param");
            return false;
        }

        // parse private host
        dbName = parseArg(args, "-dbName");
        if (dbName == null) {
            System.err.println("Lack \"-dbName XXX\" param");
            return false;
        }

        logDBHost = parseArg(args, "-logDBHost");
        if (logDBHost == null) {
            System.err.println("Lack \"-logDBHost XXX\" param");
            return false;
        }

        logDBName = parseArg(args, "-logDBName");
        if (logDBName == null) {
            System.err.println("Lack \"-logDBName XXX\" param");
            return false;
        }

        // parse manager port
        String managerPortString = parseArg(args, "-managerPort");
        if (managerPortString != null) {
            Integer managerPortInteger = Integer.valueOf(managerPortString);
            if (managerPortInteger != null) {
                managerPort = managerPortInteger;
            } else {
                System.err.println("Value of \"-managerPort\" param must be integer.");
                return false;
            }
        } else {
            System.err.println("Lack \"-managerPort XXX\" param");
            return false;
        }

        // parse rpc port
        String rpcPortString = parseArg(args, "-rpcPort");
        if (rpcPortString != null) {
            Integer rpcPortInteger = Integer.valueOf(rpcPortString);
            if (rpcPortInteger != null) {
                rpcPort = rpcPortInteger;
            } else {
                System.err.println("Value of \"-rpcPort\" param must be integer.");
                return false;
            }
        } else {
            System.err.println("Lack \"-rpcPort XXX\" param");
            return false;
        }

        loginCheckUrl = parseArg(args, "-loginCheckUrl");
        if (loginCheckUrl == null) {
            System.err.println("Lack \"-loginCheckUrl XXX\" param");
            return false;
        }
        return true;
    }

    public boolean connectToDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            dbConnection = DriverManager.getConnection("jdbc:mysql://" + dbHost + "/" + dbName + "?useUnicode=true&characterEncoding=UTF-8", "phoenixtest", "acY5qmGKVcRs4nST");
            return true;
        } catch (SQLException ex) {
            //Logger.getLogger(ProjectCardServer.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("connectToDB Error: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.err.println("connectToDB Error: " + ex.getMessage());
        }

        return false;
    }

    public void loadResources() {
        
    }
    
    public void init() {
        
    }    
    
    public void start() {
        // 启动数据库处理线程
        this.mapDBThreadHandle = new DBThreadHandler(dbConnection);
        this.dbThread = new Thread(mapDBThreadHandle, "DBThread");
        this.dbThread.start();

        // 启动战斗处理线程
        this.mapBattleThreadHandlers = new BattleThreadHandler[Consts.THREAD_BATTLE_THREAD_NUM];
        this.battleThreads = new Thread[Consts.THREAD_BATTLE_THREAD_NUM];
        this.battleThreadPlayerNum = new int[Consts.THREAD_BATTLE_THREAD_NUM];
        for (int i = 0; i < Consts.THREAD_BATTLE_THREAD_NUM; i++) {            
            this.battleMessageQueueList.add(new LinkedTransferQueue<BattleMessage>());
            this.battleThreadPlayerNum[i] = 0;
            this.mapBattleThreadHandlers[i] = new BattleThreadHandler(i);
            this.battleThreads[i] = new Thread(mapBattleThreadHandlers[i], "BattleThread" + i);
            this.battleThreads[i].start();
        }
        
        // 启动发包线程
        this.serverSendThreadHandle = new ServerSendThreadHandler();
        this.serverSendThread = new Thread(this.serverSendThreadHandle, "ServerSendThread");
        this.serverSendThread.start();

        // 线程池（用于处理战斗计算、充值、激活码的事务）
        this.ioExecutorService = Executors.newFixedThreadPool(Consts.THREAD_SERVER_THREAD_NUM);
        this.nioExecutorService = Executors.newFixedThreadPool(Consts.THREAD_SERVER_THREAD_NUM);

        // 启动GameServer对Client的监听服务
        // 200 threads max, Memory limitation: 1MB by channel, 1GB global, 1000 ms of timeout 参考http://www.cnblogs.com/baronzhao/p/netty_2.html
        ExecutionHandler executionHandler = new ExecutionHandler(new OrderedMemoryAwareThreadPoolExecutor(500, 1048576, 1073741824, 1000, TimeUnit.MILLISECONDS, Executors.defaultThreadFactory()));
        ClientConnectHandler clientConnectHandler = new ClientConnectHandler();
        this.clientConnectServer = ClientConnectServer.INSTANCE;
        this.clientConnectServer.init(this.port, new CommonToServerPipelineFactory(executionHandler, clientConnectHandler));
        this.clientConnectServer.startServer();
        
        // 启动主线程
        new Thread(this, "GameServerThread").start();
    }
    
    
    
    // 全服广播
    public void broadcast(ProtobufMessage message) {
        for (MapPlayer mapPlayer : mapPlayers.values()) {
            if (mapPlayer.state == NormalPlayerState.INSTANCE) {
                mapPlayer.channelContext.write(message);
            }
        }
    }
    
    // 对指定玩家发送消息
    public void broadcast(List<Integer> charIds, ProtobufMessage message, boolean needOffLineSend) {
        for (int charId : charIds) {
            MapPlayer mapPlayer = mapPlayers.get(charId);
            if (mapPlayer != null && mapPlayer.state == NormalPlayerState.INSTANCE) {
                mapPlayer.channelContext.write(message);
            } else if (needOffLineSend) {
                //离线消息
            }
        }
    }
    
    public void enterGame(MapPlayer player) {
        Human human = player.human;
        assert (human != null);

        human.enterGame();
        humanUpdateTimers.add(new HumanUpdateTimer(human));
    }
    
    public PlayerContext loadPlayerData(int playerId, int indexId, boolean loginUser) {
        // 载入玩家数据
        PlayerContext playerContext = playerContexts.get(indexId);

        if (playerContext == null) {
            playerContext = new PlayerContext();
            playerContext.lastVisitTime = getCurrentTime();
            if (loginUser == true) {
                playerContext.player = mapPlayers.get(playerId);
            } else {
                playerContext.player = null;                        
            }

            playerContexts.put(indexId, playerContext);
            // 通知DB线程加载玩家数据
            DBMessageQueue.queue().offer(DBMessageBuilder.buildGetCharDetailDBMessage(playerId, indexId));
        } else if (playerContext.human != null) {
            System.err.println("load player[" + playerId + "] data fail because already loaded.");
        }

        return playerContext;
    }
    
    public void removePlayer(MapPlayer player) {
        assert (player != null);

        Human human = player.human;
        if (human != null) {
            human.leaveGame();           
        }

        PlayerContext playerContext = playerContexts.get(player.getIndexId());
        if (playerContext != null) {
            playerContext.lastVisitTime = getCurrentTime();
            playerContext.player = null;
        }

        // 注意：关闭channel会触发产生CLIENTCLOSE消息处理
        player.channelContext.close();
    }    
    
    private void shutdown() {
        // 停服处理
        // 将在线玩家踢下线，并在关服过程中不接受新登陆需求
        for (MapPlayer player : mapPlayers.values()) {
            removePlayer(player);
        }

        isShuttingDown = true;
        shutdownTime = getCurrentTime() + 10 * Consts.MILSECOND_1SECOND;

        System.out.println("Server[" + serverId + "] is shutting down.");
    }
    
    private void handleMessages() {
        ServerRecvMessage msg = messageQueue.poll();
        while (msg != null) {
            switch (msg.getType()) {
                case MAP_SVR_SHUTDOWN: {
                    shutdown();
                    break;
                }
                case MAP_CLIENT_CONNECT: {
                    Channel clientChannel = ((ExternalPlayerMessage) msg).channel;
                    uninitializeChannels.put(clientChannel.getId(), new UninitializeChannel(clientChannel));
                    break;
                }
                case MAP_CLIENT_CLOSE: {
                    // TODO: 区分是网络断线还是被服务器踢出，若是断线则启动重连等待计时逻辑
                    // 玩家连接断开处理
                    Channel clientChannel = ((ExternalPlayerMessage) msg).channel;
                    Integer channelId = clientChannel.getId();
                    UninitializeChannel uninitializeChannel = uninitializeChannels.remove(channelId);
                    if (uninitializeChannel == null) {
                        // 找到玩家状态机上下文，并让玩家离线
                        Integer indexId = channelID2PlayerIDMap.remove(channelId);
                        if (indexId != null) {
                            // 注意：players.remove只此一处，因此可以在此处实现重复登陆可以在踢出前一登陆后进入游戏的逻辑
                            MapPlayer mapPlayer = mapPlayers.remove(indexId);
                            if (mapPlayer != null) {
                                removePlayer(mapPlayer);

                                // 为了实现后登陆踢出前登陆进入游戏，此处重新将mapPlayer对象中记录的消息放入消息队列
                                if (mapPlayer.loginMessage != null) {
                                    ServerRecvMessageQueue.queue().offer(mapPlayer.loginMessage);
                                    mapPlayer.loginMessage = null;
                                }
                            }
                        }
                    }
                    break;                   
                }
                case MAP_LOGIN: {
                    LoginMessage loginMsg = (LoginMessage) msg;
                    Channel clientChannel = loginMsg.channel;
                    int channelId = clientChannel.getId();
                    int playerId = loginMsg.playerId;
                    // 防止玩家登陆时断线，使上线玩家是个无连接玩家并僵死在线上，并导致玩家始终无法登陆
                    if (clientChannel.isOpen()) {
                        // 如果已经存在玩家对象该如何处理？踢出游戏中玩家并断开正在登录的连接
                        MapPlayer mapPlayer = mapPlayers.get(playerId);
                        if (mapPlayer != null) {
                            // TODO: 若要实现后登陆踢出前登陆进入游戏，可将此处实现改为不断开后登陆连接，将后登陆消息记录在player中，待处理ALL_CLIENTCLOSE消息时重新将后登陆消息放入消息队列
                            // 注意：当有多重登陆时只有最后登陆能进入游戏，中间登陆连接都要断开并删除相关对象                            
                            if (mapPlayer.loginMessage != null) {
                                Channel channel = mapPlayer.loginMessage.channel;
                                uninitializeChannels.remove(channel.getId());
                                channel.close();
                            }
                            mapPlayer.loginMessage = loginMsg;
                            removePlayer(mapPlayer);
                            System.err.println("Player[" + playerId + "] multilogin -- channel id:" + channelId);
                        } else if (isShuttingDown) {
                            // 正在关服，拒绝登陆
                            uninitializeChannels.remove(channelId);
                            clientChannel.close();
                        } else {
                            // 创建Player上下文
                            // 已经将Player初始化为Login1State
                            mapPlayer = new MapPlayer(playerId, loginMsg.passport, loginMsg.auth, loginMsg.privilege, loginMsg.endForbidTalkTime, clientChannel);
                            
                            /*
                            PlayerContext playerContext = playerContexts.get(playerId);
                            if (playerContext != null) {
                                assert (playerContext.player == null);
                                playerContext.lastVisitTime = getCurrentTime();

                                if (playerContext.human != null) {
                                    mapPlayer.human = playerContext.human;
                                    mapPlayer.human.mapPlayer = mapPlayer;
                                }

                                playerContext.player = mapPlayer;
                            }
                            */
                            
                            mapPlayers.put(playerId, mapPlayer);
                            channelID2PlayerIDMap.put(channelId, playerId);

                            uninitializeChannels.remove(channelId);

                            // 将Player的channelContext加入网络发送线程
                            ServerSendMessageQueue.queue().add(new AddChannelContextNSTMessage(mapPlayer.channelContext));

                            // 向DBApp获取玩家基本角色信息
                            DBMessageQueue.queue().offer(DBMessageBuilder.buildGetCharNumDBMessage(playerId));
                        }
                    } else {
                        System.err.println("Player[" + playerId + "] can't login because channel is closed.");
                    }
                    break;
                }
                case MAP_GET_CHAR_DETAIL_INFO_RET: {
                    GetCharDetailRetMessage getCharDetailRetMessage = (GetCharDetailRetMessage) msg;

                    PlayerContext playerContext = playerContexts.get(getCharDetailRetMessage.indexId);
                    if (playerContext != null) {
                        if (playerContext.human == null) {
                            playerContext.lastVisitTime = getCurrentTime();
                            Human human = new Human(getCharDetailRetMessage.playerId, getCharDetailRetMessage.charDetailInfo);

                            playerContext.human = human;

                            if (playerContext.player != null) {
                                assert (playerContext.player.human == null);
                                human.mapPlayer = playerContext.player;
                                playerContext.player.human = human;
                                if (playerContext.player.state == UninitPlayerState.INSTANCE) {
                                    playerContext.player.handleMessage(msg);
                                }
                            }

                            // 将等待该角色信息加载的竞技消息重新加到消息队列
                            if (playerContext.waitingMessages != null) {
                                for (ServerRecvMessage message : playerContext.waitingMessages) {
                                    ServerRecvMessageQueue.queue().offer(message);
                                }
                                playerContext.waitingMessages = null;
                            }
                        } else {
                            System.out.println("Human object has already existed when get char detail return.");
                        }
                    } else {
                        System.err.println("Can't get player context when get char detail return.");
                    }
                    break;
                }
                default: {
                    // 处理玩家相关消息，转至玩家的handleMessage处理
                    int playerId = ((InternalPlayerMessage) msg).playerId;
                    Player player = mapPlayers.get(playerId);

                    if (player != null) {
                        player.handleMessage(msg);
                    } else {
                        System.err.println("Can't find player[" + playerId + "] to handle message[" + msg.getType() + "].");
                    }
                    break;
                }                
            }
            msg = messageQueue.poll();
        }
    }    
    
    // 定期清理PlayerContext数据     
    private void cleanPlayer() {
        if (realCurrTime - lastCleanCachePlayerTime >= Consts.CLEAR_OFFLINE_PLAYER_TIME) {
            lastCleanCachePlayerTime = realCurrTime;
            for (Iterator<PlayerContext> it = playerContexts.values().iterator(); it.hasNext();) {
                PlayerContext playerContext = it.next();

                if ((playerContext.player == null) && (realCurrTime - playerContext.lastVisitTime >= Consts.CLEAR_OFFLINE_PLAYER_TIME)) {
                    if (playerContext.human != null) {
                        playerContext.human = null;
                    } else {
                        // 不可能出现playerContext.player和playerContext.human同时为null的情况
                        System.err.println("Clean player context error.");
                    }
                    it.remove();
                }
            }
        }
    }
    
    // 定期记录当前在线玩家数据
    private void logOnlinePlayerCount() {
        if (realCurrTime - lastLogOnlinePlayerCountTime >= Consts.LOG_ONLINE_PLAYER_COUNT_TIME) {
            lastLogOnlinePlayerCountTime = realCurrTime;
            // 将玩家数目计入数据库
            // GameLogger.getlogger().log(GameLogMessageBuilder.buildDBOnlineNumGameLogMessage(players.size()));
        }
    }

    // 关闭DB线程
    public void shutdownDBThread() {
        DBMessageQueue.queue().offer(DBMessageBuilder.buildShutdownDBMessage());
    }
    
    private void updateHumans(int difftime) {
        for (Iterator<HumanUpdateTimer> iterator = humanUpdateTimers.iterator(); iterator.hasNext();) {
            HumanUpdateTimer humanUpdateTimer = iterator.next();
            // 当玩家下线后，该update被移除
            if (!humanUpdateTimer.update(difftime)) {
                iterator.remove();
            }
        }
    }
    
    
    @Override
    public void run() {
        while (true) {
            try {
                realCurrTime = System.currentTimeMillis();
                int diffTime = (int) (realCurrTime - realPrevTime);

                if (diffTime < 0) {
                    System.err.println( "MainThread: Difftime=" + diffTime + " Error!");
                }
                
                 if (isShuttingDown && (mapPlayers.isEmpty() || (realCurrTime > shutdownTime))) {
                    break;
                }
                
                 // 更新玩家
                updateHumans(diffTime);

                // 定期清理离线玩家（一小时执行一次清理，离线超过一小时的玩家会被从内存删除）
                cleanPlayer();

                // 定期记录在线玩家数目
                logOnlinePlayerCount();
                
                
                // 心跳 - 广播到客户端，客户端进行校时
                if (realCurrTime / Consts.MILSECOND_1MINITE > realPrevTime / Consts.MILSECOND_1MINITE) {
                    broadcast(S2CMessageBuilder.buildRealTime(realCurrTime));
                }
                
                handleMessages();
                
                realPrevTime = realCurrTime;
                
                // 两次执行的时间差（D0) = TICK的执行时间(t0) + Sleep的时间(d0)
                // 下一次的Sleep时间(d1) + TICK的执行时间(t1) = 休眠时间常量(WORLD_SLEEP_CONST)
                // 无法获得下一次的TICK的执行时间，使用当次TICK的执行时间(t0) + 下次Sleep的时间(d1) = 休眠时间常量(WORLD_SLEEP_CONST) 满足需求
                // 即d1 = WORLD_SLEEP_CONST - t0 = WORLD_SLEEP_CONST - (D0 - d0) = WORLD_SLEEP_CONST + d0 - D0
                if (diffTime <= Consts.GAME_SLEEP_CONST + prevSleepTime) {
                    prevSleepTime = Consts.GAME_SLEEP_CONST - (diffTime - prevSleepTime);
                } else {
                    prevSleepTime = 10;
                    System.out.println("MainThread: difftime=" + diffTime);
                }
                try {
                    Thread.sleep(prevSleepTime);
                } catch (InterruptedException ex) {
                    System.err.println("MainThread Sleep Error: " + ex.getMessage());
                }
                
            } catch (Exception ex) {
                System.err.println("MainThread Error: " + ex.getMessage());
            }
        }
        
        // 将所有在线玩家数据存盘
        for (MapPlayer player : mapPlayers.values()) {
            if (player.human != null) {
                player.human.flushData();
            }
        }       

        // 通知数据库线程在完成之前所有数据库任务后结束
        shutdownDBThread();

        // 等待数据库线程结束
        try {
            dbThread.join(0);

            Thread.sleep(1000); // 睡眠一秒等待日志结束
        } catch (InterruptedException ex) {
            Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
        }

        // 关闭日志线程
        //GameLogger.getlogger().shutdown();

        System.exit(0);        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String param = "";
        for (String arg : args) {
            param += arg + " ";
        }
        System.out.println("Server starting with param : " + param);
        try {
            if (!GameServer.INSTANCE.parseArgs(args)) {
                return;
            }

            if (!GameServer.INSTANCE.connectToDB()) {
                System.err.println("Can't connect to database[" + GameServer.INSTANCE.dbHost + ":" + GameServer.INSTANCE.dbName + "].");
                return;
            }

            GameServer.INSTANCE.loadResources();

            GameServer.INSTANCE.init();
            GameServer.INSTANCE.start();

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }   
}
