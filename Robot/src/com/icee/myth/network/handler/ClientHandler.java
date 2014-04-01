/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icee.myth.network.handler;

import com.icee.myth.model.actor.Human;
import com.icee.myth.network.message.Message;
import com.icee.myth.network.protobufmessage.ProtobufMessageType;
import com.phoenix.protobuf.ExternalCommonProtocol.SCCharListProto;
import com.phoenix.protobuf.ExternalCommonProtocol.SCEnterGameRetProto;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBufferInputStream;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

/**
 *
 * @author lidonglin
 */
public class ClientHandler extends SimpleChannelUpstreamHandler {

    public Human human;

    public ClientHandler(Human human) {
        this.human = human;
    }

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
        System.out.println(human.name + " Connect to server.");
    }

    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        human.leaveGame();
        System.out.println(human.name + " Disconnect from GW.");
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
        try {
            ChannelBuffer cb = (ChannelBuffer) e.getMessage();
            short type = cb.readShort();
            int length = cb.readInt();

            switch (type) {
                case ProtobufMessageType.S2C_NO_CHAR_RET:{
                    human.receiver.messageQueue.add(new Message(type, null));
                    break;
                }

                case ProtobufMessageType.S2C_CREATE_CHAR_ERROR: {
                    System.out.println("S2C_CREATECHARERROR");
                    break;
                }                
                case ProtobufMessageType.S2C_CHAR_LIST: {
                    SCCharListProto charListProto = SCCharListProto.getDefaultInstance().newBuilderForType().mergeFrom(new ChannelBufferInputStream(cb)).build();
                    human.receiver.messageQueue.add(new Message(type, charListProto));
                    break;
                }                
                case ProtobufMessageType.S2C_ENTER_GAME_RET:{
                    SCEnterGameRetProto enterGameRetProto = SCEnterGameRetProto.getDefaultInstance().newBuilderForType().mergeFrom(new ChannelBufferInputStream(cb)).build();
                    human.receiver.messageQueue.add(new Message(type, enterGameRetProto));
                    break;
                }                
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
        e.getCause().printStackTrace();
        e.getChannel().close();
    }
}
