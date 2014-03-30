/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.common.database;

import com.phoenix.common.message.dbMessage.DBMessage;
import com.phoenix.common.message.dbMessage.DBMessage.DBMessageType;
import com.phoenix.common.message.dbMessage.SimpleDBMessage;
import com.phoenix.common.messageQueue.DBMessageQueue;
import com.phoenix.utils.Consts;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.LinkedTransferQueue;

/**
 *
 * @author rachel
 */
public class DBThreadHandler implements Runnable {

    protected final LinkedTransferQueue<DBMessage> messageQueue = DBMessageQueue.queue();
    protected Connection connection;
    private int keepAliveInterval;
    private boolean isDBDown = false;
    private boolean isShuttingDown = false;

    public DBThreadHandler(Connection connection) {
        this.connection = connection;
        keepAliveInterval = Consts.DB_KEEPALIVE_INTERVAL;
    }

    @Override
    public void run() {
        while (true) {
            // 处理消息队列中的消息
            handleMessages();

            if (isShuttingDown) {
                break;
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                System.err.println("DB Thread Sleep Error: " + ex.getMessage());
            }

            // 此处保持服务器连接的时间不是为准确的1hr
            if (keepAliveInterval <= 0) {
                dbKeepAlive();
                keepAliveInterval = Consts.DB_KEEPALIVE_INTERVAL;
            } else {
                keepAliveInterval -= 100;
            }
        }
    }

    private void dbKeepAlive() {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            stmt.executeQuery(Consts.DB_KEEPALIVE_TEST_STATEMENT);
            System.out.println("DB Keep Alive Success.");
        } catch (SQLException ex) {
            System.err.println("DB Keep Alive Error: " + ex.getMessage());
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    System.err.println("DB Keep Alive Statement Close Error: " + ex.getMessage());
                }
            }
        }
    }

    protected void handleMessages() {
        SimpleDBMessage msg = (SimpleDBMessage) messageQueue.poll();
        while (msg != null) {
            boolean needSendShutdown = false;
            DBMessageType type = msg.getType();
            switch (type) {
                case DB_MESSAGE_GET_CHAR_NUM: {
                    if (needSendShutdown == false) {
                        
                    }
                    break;
                }
            }
        }
    }
}
