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
public class DummyPlayerState implements PlayerState {

    public final static DummyPlayerState INSTANCE = new DummyPlayerState();

    private DummyPlayerState() {
    }

    @Override
    public boolean handleMessage(Player player, ServerRecvMessage message) {
        return false;
    }
}
