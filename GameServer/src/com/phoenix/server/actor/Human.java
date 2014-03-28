/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.server.actor;

import com.phoenix.common.message.protobufMessage.ProtobufMessage;
import com.phoenix.common.messageQueue.DBMessageQueue;
import com.phoenix.protobuf.InternalCommonProtocol.DBPlayerDetailProto;
import com.phoenix.server.actor.charInfo.DetailCharInfo;
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
    
    public final int charId;            // 玩家id
    public String charName;             // 玩家名
    public int charJob;                 // 玩家职业
    public int charGender;              // 玩家性别
    public int charLevel;               // 玩家等级 
    public int charExp;                 // 玩家经验
    
    public MapPlayer mapPlayer;             // Human角色对应的网络实体
    
    private FlushDataTimer flushTimer;      // 数据库刷新计时器
    public HumanUpdateTimer updateTimer = null;   // 角色场景刷新定时器
    
    public boolean inGame = false;          // 角色是否在游戏中
    
    public void update(int difftime) {
        flushTimer.update(difftime);        // 数据库更新计时器
    }
    
    // SC玩家信息
    public PlayerDetailProto buildPlayerDetail(){
        
    }
    
     public DetailCharInfo buildDetailCharInfo() {
        DetailCharInfo detailCharInfo = new DetailCharInfo();
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
    
    public void flushData() {
        // 只有当玩家在线时才需要向数据库同步保存数据
        if (inGame) {
            // 将玩家信息入库
            DBMessageQueue.queue().offer(DBMessageBuilder.buildSaveCharInfoDBMessage(this.charId, buildDetailCharInfo()));
        }
    }
    
    public void enterGame() {
        // 返回进入游戏角色信息给客户端
        //sendMessage(S2CMessageBuilder.buildEnterGameRet(buildEnterGameCharProto()));
    }
    
    public void leaveGame() {
    }
    
    public void sendMessage(ProtobufMessage message) {
        if ((mapPlayer != null) && (mapPlayer.channelContext != null)) {
            mapPlayer.channelContext.write(message);
        }
    }    
}
