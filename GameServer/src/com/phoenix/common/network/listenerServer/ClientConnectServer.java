/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.common.network.listenerServer;

/**
 *
 * @author rachel
 */
public class ClientConnectServer extends AbstractServer {
    //Singleton

    public static ClientConnectServer INSTANCE = new ClientConnectServer();

    private ClientConnectServer() {
    }
}
