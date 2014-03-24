/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.common.network.pipilineFactory;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.ChannelUpstreamHandler;
import static org.jboss.netty.channel.Channels.pipeline;
import org.jboss.netty.handler.codec.frame.LengthFieldBasedFrameDecoder;
import org.jboss.netty.handler.execution.ExecutionHandler;

/**
 *
 * @author rachel
 */
public class CommonToServerPipelineFactory implements ChannelPipelineFactory {

    // stateless handlers
    private final ExecutionHandler executionHandler;
    private final ChannelUpstreamHandler targetHandler;
    private final Type4BytesLengthFieldProtobufEncoder type4BytesLenthFieldProtobufEncoder = new Type4BytesLengthFieldProtobufEncoder();

    public CommonToServerPipelineFactory(ExecutionHandler executionHandler, ChannelUpstreamHandler targetHandler) {
        this.executionHandler = executionHandler;
        this.targetHandler = targetHandler;
    }

    @Override
    public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline p = pipeline();
        p.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(1024, 2, 2, 0, 0));
        p.addLast("customEncoder", type4BytesLenthFieldProtobufEncoder);

        if (executionHandler != null) {
            p.addLast("pipelineExecutor", executionHandler);
        }

        p.addLast("handler", targetHandler);
        return p;
    }
}
