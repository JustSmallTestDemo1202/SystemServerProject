/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.common.message.dbMessage;

/**
 *
 * @author rachel
 */
public interface DBMessage {
    /**
     * 消息类型(前缀表示消息所在服务器)   -- 消息说明
     *
     */
    public enum DBMessageType {
        DB_MESSAGE_GET_CHAR_NUM,           // 获取角色数量
        DB_MESSAGE_CREATE_CHAR,            // 创建角色
        DB_MESSAGE_GET_CHAR_DETAIL,        // 获取角色信息
        DB_MESSAGE_SAVE_CHAR_INFO,         // 保存角色信息        
        DB_MESSAGE_SHUTDOWN                // 关闭DB线程
    }

    public DBMessageType getType();
}
