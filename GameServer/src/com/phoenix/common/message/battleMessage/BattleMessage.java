/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.common.message.battleMessage;

/**
 *
 * @author Administrator
 */
public interface BattleMessage {

    /**
     * 消息类型(前缀表示消息所在服务器) -- 消息说明
     *
     */
    public enum BattleMessageType {

        BATTLE_MESSAGE_GET_CHAR_NUM,           // 获取角色数量        
    }

    public BattleMessageType getType();
}
