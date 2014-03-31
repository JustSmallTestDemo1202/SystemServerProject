/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.server.message.serverRecvMessage;

import com.phoenix.common.message.serverRecvMessage.InternalPlayerMessage;
import com.phoenix.protobuf.ExternalCommonProtocol;
import com.phoenix.protobuf.ExternalCommonProtocol.CSSelectCharProto;

/**
 *
 * @author rachel
 */
public class SelectCharMessage extends InternalPlayerMessage {

    public final ExternalCommonProtocol.CSSelectCharProto charInfo;

    public SelectCharMessage(int playerId, CSSelectCharProto charInfo) {
        super(ServerRecvMessageType.MAP_SELECT_CHAR, playerId);

        this.charInfo = charInfo;
    }
}
