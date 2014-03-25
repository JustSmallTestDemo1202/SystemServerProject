/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.server.actor;

import com.phoenix.common.message.protobufMessage.ProtobufMessage;
import com.phoenix.common.messageQueue.DBMessageQueue;
import com.phoenix.server.message.messageBuilder.S2CMessageBuilder;
import com.phoenix.server.player.MapPlayer;
import com.phoenix.server.timer.FlushDataTimer;
import com.phoenix.server.timer.HumanUpdateTimer;

/**
 *
 * @author rachel
 */
public class Human {
    public final int id;    // 玩家id
    public String name;     // 玩家名字
    public int lv;          // 玩家等级
    public int exp;         // 玩家经验
    
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
    
    // 数据库玩家信息
    public CharDetailInfo buildCharDetailInfo() {
        
    }
    
    public void flushData() {
        // 只有当玩家在线时才需要向数据库同步保存数据
        if (inGame) {
            // 将玩家信息入库
            // DBMessageQueue.queue().offer(DBMessageBuilder.buildSaveCharInfoDBMessage(id, buildCharDetailInfo()));
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
