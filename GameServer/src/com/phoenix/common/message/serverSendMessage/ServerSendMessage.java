/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.common.message.serverSendMessage;

/**
 *
 * @author rachel
 */
public interface ServerSendMessage {

    public enum ServerSendMessageType {

        NETWORK_SEND_MESSAGE_ADD_CHANNEL_CONTEXT     // 加入新channel
    }

    public ServerSendMessageType getType();
}
