/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.common.messageQueue;

import com.phoenix.common.message.serverSendMessage.ServerSendMessage;
import java.util.concurrent.LinkedTransferQueue;

/**
 * 服务器发包消息队列
 * @author rachel
 */
public class ServerSendMessageQueue {

    private static final LinkedTransferQueue<ServerSendMessage> messageQueue = new LinkedTransferQueue<>();

    public static LinkedTransferQueue<ServerSendMessage> queue() {
        return messageQueue;
    }
}
