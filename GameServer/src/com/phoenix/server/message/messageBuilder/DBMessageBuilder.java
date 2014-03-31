/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.server.message.messageBuilder;

import com.phoenix.common.message.dbMessage.DBMessage.DBMessageType;
import com.phoenix.common.message.dbMessage.SimpleDBMessage;
import com.phoenix.protobuf.ExternalCommonProtocol.CSCreateCharProto;
import com.phoenix.server.actor.charInfo.CharDetailInfo;
import com.phoenix.server.message.dbMessage.CreateCharDBMessage;
import com.phoenix.server.message.dbMessage.GetCharDetailDBMessage;
import com.phoenix.server.message.dbMessage.GetCharNumDBMessage;
import com.phoenix.server.message.dbMessage.SaveCharInfoDBMessage;

/**
 *
 * @author rachel
 */
public class DBMessageBuilder {

     public static SimpleDBMessage buildShutdownDBMessage() {
        return new SimpleDBMessage(DBMessageType.DB_MESSAGE_SHUTDOWN);
    }
    
    public static GetCharNumDBMessage buildGetCharNumDBMessage(int playerId) {
        return new GetCharNumDBMessage(playerId);
    }

    public static CreateCharDBMessage buildCreateCharDBMessage(int playerId, CSCreateCharProto charInfo) {
        return new CreateCharDBMessage(playerId, charInfo);
    }

    public static GetCharDetailDBMessage buildGetCharDetailDBMessage(int playerId, int indexId) {
        return new GetCharDetailDBMessage(playerId, indexId);
    }

    public static SaveCharInfoDBMessage buildSaveCharInfoDBMessage(int playerId, int indexId, CharDetailInfo info) {
        return new SaveCharInfoDBMessage(playerId, indexId, info);
    }
}
