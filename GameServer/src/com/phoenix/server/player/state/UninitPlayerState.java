/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.server.player.state;

import com.phoenix.common.message.serverRecvMessage.ServerRecvMessage;
import com.phoenix.server.GameServer;
import com.phoenix.server.player.MapPlayer;
import com.phoenix.server.player.Player;

/**
 *
 * @author rachel
 */
public class UninitPlayerState implements PlayerState {

    public final static UninitPlayerState INSTANCE = new UninitPlayerState();

    private UninitPlayerState() {
    }

    @Override
    public boolean handleMessage(Player player, ServerRecvMessage message) {
        MapPlayer p = (MapPlayer) player;
        int playerId = p.getId();
        ServerRecvMessage.ServerRecvMessageType msgType = message.getType();
        switch (msgType) {
            case MAP_GET_CHAR_DETAIL_INFO_RET: {
                GameServer.INSTANCE.enterGame(p);
                p.state = NormalPlayerState.INSTANCE;
                return true;
            }
            default: {
                // 消息异常（不作处理）,记录日志
                System.err.println("UninitState handle error message type[" + msgType + "].");
                return false;
            }
        }
    }
}
