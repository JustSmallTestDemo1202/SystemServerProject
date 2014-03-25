/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.server.message.serverRecvMessage;

import com.phoenix.common.message.serverRecvMessage.InternalPlayerMessage;
import com.phoenix.protobuf.ExternalCommonProtocol.CSCreateCharProto;

/**
 *
 * @author rachel
 */
public class CreateCharMessage extends InternalPlayerMessage {

    public final CSCreateCharProto charInfo;

    public CreateCharMessage(int playerId, CSCreateCharProto charInfo) {
        super(ServerRecvMessageType.MAP_CREATE_CHAR, playerId);
        
        this.charInfo = charInfo;
    }
}
