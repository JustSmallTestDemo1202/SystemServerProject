/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.server.player.state;

import com.phoenix.common.message.serverRecvMessage.ServerRecvMessage;
import com.phoenix.server.player.Player;

/**
 *
 * @author rachel
 */
public interface PlayerState {
    public boolean handleMessage(Player player, ServerRecvMessage message);
}
