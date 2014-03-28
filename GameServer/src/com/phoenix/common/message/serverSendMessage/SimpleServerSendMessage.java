/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.common.message.serverSendMessage;

/**
 *
 * @author rachel
 */
public class SimpleServerSendMessage implements ServerSendMessage {

    private final ServerSendMessageType type;

    public SimpleServerSendMessage(ServerSendMessageType type) {
        this.type = type;
    }

    @Override
    public ServerSendMessageType getType() {
        return type;
    }
}
