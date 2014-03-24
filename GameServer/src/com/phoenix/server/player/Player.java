/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.server.player;

import com.phoenix.common.message.serverRecvMessage.ServerRecvMessage;

/**
 *
 * @author rachel
 */
public interface Player {
    public int getId();
    public long getLastActiveTime();
    public boolean handleMessage(ServerRecvMessage message);
}
