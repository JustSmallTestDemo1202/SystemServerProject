/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icee.myth.model.actor;


import com.icee.myth.action.IAction;
import com.icee.myth.config.MiscConfig;
import com.icee.myth.login.UsrPasswordJsonRet;
import com.icee.myth.network.builder.MessageBuilder;
import com.icee.myth.network.encoder.Type2BytesLengthFieldProtobufEncoder;
import com.icee.myth.network.handler.ClientHandler;
import com.icee.myth.network.protobufmessage.ProtobufMessage;
import com.icee.myth.network.upstream.NetReceiver;
import com.icee.myth.utils.RandomGenerator;
import com.phoenix.protobuf.ExternalCommonProtocol.SCEnterGameCharProto;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.frame.LengthFieldBasedFrameDecoder;

/**
 *
 * @author lidonglin
 */
public class Human {

    public String accountName;      //账号信息
    private Channel channel;
    public ClientBootstrap bootstrap;
    public NetReceiver receiver;

    public long nextRoundActionTime;    //执行下次指令的时间
    public long nextRoundStartTime;     //下个指令周期的开始时间
    public int nextActionIndex;         //下个指令执行的命令

    public int id;          // 玩家id
    public String name;     // 玩家名字
    public int lv;          // 玩家等级
    public int exp;         // 玩家经验
    public int rankLv;      // 玩家军衔等级
    public int rankExp;     // 玩家军衔经验（功勋）
    public int gold1;       // 黄金（充值）
    public int gold2;       // 黄金（非充值）
    public long silver;     // 白银
    public int energy;      // 体力
    public int token;       // 军令

    public int stageId;                 // 关卡号
    public int stageType;               // 关卡类型（0普通 1精英 2活动）
    public int currentBattleIndex;      //当前的战斗号

    public boolean inGame;
    private List<IAction> actions;

    public Human(String accountName, String name) {
        this.accountName = accountName;
        this.name = name;
        receiver = new NetReceiver(this);
        actions = new LinkedList<IAction>();
        inGame = false;
    }

    public void init(int passport) {
        nextRoundStartTime = System.currentTimeMillis();
        nextRoundActionTime = System.currentTimeMillis() + RandomGenerator.INSTANCE.generator.nextInt(MiscConfig.INSTANCE.robotIntervalTime * 1000);
        nextActionIndex = 0;

        initBootstrap();
        login(passport);

    }

    public void login(int passport){
        UsrPasswordJsonRet ret = UsrPasswordJsonRet.CheckAccount(passport);
        if (ret != null && ret.result == 0){
            String sessionID = ret.sessionid;
            ChannelFuture connectFuture = null;
            connectFuture = bootstrap.connect(new InetSocketAddress("192.168.1.192", 8001));
            channel = connectFuture.awaitUninterruptibly().getChannel();
            try {
                // send login request
                if(channel != null){
                    sendMessage(MessageBuilder.buildLogin(sessionID));
                    System.out.println(name + " sendMessage Login");
                }
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }
        } else {
            System.out.println("Player Robot " + passport + " Get SessionID Error");
        }
    }

    private void initBootstrap() {
         ChannelFactory factory = new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool(), 1);
        // Create the bootstrap
        bootstrap = new ClientBootstrap(factory);
        ChannelPipeline p = bootstrap.getPipeline();

        p.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 2, 4, 0, 0));
        p.addLast("customEncoder", new Type2BytesLengthFieldProtobufEncoder());

        p.addLast("handler", new ClientHandler(this));

        bootstrap.setOption("tcpNoDelay", true);
        bootstrap.setOption("keepAlive", true);
    }

    public void update(long curTime) {
        if (channel != null) {
            handleMessage();
            if (inGame) {
                resetPlayerAction(curTime);
                for (IAction action : actions) {
                    action.process();
                }
            }
        }
    }

    public void resetPlayerAction(long curTime){
        this.actions.clear();
        /*
        // 如果没有体力了,体力全部补充到上限
        if (this.energy <= 10){
            actions.add(new GMAddEmergyAction(this));
        }

        if (curTime >= nextRoundActionTime){
            nextRoundStartTime += MiscConfig.INSTANCE.robotIntervalTime * 1000;
            nextRoundActionTime = nextRoundStartTime + RandomGenerator.INSTANCE.generator.nextInt(MiscConfig.INSTANCE.robotIntervalTime * 1000);
            if (nextActionIndex == 0){
                //进入场景
                actions.add(new EnterStageAction(this));
            } else if (nextActionIndex == 1){
                //进行第一场战斗
                actions.add(new StartPVEBattleAction(this));
                nextRoundStartTime += 5000;
                nextRoundActionTime += 5000;
            } else if (nextActionIndex == 2){
                //进行第二场战斗
                actions.add(new ContinueStageAction(this));
                actions.add(new StartPVEBattleAction(this));
                nextRoundStartTime += 5000;
                nextRoundActionTime += 5000;
            } else if (nextActionIndex == 3){
                //进行第三场战斗
                actions.add(new ContinueStageAction(this));
                actions.add(new StartPVEBattleAction(this));
                nextRoundStartTime += 5000;
                nextRoundActionTime += 5000;
            } else if (nextActionIndex == 4){
                //离开战斗
                actions.add(new LeaveStageAction(this));
            }            
            nextActionIndex++;
            nextActionIndex = nextActionIndex % 5;
        }
                */
    }

    private void handleMessage() {
        receiver.handleMessage();
    }

    public void leaveGame() {
       
    }

    public void init(SCEnterGameCharProto enterGameCharProto) {
        this.id = enterGameCharProto.getCharId();                  // 玩家id
        this.name = enterGameCharProto.getCharName();               // 玩家名字
        this.lv = enterGameCharProto.getCharLevel();                   // 玩家等级
        this.exp = enterGameCharProto.getCharExp();                 // 玩家经验
        /*
        this.rankLv = enterGameCharProto.getRankLv();           // 玩家军衔等级
        this.rankExp = enterGameCharProto.getRankExp();         // 玩家军衔经验（功勋）
        this.gold1 = enterGameCharProto.getGold1();             // 黄金（充值）
        this.gold2 = enterGameCharProto.getGold2();             // 黄金（非充值）
        this.silver = enterGameCharProto.getSilver();           // 白银
        this.energy = enterGameCharProto.getEnergy();           // 体力
        this.token = enterGameCharProto.getToken();             // 军令
        
        if (enterGameCharProto.hasStageState() == true && enterGameCharProto.getStageState() != null){
            this.currentBattleIndex = enterGameCharProto.getStageState().getCurrentBattleId();
            this.stageId = enterGameCharProto.getStageState().getStageId();
            this.stageType = enterGameCharProto.getStageState().getStageType();
            if (currentBattleIndex == 0) {
                this.nextActionIndex = 1;
            } else if (currentBattleIndex == 1) {
                this.nextActionIndex = 2;
            } else if (currentBattleIndex == 2) {
                this.nextActionIndex = 3;
            }
        } else {
            this.nextActionIndex = 0;
        }
        */

         this.inGame = true;                                     //玩家已经进入游戏
    }

    public void sendMessage(ProtobufMessage message) {
        System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] " + name + " sendMessage "+ message.type);
        channel.write(message);
    }
}
