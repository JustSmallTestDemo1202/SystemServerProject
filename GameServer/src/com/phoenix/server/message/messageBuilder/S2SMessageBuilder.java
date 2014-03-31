/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.server.message.messageBuilder;

import com.phoenix.common.message.serverRecvMessage.ExternalPlayerMessage;
import com.phoenix.common.message.serverRecvMessage.ServerRecvMessage;
import com.phoenix.common.message.serverRecvMessage.ServerRecvMessage.ServerRecvMessageType;
import com.phoenix.common.message.serverRecvMessage.SimpleServerRecvMessage;
import com.phoenix.protobuf.ExternalCommonProtocol.CSCreateCharProto;
import com.phoenix.protobuf.ExternalCommonProtocol.CSSelectCharProto;
import com.phoenix.server.actor.charInfo.CharDetailInfo;
import com.phoenix.server.message.serverRecvMessage.CharNumMessage;
import com.phoenix.server.message.serverRecvMessage.CreateCharMessage;
import com.phoenix.server.message.serverRecvMessage.CreateCharRetMessage;
import com.phoenix.server.message.serverRecvMessage.GetCharDetailRetMessage;
import com.phoenix.server.message.serverRecvMessage.LoginMessage;
import com.phoenix.server.message.serverRecvMessage.SelectCharMessage;
import com.phoenix.server.social.BriefPlayerInfo;
import java.util.List;
import org.jboss.netty.channel.Channel;

/**
 *
 * @author rachel
 */
public class S2SMessageBuilder {

    public static ServerRecvMessage buildShutdownMessage() {
        return new SimpleServerRecvMessage(ServerRecvMessageType.MAP_SVR_SHUTDOWN);
    }
    
    public static ServerRecvMessage buildClientConnectMessage(Channel channel) {
        return new ExternalPlayerMessage(ServerRecvMessageType.MAP_CLIENT_CONNECT, channel);
    }

    public static ServerRecvMessage buildClientCloseMessage(Channel channel) {
        return new ExternalPlayerMessage(ServerRecvMessageType.MAP_CLIENT_CLOSE, channel);
    }

    public static ServerRecvMessage buildLoginMessage(Channel channel, int playerId, String passport, boolean auth, int privilege, int endForbidTalkTime) {
        return new LoginMessage(channel, playerId, passport, auth, privilege, endForbidTalkTime);
    }

    public static ServerRecvMessage buildCharNumMessage(int playerId, int charNum, List<BriefPlayerInfo> charDetail) {
        return new CharNumMessage(playerId, charNum, charDetail);
    }

    public static ServerRecvMessage buildCreateCharRetMessage(int playerId, int indexId) {
        return new CreateCharRetMessage(playerId, indexId);
    }

    public static ServerRecvMessage buildCreateCharMessage(int playerId, CSCreateCharProto charInfo) {
        return new CreateCharMessage(playerId, charInfo);
    }

    public static ServerRecvMessage buildSelectCharMessage(int playerId, CSSelectCharProto charInfo) {
        return new SelectCharMessage(playerId, charInfo);
    }
    
    public static ServerRecvMessage buildGetCharDetailRetMessage(int playerId, int indexId, CharDetailInfo charDetailInfo) {
        return new GetCharDetailRetMessage(playerId, indexId, charDetailInfo);
    }
}
