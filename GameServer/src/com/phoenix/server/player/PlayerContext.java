/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.server.player;

import com.phoenix.common.message.serverRecvMessage.ServerRecvMessage;
import com.phoenix.server.actor.Human;
import java.util.LinkedList;

/**
 *
 * @author rachel
 */
public class PlayerContext {

    public long lastVisitTime;
    public MapPlayer player;
    public Human human;
    public LinkedList<ServerRecvMessage> waitingMessages;

    public PlayerContext() {
    }

    public void addWaitingMessage(ServerRecvMessage message) {
        if (waitingMessages == null) {
            waitingMessages = new LinkedList<>();
        }

        waitingMessages.add(message);
    }
}
