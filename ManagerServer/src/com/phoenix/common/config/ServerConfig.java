/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.common.config;

import java.util.Date;

/**
 *
 * @author rachel
 */
public class ServerConfig {

    public int serverId;            // 服id
    public String serverName;       // 服名称
    public String dbHost;           // 数据库IP
    public String dbName;           // 数据库名
    public String logDBHost;        // 日志数据库IP
    public String logDBName;        // 日志数据库名前缀
    public String host;             // 部署服务器的机器内网IP
    public String externalHost;     // 对外提供服务的IP
    public int externalPort;        // 对外提供服务的端口
    public int managerPort;         // 让manager连接的端口
    public int rpcPort;             // rpc连接端口
    public Date openTime;           // 开服时间
    public String jvmOption;        // jvm参数
}
