/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.server.player.state;

import com.phoenix.common.message.serverRecvMessage.ServerRecvMessage;
import com.phoenix.server.GameServer;
import com.phoenix.server.message.messageBuilder.S2CMessageBuilder;
import com.phoenix.server.message.serverRecvMessage.CharNumMessage;
import com.phoenix.server.player.MapPlayer;
import com.phoenix.server.player.Player;

/**
 *
 * @author rachel
 */
public class Login1PlayerState implements PlayerState {

    public final static Login1PlayerState INSTANCE = new Login1PlayerState();

    private Login1PlayerState() {
    }

    @Override
    public boolean handleMessage(Player player, ServerRecvMessage message) {
        // 说明：当遇到不该在此状态处理的消息时，说明玩家状态异常，将玩家状态设置为空，
        // 告诉主线程切断玩家连接并清理玩家上下文
        MapPlayer p = (MapPlayer) player;
        int playerId = p.getIndexId();
        ServerRecvMessage.ServerRecvMessageType msgType = message.getType();
        switch (msgType) {
            case MAP_CHAR_NUM: {
                CharNumMessage charNumMessage = (CharNumMessage) message;

                if (charNumMessage.charNum == 0) {
                    // 未创建角色
                    p.channelContext.write(S2CMessageBuilder.buildLoginRetNoChar());
                } else {
                    p.channelContext.write(S2CMessageBuilder.buildLoginCharList(charNumMessage.charDetail));
                }

                p.state = Login2PlayerState.INSTANCE;

                /*
                 if (charNumMessage.charNum == 0) {
                 // 未创建角色
                 p.channelContext.write(S2CMessageBuilder.buildLoginRetNoChar());
                 p.state = Login2PlayerState.INSTANCE;
                 } else if (charNumMessage.charNum == 1) {
                 // 角色存在
                 if (p.human != null) { // 玩家数据已关联
                 if (p.human.mapPlayer != null) {
                 GameServer.INSTANCE.enterGame(p);
                 p.state = NormalPlayerState.INSTANCE;
                 } else {
                 System.err.println("Player[" + playerId + "] Login1State human.mapPlayer == null error.");
                 }
                 } else { // 玩家数据未关联
                 GameServer.INSTANCE.loadPlayerData(playerId);
                 p.state = UninitPlayerState.INSTANCE;
                 }
                 } else {
                 System.err.println("Player[" + playerId + "] Login1State get char num==" + charNumMessage.charNum + " error.");
                 }
                 */
                return true;
            }
            default: {
                // 暂时不作处理，记录日志
                System.err.println("Player[" + playerId + "] Login2State handle error message type[" + msgType + "].");

                return false;
            }
        }
    }
}
