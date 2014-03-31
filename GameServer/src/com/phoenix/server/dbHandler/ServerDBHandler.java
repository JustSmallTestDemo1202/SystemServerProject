/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.server.dbHandler;

import com.google.protobuf.CodedInputStream;
import com.mysql.jdbc.exceptions.jdbc4.MySQLNonTransientConnectionException;
import com.phoenix.common.messageQueue.ServerRecvMessageQueue;
import com.phoenix.protobuf.ExternalCommonProtocol.CSCreateCharProto;
import com.phoenix.protobuf.InternalCommonProtocol.DBPlayerDetailProto;
import com.phoenix.server.GameServer;
import com.phoenix.server.actor.charInfo.CharDetailInfo;
import com.phoenix.server.message.messageBuilder.S2SMessageBuilder;
import com.phoenix.server.social.BriefPlayerInfo;
import java.io.IOException;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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

    /**
     * 创建角色
     *
     * @param connection
     * @param playerId
     * @param createCharInfo
     * @return false表示数据库断连
     */
    public static boolean handleCreateChar(Connection connection, int playerId, CSCreateCharProto createCharInfo) {
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.prepareCall("{call Create_Char(?,?,?)}");
            stmt.setInt(1, playerId);                           // charId            
            stmt.setString(2, createCharInfo.getCharName());    // charName
            stmt.setInt(3, createCharInfo.getCharJob());        // charJob
            stmt.setInt(4, createCharInfo.getCharGender());     // charGender

            int indexId = -1;
            rs = stmt.executeQuery();
            if (rs.next()) {
                indexId = rs.getInt(1);
            }

            // 创建角色成功, 返回indexId的值
            ServerRecvMessageQueue.queue().offer(S2SMessageBuilder.buildCreateCharRetMessage(playerId, indexId));

        } catch (MySQLNonTransientConnectionException ex) {
            // 数据库断线
            System.err.println("DB Create Char MySQLNonTransientConnectionException Error: " + ex.getMessage());
            return false;
        } catch (SQLException ex) {
            System.err.println("DB Create Char SQLException Error: " + ex.getMessage());

            // 创建角色失败
            ServerRecvMessageQueue.queue().offer(S2SMessageBuilder.buildCreateCharRetMessage(playerId, -1));
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println("DB Create Char ResultSet Close SQLException Error: " + ex.getMessage());
                }
            }
            
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    System.err.println("DB Create Char Statement Close SQLException Error: " + ex.getMessage());
                }
            }
        }

        return true;
    }
    
    /**
     * 获取角色详细信息
     * @param connection
     * @param playerId
     * @param indexId
     * @return false表示数据库断连
     */
    public static boolean handleGetCharDetail(Connection connection, int playerId, int indexId) {
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.prepareCall("{call Get_Char_Detail(?)}");
            stmt.setInt(1, indexId);
            stmt.setInt(2, playerId);
            rs = stmt.executeQuery();

            CharDetailInfo charInfo = new CharDetailInfo();
            if (rs.next()) {
                charInfo.indexId = rs.getInt(1);
                charInfo.charId = rs.getInt(2);
                charInfo.charName = rs.getString(3);
                charInfo.charJob = rs.getInt(4);
                charInfo.charGender = rs.getInt(5);
                charInfo.charLevel = rs.getInt(6);                

                Blob charDetailBlob = rs.getBlob(7);
                if (charDetailBlob != null) {
                    try {
                        charInfo.playerDetail = DBPlayerDetailProto.getDefaultInstance().newBuilderForType().mergeFrom(CodedInputStream.newInstance(charDetailBlob.getBinaryStream())).build();
                    } catch (IOException ex) {
                       System.err.println("DB Get Char Detail DBPlayerDetailProto Convert Error: " + ex.getMessage());

                        // TODO: 获取信息失败
                    }
                }

                charInfo.totalOnlineTime = rs.getLong(8);

                Timestamp timestamp = rs.getTimestamp(9);
                charInfo.leaveTime = (timestamp != null) ? timestamp.getTime() : GameServer.INSTANCE.getCurrentTime();

                ServerRecvMessageQueue.queue().offer(S2SMessageBuilder.buildGetCharDetailRetMessage(charInfo.charId, charInfo.indexId, charInfo));
            } else {
               System.err.println("Player[" + playerId + "] not found in database.");
            }
        } catch (MySQLNonTransientConnectionException ex) {
            // 数据库断线
            System.err.println("DB Get Char Detail MySQLNonTransientConnectionException Error: " + ex.getMessage());
            return false;
        } catch (SQLException ex) {
            // TODO: 统计DB出错次数,当连续出错次数达到限值时,表示数据库故障
            System.err.println("DB Get Char Detail SQLException Error: " + ex.getMessage());

        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.err.println("DB Get Char Detail ResultSet Close SQLException Error: " + ex.getMessage());
                }
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    System.err.println("DB Get Char Detail Statement Close SQLException Error: " + ex.getMessage());
                }
            }
        }

        return true;
    }

    /**
     * 更新玩家角色详细数据
     * @param connection
     * @param playerId
     * @param indexId
     * @param info
     * @return false表示数据库连接
     */
    public static boolean handleUpdateCharDetailInfo(Connection connection, int playerId, int indexId, CharDetailInfo info) {
        
        CallableStatement stmt = null;
        try {
            stmt = connection.prepareCall("{call Update_Char_Detail_Info(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            stmt.setInt(1, indexId);
            stmt.setInt(2, playerId);
            stmt.setInt(3, info.charLevel);
            stmt.setInt(4, info.charExp);
            stmt.setLong(5, info.totalOnlineTime);
            stmt.setTimestamp(6, new Timestamp(info.leaveTime));            

            /*
            assert (info.detail != null);
            Blob detailBlob = connection.createBlob();
            byte[] bytes = info.detail.toByteArray();
            detailBlob.setBytes(1, bytes);
            stmt.setBlob(16, detailBlob);
            */
            stmt.executeUpdate();

            // 记录Debug日志
            System.out.println("Update char detail info : id[" + playerId + "].");
        } catch (MySQLNonTransientConnectionException ex) {
            // 数据库断线
            System.err.println("DB Update Char Detail MySQLNonTransientConnectionException Error: " + ex.getMessage());
            return false;
        } catch (SQLException ex) {
            // TODO: 统计DB出错次数,当连续出错次数达到限值时,表示数据库故障

            System.err.println("DB Update Char Detail SQLException Error: " + ex.getMessage());
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    System.err.println("DB Update Char Detail Statement Close Error: " + ex.getMessage());
                }
            }
        }        
        return true;
    }
     
}
