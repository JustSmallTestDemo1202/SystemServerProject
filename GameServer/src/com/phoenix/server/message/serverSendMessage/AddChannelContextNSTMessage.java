/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.server.message.serverSendMessage;

import com.phoenix.common.message.serverSendMessage.SimpleServerSendMessage;
import com.phoenix.common.network.channel.ChannelContext;

/**
 * 将Client连接成功的Channel加入到发包List中
 * @author rachel
 */
public class AddChannelContextNSTMessage extends SimpleServerSendMessage {

    public final ChannelContext channelContext;

    public AddChannelContextNSTMessage(ChannelContext channelContext) {
        super(ServerSendMessageType.NETWORK_SEND_MESSAGE_ADD_CHANNEL_CONTEXT);

        this.channelContext = channelContext;
    }
}
