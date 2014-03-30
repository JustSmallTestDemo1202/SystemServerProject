/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icee.myth.network.handler;

import com.icee.myth.model.actor.Human;
import com.icee.myth.network.message.Message;
import com.icee.myth.network.protobufmessage.ProtobufMessageType;
import com.icee.myth.protobuf.ExternalCommonProtocol.EnterGameRetProto;
import com.icee.myth.protobuf.ExternalCommonProtocol.IntValueProto;
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
                case ProtobufMessageType.S2C_NOCHARRET:{
                    human.receiver.messageQueue.add(new Message(type, null));
                    break;
                }

                case ProtobufMessageType.S2C_CREATECHARERROR: {
                    System.out.println("S2C_CREATECHARERROR");
                    break;
                }

                case ProtobufMessageType.S2C_ENTERGAMERET:{
                    EnterGameRetProto enterGameRetProto = EnterGameRetProto.getDefaultInstance().newBuilderForType().mergeFrom(new ChannelBufferInputStream(cb)).build();
                    human.receiver.messageQueue.add(new Message(type, enterGameRetProto));
                    break;
                }

                case ProtobufMessageType.S2C_ENERGY_CHANGE:{
                    IntValueProto energyProto = IntValueProto.getDefaultInstance().newBuilderForType().mergeFrom(new ChannelBufferInputStream(cb)).build();
                    human.receiver.messageQueue.add(new Message(type, energyProto));
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
