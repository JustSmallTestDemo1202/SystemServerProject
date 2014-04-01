/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.icee.myth.network.builder;

import com.icee.myth.network.protobufmessage.ProtobufMessage;
import com.icee.myth.network.protobufmessage.ProtobufMessageType;
import com.phoenix.protobuf.ExternalCommonProtocol.CSCreateCharProto;
import com.phoenix.protobuf.ExternalCommonProtocol.CSLoginProto;
import com.phoenix.protobuf.ExternalCommonProtocol.CSSelectCharProto;

/**
 *
 * @author lidonglin
 */
public class MessageBuilder {
    public static ProtobufMessage buildLogin(String loginSession){
        CSLoginProto.Builder builder = CSLoginProto.newBuilder();
        builder.setLoginSession(loginSession);
        return new ProtobufMessage(ProtobufMessageType.C2S_LOGIN, builder.build());        
    }

    public static ProtobufMessage buildCreateChar(int job, String name){
        CSCreateCharProto.Builder builder = CSCreateCharProto.newBuilder();
        builder.setCharName(name);
        builder.setCharJob(job);
        builder.setCharGender(0);
        return new ProtobufMessage(ProtobufMessageType.C2S_CREATE_CHAR, builder.build());
    }

    public static ProtobufMessage buildSelectChar(int indexId) {
        CSSelectCharProto.Builder builder = CSSelectCharProto.newBuilder();
        builder.setIndexId(indexId);
        return new ProtobufMessage(ProtobufMessageType.C2S_SELECT_CHAR, builder.build());
    }
}
