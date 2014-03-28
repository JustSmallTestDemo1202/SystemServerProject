/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.common.message.protobufMessage;

/**
 *
 * @author rachel
 */
public class ProtobufMessage {
    public int type;
    public Object payload;

    public ProtobufMessage(int type, Object payload) {
        this.type = type;
        this.payload = payload;
    }
}
