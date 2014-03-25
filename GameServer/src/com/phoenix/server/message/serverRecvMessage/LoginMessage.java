/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.server.message.serverRecvMessage;

import com.phoenix.common.message.serverRecvMessage.ExternalPlayerMessage;
import org.jboss.netty.channel.Channel;

/**
 *
 * @author rachel
 */
public class LoginMessage extends ExternalPlayerMessage {

    public final int playerId;
    public final boolean auth;
    public final int privilege;
    public final int endForbidTalkTime;
    public final String passport;

    public LoginMessage(Channel channel, int playerId, String passport, boolean auth, int privilege, int endForbidTalkTime) {
        super(ServerRecvMessageType.MAP_LOGIN, channel);

        this.playerId = playerId;
        this.passport = passport;
        this.auth = auth;
        this.privilege = privilege;
        this.endForbidTalkTime = endForbidTalkTime;
    }
}
