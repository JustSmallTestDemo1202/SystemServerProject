/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.common.network;

import com.phoenix.common.message.serverSendMessage.ServerSendMessage;
import com.phoenix.common.messageQueue.ServerSendMessageQueue;
import java.util.LinkedList;
import java.util.concurrent.LinkedTransferQueue;

/**
 *
 * @author rachel
 */
public class ServerSendThreadHandler implements Runnable {
    protected final LinkedTransferQueue<ServerSendMessage> messageQueue = ServerSendMessageQueue.queue();
    public final LinkedList<ChannelContext> channelContexts = new LinkedList<ChannelContext>();
    private long realPrevTime;
    private long realCurrTime;
    private int prevSleepTime = 0;

    public ServerSendThreadHandler() {
    }
    
    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
