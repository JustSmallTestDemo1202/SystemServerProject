/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.common.network;

import com.phoenix.common.message.serverSendMessage.ServerSendMessage;
import com.phoenix.common.message.serverSendMessage.ServerSendMessage.ServerSendMessageType;
import com.phoenix.common.messageQueue.ServerSendMessageQueue;
import com.phoenix.common.network.channel.ChannelContext;
import com.phoenix.server.message.serverSendMessage.AddChannelContextNSTMessage;
import com.phoenix.utils.Consts;
import java.util.Iterator;
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
       realPrevTime = System.currentTimeMillis();
        while (true) {
            realCurrTime = System.currentTimeMillis();
            int difftime = (int) (realCurrTime - realPrevTime);

            handleMessages();

            // 统一发送消息
            flushNetData();

            realPrevTime = realCurrTime;
            // diff (D0) include time of previous sleep (d0) + tick time (t0)
            // we want that next d1 + t1 == WORLD_SLEEP_CONST
            // we can't know next t1 and then can use (t0 + d1) == WORLD_SLEEP_CONST requirement
            // d1 = WORLD_SLEEP_CONST - t0 = WORLD_SLEEP_CONST - (D0 - d0) = WORLD_SLEEP_CONST + d0 - D0
            if (difftime <= Consts.GAME_SLEEP_CONST + prevSleepTime) {
                prevSleepTime = Consts.GAME_SLEEP_CONST + prevSleepTime - difftime;
            } else {
                prevSleepTime = 10;
                System.out.println("NetworkSendThread: difftime=" + difftime);
            }

            try {
                Thread.sleep(prevSleepTime);
            } catch (InterruptedException ex) {
                System.err.println("Network Send Thread Sleep Error " + ex.getMessage());
            }
        }
    }

    private void handleMessages() {
        // handle message from message queue
        // 不处理shuttingDown过程中产生的消息
        ServerSendMessage msg = messageQueue.poll();
        while (msg != null) {
            ServerSendMessageType type = msg.getType();
            switch (msg.getType()) {
                case NETWORK_SEND_MESSAGE_ADD_CHANNEL_CONTEXT: {
                    channelContexts.add(((AddChannelContextNSTMessage) msg).channelContext);
                    break;
                }
            }
            msg = (ServerSendMessage) messageQueue.poll();
        }
    }

    private void flushNetData() {
        for (Iterator<ChannelContext> iter = channelContexts.iterator(); iter.hasNext();) {
            ChannelContext channelContext = iter.next();
            if (channelContext.isActive()) {
                channelContext.flush();
            } else {
                iter.remove();
            }
        }
    }
}
