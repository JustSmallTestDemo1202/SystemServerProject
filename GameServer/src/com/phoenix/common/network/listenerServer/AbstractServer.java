/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.common.network.listenerServer;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

/**
 *
 * @author rachel
 */
public class AbstractServer {
    //监听端口

    private int port;
    private ServerBootstrap bootstrap;
    private Channel bindChannel = null;

    public AbstractServer() {
    }

    public void init(int port, ChannelPipelineFactory pipelineFactory) {
        this.port = port;

        // Start server with Nb of active threads = 2*NB CPU + 1 as maximum.
        ChannelFactory factory = new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool(),
                Runtime.getRuntime().availableProcessors() * 2 + 1);

        // Configure the server.
        bootstrap = new ServerBootstrap(factory);

        bootstrap.setPipelineFactory(pipelineFactory);

        bootstrap.setOption("child.tcpNoDelay", true);
        bootstrap.setOption("child.keepAlive", true);
        bootstrap.setOption("reuseAddress ", true);
    }

    public void startServer() {
        // Bind and start to accept incoming connections.
        bindChannel = bootstrap.bind(new InetSocketAddress(port));
    }

    public void closeServer() {
        if (bindChannel != null) {
            bindChannel.close();
        }
    }
}
