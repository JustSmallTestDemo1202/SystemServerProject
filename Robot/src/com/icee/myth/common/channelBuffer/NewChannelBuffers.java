/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.icee.myth.common.channelBuffer;

import com.google.protobuf.CodedOutputStream;
import java.nio.ByteOrder;
import java.util.ArrayList;
import static org.jboss.netty.buffer.ChannelBuffers.*;

import java.util.List;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.buffer.CompositeChannelBuffer;

/**
 *
 * @author lidonglin
 */
public class NewChannelBuffers {
    public static ChannelBuffer wrappedBuffer(ArrayList<Object> buffers) {
        int size = buffers.size();
        switch (size) {
            case 0:
                break;
            case 1: {
                ChannelBuffer cb = (ChannelBuffer)buffers.get(0);
                if (cb.readable()) {
                    return ChannelBuffers.wrappedBuffer(cb);
                }
                break;
            }
            default: {
                ByteOrder order = null;
                final ArrayList<ChannelBuffer> components = new ArrayList<ChannelBuffer>(size);
                for (int i=0; i<size; i++) {
                    ChannelBuffer cb = (ChannelBuffer)buffers.get(i);
                    if (cb == null) {
                        break;
                    }
                    if (cb.readable()) {
                        if (order != null) {
                            if (!order.equals(cb.order())) {
                                throw new IllegalArgumentException(
                                        "inconsistent byte order");
                            }
                        } else {
                            order = cb.order();
                        }
                        if (cb instanceof CompositeChannelBuffer) {
                            // Expand nested composition.
                            components.addAll(
                                    ((CompositeChannelBuffer) cb).decompose(
                                            cb.readerIndex(), cb.readableBytes()));
                        } else {
                            // An ordinary buffer (non-composite)
                            components.add(cb.slice());
                        }
                    }
                }
                return compositeBuffer(order, components);
            }
        }
        return EMPTY_BUFFER;
    }

    private static ChannelBuffer compositeBuffer(
            ByteOrder endianness, List<ChannelBuffer> components) {
        switch (components.size()) {
        case 0:
            return EMPTY_BUFFER;
        case 1:
            return components.get(0);
        default:
            return new CompositeChannelBuffer(endianness, components, true);
        }
    }

    public static ChannelBuffer buildVarint32(int data) {
        byte[] bytes = new byte[CodedOutputStream.computeRawVarint32Size(data)];
        int index = 0;
        while (true) {
          if ((data & ~0x7F) == 0) {
              bytes[index++] = (byte)data;
              break;
          } else {
              bytes[index++] = (byte)((data & 0x7F) | 0x80);
              data >>>= 7;
          }
        }

        return ChannelBuffers.wrappedBuffer(bytes);
    }
}
