/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.server.actor;

import com.phoenix.common.message.protobufMessage.ProtobufMessage;
import com.phoenix.common.messageQueue.DBMessageQueue;
import com.phoenix.protobuf.ExternalCommonProtocol.SCEnterGameCharProto;
import com.phoenix.protobuf.InternalCommonProtocol.DBPlayerDetailProto;
import com.phoenix.server.GameServer;
import com.phoenix.server.actor.charInfo.CharDetailInfo;
import com.phoenix.server.message.messageBuilder.DBMessageBuilder;
import com.phoenix.server.message.messageBuilder.S2CMessageBuilder;
import com.phoenix.server.player.MapPlayer;
import com.phoenix.server.timer.FlushDataTimer;
import com.phoenix.server.timer.HumanUpdateTimer;

/**
 *
 * @author rachel
 */
public class Human {
    public final int indexId;           // 玩家角色索引（唯一）
    public final int charId;            // 玩家id    
    public String charName;             // 玩家名
    
    public int charJob;                 // 玩家职业
    public int charGender;              // 玩家性别
    
    public int charLevel;               // 玩家等级 
    public int charExp;                 // 玩家经验
    
    public int charGold;                // 玩家金钱
    public int charDiamond;             // 玩家钻石
    
    public int charEnergy;              // 玩家体力
    

    public MapPlayer mapPlayer;                         // Human角色对应的网络实体

    private FlushDataTimer flushTimer;                  // 数据库刷新计时器
    //public HumanUpdateTimer updateTimer = null;       // 角色场景刷新定时器

    public boolean inGame = false;                      // 角色是否在游戏中
    
    public int ip;                      // 玩家登陆ip
    public long enterTime;              // 最近一次登陆游戏时间
    public long leaveTime;              // 最后一次离开游戏时间
    public long totalOnlineTime;        // 累计游戏时间
    
    
    public void update(int difftime) {
        flushTimer.update(difftime);                    // 数据库更新计时器
    }

    public Human(int charId, CharDetailInfo detailCharInfo) {
        this.indexId = detailCharInfo.indexId;
        this.charId = detailCharInfo.charId;        
        this.charName = detailCharInfo.charName;
        this.charJob = detailCharInfo.charJob;
        this.charGender = detailCharInfo.charGender;
        this.charLevel = detailCharInfo.charLevel;
        this.charExp = detailCharInfo.charExp;
        
        this.leaveTime = detailCharInfo.leaveTime;
        this.totalOnlineTime = detailCharInfo.totalOnlineTime;
    }

    public CharDetailInfo buildDetailCharInfo() {
        CharDetailInfo detailCharInfo = new CharDetailInfo();
        detailCharInfo.indexId = this.indexId;
        detailCharInfo.charId = this.charId;        
        detailCharInfo.charName = this.charName;                // 玩家名
        detailCharInfo.charJob = this.charJob;                  // 玩家职业
        detailCharInfo.charGender = this.charGender;            // 玩家性别
        detailCharInfo.charLevel = this.charLevel;              // 玩家等级 
        detailCharInfo.charExp = this.charExp;                  // 玩家经验

        detailCharInfo.playerDetail = buildDBPlayerDetailProto();
        return detailCharInfo;
    }

    // 数据库玩家信息
    public DBPlayerDetailProto buildDBPlayerDetailProto() {
        DBPlayerDetailProto.Builder builder1 = DBPlayerDetailProto.newBuilder();
        
        return builder1.build();
    }

    public SCEnterGameCharProto buildSCEnterGameCharProto() {
        SCEnterGameCharProto.Builder builder1 = SCEnterGameCharProto.newBuilder();
        builder1.setIndexId(indexId);
        builder1.setCharId(charId);
        builder1.setCharName(charName);
        builder1.setCharJob(charJob);
        builder1.setCharGender(charGender);
        builder1.setCharLevel(charLevel);
        builder1.setCharExp(charExp);
        return builder1.build();
    }

    public void flushData() {
        // 只有当玩家在线时才需要向数据库同步保存数据
        if (inGame) {
            // 将玩家信息入库
            DBMessageQueue.queue().offer(DBMessageBuilder.buildSaveCharInfoDBMessage(this.charId, this.indexId, buildDetailCharInfo()));
        }
    }
    
    /**
     * 与离线上线时间差相关的刷新
     */
    public void timeRefresh() {
        
    }

    public void enterGame() {
        // 返回进入游戏角色信息给客户端        
        //updateTimer = new HumanUpdateTimer(this);
        
        enterTime = GameServer.INSTANCE.getCurrentTime();
        timeRefresh();
        
        sendMessage(S2CMessageBuilder.buildEnterGameRet(buildSCEnterGameCharProto()));
        
        inGame = true;
        ip = mapPlayer.channelContext.getRemoteIP();
        
        flushTimer = new FlushDataTimer(mapPlayer);
    }

    public void leaveGame() {
         if (inGame) {
            leaveTime = GameServer.INSTANCE.getCurrentTime();
            totalOnlineTime += leaveTime - enterTime;
            mapPlayer = null;

            // 玩家数据入库
            flushData();

            inGame = false;

            // 修改简略玩家信息的上线标志
            GameServer.INSTANCE.briefPlayerInfos.setOffGame(charId);            
        } else {
            System.err.println("Player[" + charId + "] multi-logout!");
        }
    }

    public void sendMessage(ProtobufMessage message) {
        if ((mapPlayer != null) && (mapPlayer.channelContext != null)) {
            mapPlayer.channelContext.write(message);
        }
    }
}
