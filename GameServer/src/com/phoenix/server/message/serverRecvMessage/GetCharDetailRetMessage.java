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
public class GetCharDetailRetMessage extends InternalPlayerMessage{
    public final CharDetailInfo charDetailInfo;

    public GetCharDetailRetMessage(int playerId, CharDetailInfo charDetailInfo) {
        super(ServerRecvMessageType.MAP_GET_CHAR_DETAIL_INFO_RET, playerId);

        this.charDetailInfo = charDetailInfo;
    }
}
