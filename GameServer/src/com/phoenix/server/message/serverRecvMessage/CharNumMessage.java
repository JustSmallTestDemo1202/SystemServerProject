/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.server.message.serverRecvMessage;

import com.phoenix.common.message.serverRecvMessage.InternalPlayerMessage;
import com.phoenix.server.social.BriefPlayerInfo;
import java.util.List;

/**
 *
 * @author rachel
 */
public class CharNumMessage extends InternalPlayerMessage {    
    
    public final int charNum;
    public final List<BriefPlayerInfo> charDetail;

    public CharNumMessage(int playerId, int charNum, List<BriefPlayerInfo> charDetail) {
        super(ServerRecvMessageType.MAP_CHAR_NUM, playerId);

        this.charNum = charNum;
        this.charDetail = charDetail;
    }
}
