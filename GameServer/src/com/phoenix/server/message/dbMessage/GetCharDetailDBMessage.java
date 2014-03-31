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
public class GetCharDetailDBMessage extends SimpleDBMessage {

    public final int playerId;
    public final int indexId;

    public GetCharDetailDBMessage(int playerId, int indexId) {
        super(DBMessageType.DB_MESSAGE_GET_CHAR_DETAIL);
        this.playerId = playerId;
        this.indexId = indexId;
    }
}
