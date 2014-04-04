/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.phoenix.common.messageQueue;

import com.phoenix.common.message.battleMessage.BattleMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedTransferQueue;

/**
 *
 * @author Administrator
 */
public class BattleMessageQueueList {
    private static final List<LinkedTransferQueue<BattleMessage>> messsageQueueList = new ArrayList<>();
    
    public static List<LinkedTransferQueue<BattleMessage>> queueList() {
        return messsageQueueList;
    }
    
    public static LinkedTransferQueue<BattleMessage> queue(int index) {
        if (index < messsageQueueList.size()) {
            return messsageQueueList.get(index);
        }
        return null;        
    }
}
