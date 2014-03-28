/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.common.message.serverRecvMessage;

/**
 *
 * @author rachel
 */
public class InternalPlayerMessage extends SimpleServerRecvMessage {

    public final int playerId;  // The player's id

    public InternalPlayerMessage(ServerRecvMessageType type, int playerId) {
        super(type);
        this.playerId = playerId;
    }
}
