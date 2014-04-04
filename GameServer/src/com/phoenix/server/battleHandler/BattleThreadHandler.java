/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.phoenix.server.battleHandler;

import com.phoenix.common.message.battleMessage.BattleMessage;
import com.phoenix.common.message.battleMessage.BattleMessage.BattleMessageType;
import com.phoenix.common.message.battleMessage.SimpleBattleMessage;
import com.phoenix.common.message.dbMessage.DBMessage;
import com.phoenix.common.messageQueue.BattleMessageQueueList;
import com.phoenix.utils.Consts;
import java.util.concurrent.LinkedTransferQueue;

/**
 *
 * @author Administrator
 */
public class BattleThreadHandler implements Runnable{
    protected final LinkedTransferQueue<BattleMessage> messageQueue;
    private int threadIndex;
    
    public BattleThreadHandler(int threadIndex) {
        this.threadIndex = threadIndex;
        messageQueue = BattleMessageQueueList.queue(threadIndex);
    }
    
    @Override
    public void run() {
        while (true) {
            // 处理消息队列中的消息
            handleMessages();

            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                System.err.println("Battle Thread " + threadIndex +" Sleep Error: " + ex.getMessage());
            }
        }
    }
    
    protected void handleMessages() {
        SimpleBattleMessage msg = (SimpleBattleMessage) messageQueue.poll();
        while (msg != null) {
            boolean needSendShutdown = false;
            BattleMessageType type = msg.getType();
            switch (type) {
            
            }
            msg = (SimpleBattleMessage) messageQueue.poll();
        }  
    }
}
