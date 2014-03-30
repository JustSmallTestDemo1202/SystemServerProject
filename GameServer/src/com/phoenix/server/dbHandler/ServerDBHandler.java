/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.server.dbHandler;

import com.mysql.jdbc.exceptions.jdbc4.MySQLNonTransientConnectionException;
import com.phoenix.common.messageQueue.ServerRecvMessageQueue;
import com.phoenix.server.message.messageBuilder.S2SMessageBuilder;
import com.phoenix.server.social.BriefPlayerInfo;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rachel
 */
public class ServerDBHandler {

    /**
     * 获取帐号角色数
     *
     * @param connection
     * @param playerId
     * @return false 表示数据库已断连
     */
    public static boolean handleGetCharNum(Connection connection, int playerId) {
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.prepareCall("{call Get_Char_Num(?)}");

            stmt.setInt(1, playerId);
            rs = stmt.executeQuery();

            int charNum = -1;
            if (rs.next()) {
                charNum = rs.getInt(1);
            }
            
            List<BriefPlayerInfo> playerDetail = new ArrayList<>();
            if (charNum > 0) {
                stmt = connection.prepareCall("{call Get_Char_Brief(?)}");

                stmt.setInt(1, playerId);
                rs = stmt.executeQuery();
                
                if (rs.next()) {
                    BriefPlayerInfo playerInfo = new BriefPlayerInfo(
                            rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4),
                            rs.getInt(5), rs.getInt(6), rs.getInt(7));
                    playerDetail.add(playerInfo);
                }
            }

            ServerRecvMessageQueue.queue().offer(S2SMessageBuilder.buildCharNumMessage(playerId, charNum, playerDetail));
        } catch (MySQLNonTransientConnectionException ex) {
            // 数据库断线
            System.err.println("DB Get Char Num MySQLNonTransientConnectionException Error: " + ex.getMessage());
            return false;
        } catch (SQLException ex) {
            // 其他异常
            System.err.println("DB Get Char Num SQLException Error: " + ex.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println("DB Get Char Num ResultSet Close SQLException Error: " + ex.getMessage());
                }
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    System.err.println("DB Get Char Num Statement Close SQLException Error: " + ex.getMessage());
                }
            }
        }

        return true;
    }
}
