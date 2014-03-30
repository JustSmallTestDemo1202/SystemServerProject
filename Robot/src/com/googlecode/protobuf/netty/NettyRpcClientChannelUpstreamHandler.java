/*
 * Copyright (c) 2009 Stephen Tu <stephen_tu@berkeley.edu>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.googlecode.protobuf.netty;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipelineCoverage;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

import com.googlecode.protobuf.netty.NettyRpcChannel.ResponsePrototypeRpcCallback;
import com.googlecode.protobuf.netty.NettyRpcProto.RpcResponse;
import com.icee.myth.utils.StackTraceUtil;

@ChannelPipelineCoverage("one")
public class NettyRpcClientChannelUpstreamHandler extends SimpleChannelUpstreamHandler {

    private final AtomicInteger seqNum = new AtomicInteger(0);
    private final Map<Integer, ResponsePrototypeRpcCallback> callbackMap = new ConcurrentHashMap<Integer, ResponsePrototypeRpcCallback>();

    public int getNextSeqId() {
        return seqNum.getAndIncrement();
    }

    public synchronized void registerCallback(int seqId, ResponsePrototypeRpcCallback callback) {
        if (callbackMap.containsKey(seqId)) {
            throw new IllegalArgumentException("Callback already registered");
        }
        callbackMap.put(seqId, callback);
    }

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
        System.out.println("RPC Channel connected.");
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {

        RpcResponse response = (RpcResponse) e.getMessage();

        if (!response.hasId()) {
            System.out.println("RPC request should never receive response without seqId");
            return;
        }

        int seqId = response.getId();
        ResponsePrototypeRpcCallback callback = callbackMap.remove(seqId);

        if (response.hasErrorCode() && callback != null && callback.getRpcController() != null) {
            callback.getRpcController().setFailed(response.getErrorMessage());
        }

        if (callback == null) {
            System.out.println("Received response with no callback registered");
        } else {
            System.out.println("Invoking callback with response");
            callback.run(response);
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        System.out.println("Unhandled exception in handler:" + StackTraceUtil.getStackTrace(e.getCause()));
        e.getChannel().close();
        throw new Exception(e.getCause());
    }
}
