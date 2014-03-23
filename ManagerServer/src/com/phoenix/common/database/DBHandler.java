/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.common.database;

import com.phoenix.common.config.ServerConfig;
import com.phoenix.common.web.PlayerManager;
import com.phoenix.server.info.PlayerInfo;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;

/**
 *
 * @author rachel
 */
public class DBHandler {

    private String dbHost;  // 数据库地址
    private String dbName;  // 数据库名
    public Connection connection;  // 数据库连接

    public DBHandler(String dbHost, String dbName) {
        this.dbHost = dbHost;
        this.dbName = dbName;
    }

    public boolean connectToDB() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://" + dbHost + "/" + dbName + "?useUnicode=true&characterEncoding=UTF-8", "phoenixtest", "acY5qmGKVcRs4nST");
            }
            return true;
        } catch (SQLException ex) {
            System.err.println("Connect to database error : SQLException " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.err.println("Connect to database error : ClassNotFoundException " + ex.getMessage());
        }

        return false;
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            System.err.println("close database error : SQLException " + ex.getMessage());
        }
    }

    public LinkedList<ServerConfig> getServerConfigs() {
        LinkedList<ServerConfig> serverConfigs = new LinkedList<ServerConfig>();

        Statement stmt = null;
        ResultSet rs = null;
        try {
            if (connectToDB()) {
                String sql = "SELECT serverId,serverName,dbHost,dbName,logdbHost,logdbName,host,externalHost,externalPort,managerPort,rpcPort,opentime,jvmoption FROM serverconfig;";
                stmt = connection.createStatement();

                rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    ServerConfig serverConfig = new ServerConfig();
                    serverConfig.serverId = rs.getInt("serverId");
                    serverConfig.serverName = rs.getString("serverName");
                    serverConfig.dbName = rs.getString("dbName");
                    serverConfig.dbHost = rs.getString("dbHost");
                    serverConfig.logDBHost = rs.getString("logdbHost");
                    serverConfig.logDBName = rs.getString("logdbName");
                    serverConfig.host = rs.getString("host");
                    serverConfig.externalHost = rs.getString("externalHost");
                    serverConfig.externalPort = rs.getInt("externalPort");
                    serverConfig.managerPort = rs.getInt("managerPort");
                    serverConfig.rpcPort = rs.getInt("rpcPort");
                    Timestamp ts = rs.getTimestamp("openTime");
                    serverConfig.openTime = new Date(ts.getTime());
                    serverConfig.jvmOption = rs.getString("jvmOption");
                    serverConfigs.add(serverConfig);
                }
            }
        } catch (SQLException ex) {
            // TODO: 统计DB出错次数,当连续出错次数达到限值时,表示数据库故障
            System.err.println("GetServerConfigs Error:" + ex.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println("GetServerConfigs Close ResultSet Error: " + ex.getMessage());
                }
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    System.err.println("GetServerConfigs Close Statement Error: " + ex.getMessage());
                }
            }
        }
        return serverConfigs;
    }

    public boolean getMaxCid(PlayerManager webServer) {
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            if (connectToDB()) {
                stmt = connection.prepareCall("{call Get_Max_Char_Id()}");
                rs = stmt.executeQuery();
                if (rs.next()) {
                    webServer.maxCid = rs.getInt("maxCharId") + 1;
                } else {
                    webServer.maxCid = 0;
                }
                return true;
            }
        } catch (SQLException ex) {
            // TODO: 统计DB出错次数,当连续出错次数达到限值时,表示数据库故障
            System.err.println("GetMaxCid Error: " + ex.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println("GetMaxCid Close ResultSet Error: " + ex.getMessage());
                }
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    System.err.println("GetMaxCid Close Statement Error: " + ex.getMessage());
                }
            }
        }
        return false;
    }

    public PlayerInfo getPlayerInfoFromDB(String passport) {
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            if (connectToDB()) {
                stmt = connection.prepareCall("{call Get_Player_Info(?)}");
                stmt.setString(1, passport);
                rs = stmt.executeQuery();

                if (rs.next()) {
                    return new PlayerInfo(passport, rs.getInt("charId"), rs.getInt("forbiddenTalkEndTime"), rs.getInt("forbiddenLoginEndTime"));
                }
            }
        } catch (SQLException ex) {
            // TODO: 统计DB出错次数,当连续出错次数达到限值时,表示数据库故障
            System.err.println("GetPlayerInfoFromDB Error: " + ex.getMessage());
            return null;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println("GetPlayerInfoFromDB Close ResultSet Error: " + ex.getMessage());
                }
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    System.err.println("GetPlayerInfoFromDB Close Statement Error: " + ex.getMessage());
                }
            }
        }
        return null;
    }

    public boolean insertPlayerInfo(String passport, int maxCid) {
        CallableStatement stmt = null;
        int rs = 0;
        try {
            if (connectToDB()) {
                stmt = connection.prepareCall("{call Insert_Player_Info(?,?)}");
                stmt.setString(1, passport);
                stmt.setInt(2, maxCid);
                rs = stmt.executeUpdate();

                if (rs == 1) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            // TODO: 统计DB出错次数,当连续出错次数达到限值时,表示数据库故障
            System.err.println("InsertPlayerInfo Error: " + ex.getMessage());
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    System.err.println("InsertPlayerInfo Close Statement Error: " + ex.getMessage());
                }
            }
        }
        return false;
    }
}
