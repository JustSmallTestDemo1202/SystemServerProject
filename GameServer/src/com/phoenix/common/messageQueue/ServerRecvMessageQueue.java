/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.common.messageQueue;

import java.util.concurrent.LinkedTransferQueue;

/**
 *
 * @author rachel
 */
public class ServerRecvMessageQueue {
    private static final LinkedTransferQueue<Message> messageQueue = new LinkedTransferQueue<Message>();
    
    public static LinkedTransferQueue<Message> queue() {
        return messageQueue;
    }
}
