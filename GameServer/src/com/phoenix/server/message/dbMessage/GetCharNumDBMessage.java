/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.server.message.dbMessage;

import com.phoenix.common.message.dbMessage.SimpleDBMessage;

/**
 *
 * @author rachel
 */
public class GetCharNumDBMessage extends SimpleDBMessage {

    public final int playerId;

    public GetCharNumDBMessage(int playerId) {
        super(DBMessageType.DB_MESSAGE_GET_CHAR_NUM);
        this.playerId = playerId;
    }
}
