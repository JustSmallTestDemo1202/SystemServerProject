/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.common.messageQueue;

import com.phoenix.common.message.serverRecvMessage.ServerRecvMessage;
import java.util.concurrent.LinkedTransferQueue;

/**
 *
 * @author rachel
 */
public class ServerRecvMessageQueue {
    private static final LinkedTransferQueue<ServerRecvMessage> messageQueue = new LinkedTransferQueue<ServerRecvMessage>();
    
    public static LinkedTransferQueue<ServerRecvMessage> queue() {
        return messageQueue;
    }
}
