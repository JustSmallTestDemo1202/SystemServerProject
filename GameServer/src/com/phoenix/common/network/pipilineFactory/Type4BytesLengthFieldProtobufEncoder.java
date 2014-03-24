/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.common.network.pipilineFactory;

import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.MessageLite;
import com.phoenix.common.message.protobufMessage.ProtobufMessage;
import com.phoenix.common.network.channelBuffer.NewChannelBuffers;
import java.io.IOException;
import java.nio.ByteOrder;
import java.util.ArrayList;
import org.jboss.netty.buffer.ChannelBuffer;
import static org.jboss.netty.buffer.ChannelBuffers.wrappedBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipelineCoverage;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

/**
 *
 * @author rachel
 */
@ChannelPipelineCoverage("all")
public class Type4BytesLengthFieldProtobufEncoder extends OneToOneEncoder {
    public Type4BytesLengthFieldProtobufEncoder() {
        super();
    }

    @Override
    protected Object encode(
            ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
        if (msg instanceof ProtobufMessage) {
            return encodeProtobufMessage(channel, (ProtobufMessage)msg);
        } else if (msg instanceof ArrayList) {
            ArrayList<Object> msgs = (ArrayList<Object>)msg;
            int size = msgs.size();
            for(int i=0; i<size; i++) {
                Object obj = msgs.get(i);
                if (!(obj instanceof ChannelBuffer)) {
                    msgs.set(i, encodeProtobufMessage(channel, (ProtobufMessage)msgs.get(i)));
                }
            }
            return NewChannelBuffers.wrappedBuffer(msgs);
        } else {
            return msg;
        }
    }

    private Object encodeProtobufMessage(Channel channel, ProtobufMessage pm) {
        if (pm.payload instanceof MessageLite) {
            MessageLite ml = (MessageLite) pm.payload;
            int mlsize = ml.getSerializedSize();
            byte[] res = new byte[6 + mlsize];
            res[0] = (byte) ((pm.type >> 8) & 0xFF);
            res[1] = (byte) (pm.type & 0xFF);
            res[2] = (byte) ((mlsize >> 24) & 0xFF);
            res[3] = (byte) ((mlsize >> 16) & 0xFF);
            res[4] = (byte) ((mlsize >> 8) & 0xFF);
            res[5] = (byte) (mlsize & 0xFF);
            CodedOutputStream cos = CodedOutputStream.newInstance(res, 6, mlsize);
            try {
                ml.writeTo(cos);
            } catch (IOException ex) {
                //GameLogger.getlogger().log(GameLogMessageBuilder.buildFileNetErrorGameLogMessage(StackTraceUtil.getStackTrace(ex)));
            }
            cos = null;
            return wrappedBuffer(res);
        }  else if (pm.payload instanceof byte[]) {
            ChannelBuffer header = channel.getConfig().getBufferFactory().getBuffer(ByteOrder.BIG_ENDIAN, 6);
            byte[] arr = (byte[]) pm.payload;
            header.writeShort((short)pm.type);
            header.writeInt(arr.length);
            return wrappedBuffer(header, wrappedBuffer(arr));
        } else if(pm.payload == null) {
            ChannelBuffer header = channel.getConfig().getBufferFactory().getBuffer(ByteOrder.BIG_ENDIAN, 6);
            header.writeShort((short)pm.type);
            header.writeInt(0);
            return header;
        }

        return pm;
    }
}
