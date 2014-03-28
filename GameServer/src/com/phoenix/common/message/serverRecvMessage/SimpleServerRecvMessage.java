/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.common.message.serverRecvMessage;

/**
 *
 * @author rachel
 */
public class SimpleServerRecvMessage implements ServerRecvMessage {

    public final ServerRecvMessageType type; // The type of receive message

    public SimpleServerRecvMessage(ServerRecvMessageType type) {
        this.type = type;
    }

    @Override
    public final ServerRecvMessageType getType() {
        return type;
    }
}
