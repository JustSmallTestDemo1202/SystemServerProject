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
public class CharNumMessage extends InternalPlayerMessage {

    public final int charNum;

    public CharNumMessage(int playerId, int charNum) {
        super(ServerRecvMessageType.MAP_CHAR_NUM, playerId);

        this.charNum = charNum;
    }
}
