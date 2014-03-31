/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.server.message.serverRecvMessage;

import com.phoenix.common.message.serverRecvMessage.InternalPlayerMessage;
import com.phoenix.server.actor.charInfo.CharDetailInfo;

/**
 *
 * @author Administrator
 */
public class GetCharDetailRetMessage extends InternalPlayerMessage {

    public final CharDetailInfo charDetailInfo;
    public final int indexId;

    public GetCharDetailRetMessage(int playerId, int indexId, CharDetailInfo charDetailInfo) {
        super(ServerRecvMessageType.MAP_GET_CHAR_DETAIL_INFO_RET, playerId);
        this.indexId = indexId;
        this.charDetailInfo = charDetailInfo;
    }
}
