/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.server.message.messageBuilder;

import com.phoenix.common.message.serverRecvMessage.ExternalPlayerMessage;
import com.phoenix.common.message.serverRecvMessage.ServerRecvMessage;
import com.phoenix.common.message.serverRecvMessage.ServerRecvMessage.ServerRecvMessageType;
import com.phoenix.protobuf.ExternalCommonProtocol.CSCreateCharProto;
import com.phoenix.server.message.serverRecvMessage.CharNumMessage;
import com.phoenix.server.message.serverRecvMessage.CreateCharMessage;
import com.phoenix.server.message.serverRecvMessage.CreateCharRetMessage;
import com.phoenix.server.message.serverRecvMessage.LoginMessage;
import org.jboss.netty.channel.Channel;

/**
 *
 * @author rachel
 */
public class S2SMessageBuilder {
    public static ServerRecvMessage buildClientConnectMessage(Channel channel) {
        return new ExternalPlayerMessage(ServerRecvMessageType.MAP_CLIENT_CONNECT,channel);
    }
    
    public static ServerRecvMessage buildClientCloseMessage(Channel channel) {
        return new ExternalPlayerMessage(ServerRecvMessageType.MAP_CLIENT_CLOSE, channel);
    }
    
    public static ServerRecvMessage buildLoginMessage(Channel channel, int playerId, String passport, boolean auth, int privilege,int endForbidTalkTime) {
        return new LoginMessage(channel, playerId, passport, auth, privilege, endForbidTalkTime);
    }
    
    public static ServerRecvMessage buildCharNumMessage(int playerId, int charNum) {
        return new CharNumMessage(playerId, charNum);
    }
    
     public static ServerRecvMessage buildCreateCharRetMessage(int playerId, int result) {
        return new CreateCharRetMessage(playerId, result);
    }
     
     public static ServerRecvMessage buildCreateCharMessage(int playerId, CSCreateCharProto charInfo) {
        return new CreateCharMessage(playerId, charInfo);
    }
}
