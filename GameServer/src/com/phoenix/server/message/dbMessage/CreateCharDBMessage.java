/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.server.message.dbMessage;

import com.phoenix.common.message.dbMessage.SimpleDBMessage;
import com.phoenix.protobuf.ExternalCommonProtocol.CSCreateCharProto;

/**
 *
 * @author rachel
 */
public class CreateCharDBMessage extends SimpleDBMessage {

    public final int playerId;
    public final CSCreateCharProto createCharInfo;

    public CreateCharDBMessage(int playerId, CSCreateCharProto createCharInfo) {
        super(DBMessageType.DB_MESSAGE_CREATE_CHAR);
        this.playerId = playerId;
        this.createCharInfo = createCharInfo;
    }
}
