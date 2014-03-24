/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.server.player.state;

import com.phoenix.common.message.serverRecvMessage.ServerRecvMessage;
import com.phoenix.server.player.MapPlayer;
import com.phoenix.server.player.Player;

/**
 *
 * @author rachel
 */
public class CreatingCharPlayerState implements PlayerState {

    public final static CreatingCharPlayerState INSTANCE = new CreatingCharPlayerState();

    private CreatingCharPlayerState() {
    }

    @Override
    public boolean handleMessage(Player player, ServerRecvMessage message) {
        MapPlayer p = (MapPlayer) player;
        int playerId = p.getId();
        MessageType msgType = message.getType();
        switch (msgType) {
            case MAP_CREATE_CHAR_RET: {
                CreateCharRetMessage createCharRetMsg = (CreateCharRetMessage) message;

                if (createCharRetMsg.result == 1) {
                    ProjectCardServer.INSTANCE.loadPlayerData(playerId);
                    p.state = UninitPlayerState.INSTANCE;
                } else {
                    // 通知玩家无法创建角色（重名）
                    p.channelContext.write(ServerToClientMessageBuilder.buildCreateCharError());
                    p.state = Login2PlayerState.INSTANCE;
                }

                return true;
            }
            default: {
                // 不作处理，记录日志
                System.err.println("Player[" + playerId + "] CreatingCharState handle error message type[" + msgType + "].");

                return false;
            }
        }
    }
}
