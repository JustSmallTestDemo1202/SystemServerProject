/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.common.message.serverRecvMessage;

import org.jboss.netty.channel.Channel;

/**
 *
 * @author rachel
 */
public class ExternalPlayerMessage extends SimpleServerRecvMessage {

    // The channel which message is received from
    public final Channel channel;

    public ExternalPlayerMessage(ServerRecvMessageType type, Channel channel) {
        super(type);
        this.channel = channel;
    }
}
