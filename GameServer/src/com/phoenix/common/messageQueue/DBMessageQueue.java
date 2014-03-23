/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.common.messageQueue;

import com.phoenix.common.message.dbMessage.DBMessage;
import java.util.concurrent.LinkedTransferQueue;

/**
 * 服务器数据库消息队列
 * @author rachel
 */
public class DBMessageQueue {

    private static final LinkedTransferQueue<DBMessage> messsageQueue = new LinkedTransferQueue<>();

    public static LinkedTransferQueue<DBMessage> queue() {
        return messsageQueue;
    }
}
