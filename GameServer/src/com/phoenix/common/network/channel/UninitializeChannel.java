/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.common.network.channel;

import com.phoenix.server.GameServer;
import org.jboss.netty.channel.Channel;

/**
 *
 * @author rachel
 */
public class UninitializeChannel {
    public long createTime;
    public Channel channel;

    public UninitializeChannel(Channel channel) {
        this.channel = channel;
        createTime = GameServer.INSTANCE.getCurrentTime();
    }
}
