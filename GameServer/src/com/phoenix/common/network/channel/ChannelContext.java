/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.common.network.channel;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.concurrent.LinkedTransferQueue;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;

/**
 *
 * @author rachel
 */
public class ChannelContext {

    private static final int MAX_FLUSH_MESSAGE_NUM_PER_WRITING = 1024;
    protected final LinkedTransferQueue<Object> messageQueue = new LinkedTransferQueue<Object>();
    protected Channel channel = null;  // channel to server
    protected boolean isActive = false;

    public ChannelContext() {
    }

    public void setChannel(Channel channel) {
        if (channel != null) {
            this.channel = channel;
            isActive = true;
        }
    }

    public boolean isActive() {
        return isActive;
    }

    public final void write(Object message) {
        if (isActive) {
            messageQueue.add(message);
        }
    }

    public void flush() {
        if (isActive) {
            Object msg = messageQueue.poll();
            if (msg != null) {
                boolean finish = false;
                while (!finish) {
                    ArrayList<Object> messages = new ArrayList<Object>(MAX_FLUSH_MESSAGE_NUM_PER_WRITING);
                    while (msg != null && messages.size() < MAX_FLUSH_MESSAGE_NUM_PER_WRITING) {
                        messages.add(msg);
                        msg = messageQueue.poll();
                    }
                    finish = messages.size() < MAX_FLUSH_MESSAGE_NUM_PER_WRITING;
                    channel.write(messages);
                }
            }
        }
    }

    public ChannelFuture fastWrite(Object message) {
        if (isActive) {
            return channel.write(message);
        } else {
            return null;
        }
    }

    public ChannelFuture close() {
        if (isActive) {
            isActive = false;
            messageQueue.clear();
            return channel.close();
        } else {
            return null;
        }
    }

    public boolean isChannel(Channel channel) {
        return this.channel == channel;
    }

    public int getId() {
        if (isActive) {
            return channel.getId();
        } else {
            return -1;
        }
    }

    public int getRemoteIP() {
        InetSocketAddress saddr = (InetSocketAddress) channel.getRemoteAddress();
        InetAddress inetAddress = saddr.getAddress();
        byte[] bs = inetAddress.getAddress();
        int ip = 0;
        if (bs.length == 4) {
            ip = (bs[0] & 0xFF) << 24 | (bs[1] & 0xFF) << 16 | (bs[2] & 0xFF) << 8 | (bs[3] & 0xFF);
        }
        return ip;
    }
}
