/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.server.message.messageBuilder;

import com.phoenix.common.message.protobufMessage.ProtobufMessage;
import com.phoenix.common.message.protobufMessage.ProtobufMessageType;
import com.phoenix.protobuf.ExternalCommonProtocol.BriefPlayerProto;
import com.phoenix.protobuf.ExternalCommonProtocol.LongValueProto;
import com.phoenix.protobuf.ExternalCommonProtocol.SCCharListProto;
import com.phoenix.protobuf.ExternalCommonProtocol.SCEnterGameCharProto;
import com.phoenix.protobuf.ExternalCommonProtocol.SCEnterGameRetProto;
import com.phoenix.server.social.BriefPlayerInfo;
import java.util.List;

/**
 *
 * @author rachel
 */
public class S2CMessageBuilder {

    public static ProtobufMessage buildRealTime(long realTime) {
        LongValueProto.Builder builder = LongValueProto.newBuilder();
        builder.setValue(realTime);
        return new ProtobufMessage(ProtobufMessageType.S2C_REAL_TIME, builder.build().toByteArray());
    }

    public static ProtobufMessage buildLoginFail() {
        return new ProtobufMessage(ProtobufMessageType.S2C_LOGIN_ERROR, null);
    }

    public static ProtobufMessage buildLoginRetNoChar() {
        return new ProtobufMessage(ProtobufMessageType.S2C_NO_CHAR_RET, null);
    }
    
    public static ProtobufMessage buildLoginCharList(List<BriefPlayerInfo> playersInfo) {
        SCCharListProto.Builder builder1 = SCCharListProto.newBuilder();
        for (BriefPlayerInfo playerInfo : playersInfo) {
            BriefPlayerProto.Builder builder2 = BriefPlayerProto.newBuilder();
            builder2.setIndexId(playerInfo.indexId);
            builder2.setCharId(playerInfo.charId);            
            builder2.setCharName(playerInfo.charName);
            builder2.setCharGender(playerInfo.charGender);
            builder2.setCharJob(playerInfo.charJob);
            builder2.setCharLevel(playerInfo.charLevel);

            builder1.addBriefPlayer(builder2);
        }
        return new ProtobufMessage(ProtobufMessageType.S2C_CHAR_LIST, builder1.build());
    }

    public static ProtobufMessage buildCreateCharError() {
        return new ProtobufMessage(ProtobufMessageType.S2C_CREATE_CHAR_ERROR, null);
    }

    public static ProtobufMessage buildEnterGameRet(SCEnterGameCharProto enterGameCharInfo) {
        SCEnterGameRetProto.Builder builder = SCEnterGameRetProto.newBuilder();

        builder.setResult(0);
        builder.setEnterGameChar(enterGameCharInfo);

        return new ProtobufMessage(ProtobufMessageType.S2C_ENTER_GAME_RET, builder.build());
    }
}
