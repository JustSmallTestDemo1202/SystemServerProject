/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.common.network;

import com.google.gson.JsonSyntaxException;
import com.phoenix.common.loginAuth.JSONLoginAuthData;
import com.phoenix.common.loginAuth.JSONLoginAuthRetData;
import com.phoenix.common.message.protobufMessage.ProtobufMessageType;
import com.phoenix.common.messageQueue.ServerRecvMessageQueue;
import com.phoenix.protobuf.ExternalCommonProtocol.CSCreateCharProto;
import com.phoenix.protobuf.ExternalCommonProtocol.CSLoginProto;
import com.phoenix.protobuf.ExternalCommonProtocol.CSSelectCharProto;
import com.phoenix.server.GameServer;
import com.phoenix.server.message.messageBuilder.S2CMessageBuilder;
import com.phoenix.server.message.messageBuilder.S2SMessageBuilder;
import com.phoenix.utils.Consts;
import com.phoenix.utils.JSONHelper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.channels.ClosedChannelException;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBufferInputStream;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipelineCoverage;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

/**
 *
 * @author rachel
 */
@ChannelPipelineCoverage("all")
public class ClientConnectHandler extends SimpleChannelUpstreamHandler {

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        if (GameServer.INSTANCE.isShuttingDown()) {
            e.getChannel().close();
            return;
        }
        ServerRecvMessageQueue.queue().offer(S2SMessageBuilder.buildClientConnectMessage(e.getChannel()));
        System.out.println("Client connected channel id = " + e.getChannel().getId());
    }

    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        ServerRecvMessageQueue.queue().offer(S2SMessageBuilder.buildClientCloseMessage(e.getChannel()));
        System.out.println("Client closed channel id = " + e.getChannel().getId());
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
        if (GameServer.INSTANCE.isShuttingDown()) {
            e.getChannel().close();
            return;
        }

        ChannelBuffer cb = (ChannelBuffer) e.getMessage();
        short type = cb.readShort();
        short length = cb.readShort();
        Integer playerId = GameServer.INSTANCE.channelID2PlayerIDMap.get(e.getChannel().getId());

        if (playerId == null && type != ProtobufMessageType.C2S_LOGIN) {
            System.err.println("Handle wrong message[" + type + "] for unknown player, channel id[" + e.getChannel().getId() + "]");
            return;
        }
        try {
            switch (type) {
                case ProtobufMessageType.C2S_LOGIN:
                    if (playerId == null) {
                        CSLoginProto loginMsg = CSLoginProto.getDefaultInstance().newBuilderForType().mergeFrom(new ChannelBufferInputStream(cb)).build();
                        String loginSession = loginMsg.getLoginSession();
                        if (loginSession == null) {
                            loginFail(e.getChannel());
                            return;
                        }
                        InetSocketAddress saddr = (InetSocketAddress) e.getChannel().getRemoteAddress();
                        InetAddress inetAddress = saddr.getAddress();
                        String clientIp = inetAddress.getHostAddress();
                        JSONLoginAuthRetData loginAuthRetData = checkAccount(loginSession, clientIp);

                        if (loginAuthRetData == null || loginAuthRetData.result != 0 || loginAuthRetData.forbiddenEndLoginTime > (GameServer.INSTANCE.getCurrentTime() / Consts.MILSECOND_1SECOND)) {
                            System.err.println("Player[" + playerId + "] login fail for session[" + loginSession + "] result[" + loginAuthRetData.result + "].");
                            loginFail(e.getChannel());
                            return;
                        }
                        ServerRecvMessageQueue.queue().offer(S2SMessageBuilder.buildLoginMessage(e.getChannel(), loginAuthRetData.id, loginAuthRetData.passport, loginAuthRetData.auth, loginAuthRetData.privilege, loginAuthRetData.forbiddenEndTalkTime));
                    } else {
                        System.err.println("Player[" + playerId + "] Can't multi-login.");
                    }
                    break;
                case ProtobufMessageType.C2S_CREATE_CHAR:
                    CSCreateCharProto createCharInfo = CSCreateCharProto.getDefaultInstance().newBuilderForType().mergeFrom(new ChannelBufferInputStream(cb)).build();
                    ServerRecvMessageQueue.queue().offer(S2SMessageBuilder.buildCreateCharMessage(playerId, createCharInfo));
                    break;
                case ProtobufMessageType.C2S_SELECT_CHAR:
                    CSSelectCharProto selectCharInfo = CSSelectCharProto.getDefaultInstance().newBuilderForType().mergeFrom(new ChannelBufferInputStream(cb)).build();
                    ServerRecvMessageQueue.queue().offer(S2SMessageBuilder.buildSelectCharMessage(playerId, selectCharInfo));
                    break;
            }
        } catch (IOException ex) {
            System.err.println("Player[" + playerId + "] Message Received Type: " + type + " Error: " + ex.getMessage());
            e.getChannel().close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
        if (!(e.getCause() instanceof ClosedChannelException)) {
            e.getChannel().close();
        }
    }

    private JSONLoginAuthRetData checkAccount(String sessionId, String clientIp) {
        HttpURLConnection con = null;
        BufferedReader in = null;
        try {
            con = (HttpURLConnection) new URL(GameServer.INSTANCE.loginCheckUrl).openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setConnectTimeout(2000);
            con.setReadTimeout(2000);

            //Http请求
            JSONLoginAuthData data = new JSONLoginAuthData();
            data.sessionId = sessionId;
            data.clientIp = clientIp;

            con.getOutputStream().write(JSONHelper.toJSON(data).getBytes("UTF-8"));
            con.getOutputStream().flush();
            con.getOutputStream().close();

            //获取结果
            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String ret = in.readLine();

            JSONLoginAuthRetData retData = null;
            try {
                retData = JSONHelper.parseString(ret, JSONLoginAuthRetData.class);
            } catch (JsonSyntaxException jse) {
                // 记录JSON parse出错
                System.err.println("PlayerConnectHandler Check Account Error: " + jse.getMessage());
                return null;
            }

            return retData;
        } catch (IOException ex) {
            System.err.println("PlayerConnectHandler Check Account Error: " + ex.getMessage());
            return null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    System.err.println("PlayerConnectHandler Check Account Error: " + ex.getMessage());
                }
            }
            if (con != null) {
                con.disconnect();
            }
        }
    }

    private void loginFail(final Channel channel) {
        if (channel == null) {
            return;
        }

        ChannelFuture channelFuture = channel.write(S2CMessageBuilder.buildLoginFail());
        if (channelFuture != null) {
            //f.addListener(ChannelFutureListener.CLOSE);
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) {
                    future.getChannel().close();
                }
            });
        }
    }
}
