/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.server.message.messageBuilder;

import com.phoenix.common.message.protobufMessage.ProtobufMessage;
import com.phoenix.common.message.protobufMessage.ProtobufMessageType;
import com.phoenix.protobuf.ExternalCommonProtocol.LongValueProto;
import com.phoenix.protobuf.ExternalCommonProtocol.SCEnterGameCharProto;
import com.phoenix.protobuf.ExternalCommonProtocol.SCEnterGameRetProto;

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
