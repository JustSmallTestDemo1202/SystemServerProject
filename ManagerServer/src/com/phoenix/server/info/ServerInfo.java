/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.server.info;

import com.phoenix.common.config.ServerConfig;

/**
 *
 * @author rachel
 */
public class ServerInfo {

    public ServerConfig serverConfig;

    public ServerInfo(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }

    public int getServerId() {
        return serverConfig.serverId;
    }
}
