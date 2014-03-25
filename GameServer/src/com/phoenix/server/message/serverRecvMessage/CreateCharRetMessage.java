/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.server.message.serverRecvMessage;

import com.phoenix.common.message.serverRecvMessage.InternalPlayerMessage;

/**
 *
 * @author rachel
 */
public class CreateCharRetMessage extends InternalPlayerMessage {

    public final int result;

    public CreateCharRetMessage(int playerId, int result) {
        super(ServerRecvMessageType.MAP_CREATE_CHAR_RET, playerId);

        this.result = result;
    }
}
