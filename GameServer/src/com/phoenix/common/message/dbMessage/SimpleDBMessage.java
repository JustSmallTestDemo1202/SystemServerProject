/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.common.message.dbMessage;

/**
 *
 * @author rachel
 */
public class SimpleDBMessage implements DBMessage {

    private final DBMessageType type;

    public SimpleDBMessage(DBMessageType type) {
        this.type = type;
    }

    @Override
    public DBMessageType getType() {
        return type;
    }
}
