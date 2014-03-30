/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.icee.myth.network.builder;

import com.icee.myth.network.protobufmessage.ProtobufMessage;
import com.icee.myth.network.protobufmessage.ProtobufMessageType;
import com.icee.myth.protobuf.ExternalCommonProtocol.CreateCharProto;
import com.icee.myth.protobuf.ExternalCommonProtocol.EnterStageProto;
import com.icee.myth.protobuf.ExternalCommonProtocol.LoginProto;

/**
 *
 * @author lidonglin
 */
public class MessageBuilder {
    public static ProtobufMessage buildLogin(String loginSession){
        LoginProto.Builder builder = LoginProto.newBuilder();
        builder.setLoginsession(loginSession);
        return new ProtobufMessage(ProtobufMessageType.C2S_LOGIN, builder.build());        
    }

    public static ProtobufMessage buildCreateChar(int job, String name){
        CreateCharProto.Builder builder = CreateCharProto.newBuilder();
        builder.setName(name);
        builder.setJob(job);
        return new ProtobufMessage(ProtobufMessageType.C2S_CREATECHAR, builder.build());
    }

    public static ProtobufMessage buildEnterStage(int stageId, boolean isBigStage, int helper){
        EnterStageProto.Builder builder = EnterStageProto.newBuilder();
        builder.setStageId(stageId);
        builder.setIsBigStage(isBigStage);
        builder.setHelper(helper);
        return new ProtobufMessage(ProtobufMessageType.C2S_STAGE_ENTER, builder.build());
    }

    public static ProtobufMessage buildContinueStage(){
        return new ProtobufMessage(ProtobufMessageType.C2S_STAGE_CONTINUE, null);
    }

    public static ProtobufMessage buildStartPVEBattle(){
        return new ProtobufMessage(ProtobufMessageType.C2S_STARTPVEBATTLE, null);
    }

    public static ProtobufMessage buildLeaveStage(){
        return new ProtobufMessage(ProtobufMessageType.C2S_STAGE_CONTINUE, null);
    }

    //GM增加体力
    public static ProtobufMessage buildGMAddEmergy(){
        return new ProtobufMessage(ProtobufMessageType.C2S_GM_ADD_ENERGY, null);
    }

    //GM跳过新手指引
    public static ProtobufMessage buildIgnoreGuideStep(){
        return new ProtobufMessage(ProtobufMessageType.C2S_GM_IGNORE_GUIDE_STEP, null);
    }
}
