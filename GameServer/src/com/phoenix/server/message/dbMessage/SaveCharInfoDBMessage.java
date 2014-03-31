/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.server.message.dbMessage;

import com.phoenix.common.message.dbMessage.SimpleDBMessage;
import com.phoenix.server.actor.charInfo.CharDetailInfo;

/**
 *
 * @author Administrator
 */
public class SaveCharInfoDBMessage extends SimpleDBMessage {

    public final int playerId;
    public final int indexId;
    public final CharDetailInfo detailCharInfo;

    public SaveCharInfoDBMessage(int playerId, int indexId, CharDetailInfo detailCharInfo) {
        super(DBMessageType.DB_MESSAGE_SAVE_CHAR_INFO);

        this.playerId = playerId;
        this.indexId = indexId;
        this.detailCharInfo = detailCharInfo;
    }
}
