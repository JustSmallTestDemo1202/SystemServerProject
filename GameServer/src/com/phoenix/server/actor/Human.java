/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.server.actor;

import com.phoenix.common.message.protobufMessage.ProtobufMessage;
import com.phoenix.server.message.messageBuilder.S2CMessageBuilder;
import com.phoenix.server.player.MapPlayer;

/**
 *
 * @author rachel
 */
public class Human {

    public MapPlayer mapPlayer;         // Human角色对应的网络实体
    
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
