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
public class SimpleBattleMessage implements BattleMessage {

    private final BattleMessageType type;

    public SimpleBattleMessage(BattleMessageType type) {
        this.type = type;
    }

    @Override
    public BattleMessageType getType() {
        return type;
    }
}
