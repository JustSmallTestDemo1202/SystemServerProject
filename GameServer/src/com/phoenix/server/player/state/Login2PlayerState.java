/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.server.player.state;

import com.phoenix.common.message.serverRecvMessage.ServerRecvMessage;
import com.phoenix.common.messageQueue.DBMessageQueue;
import com.phoenix.protobuf.ExternalCommonProtocol.CSCreateCharProto;
import com.phoenix.server.GameServer;
import com.phoenix.server.message.messageBuilder.DBMessageBuilder;
import com.phoenix.server.message.messageBuilder.S2CMessageBuilder;
import com.phoenix.server.message.serverRecvMessage.CreateCharMessage;
import com.phoenix.server.message.serverRecvMessage.SelectCharMessage;
import com.phoenix.server.player.MapPlayer;
import com.phoenix.server.player.Player;

/**
 *
 * @author rachel
 */
public class Login2PlayerState implements PlayerState {

    public final static Login2PlayerState INSTANCE = new Login2PlayerState();

    private Login2PlayerState() {
    }

    @Override
    public boolean handleMessage(Player player, ServerRecvMessage message) {
        // 说明：当遇到不该在此状态处理的消息时，说明玩家状态异常，将玩家状态设置为空，
        // 告诉主线程切断玩家连接并清理玩家上下文
        MapPlayer p = (MapPlayer) player;
        int playerId = p.getId();
        ServerRecvMessage.ServerRecvMessageType msgType = message.getType();
        switch (msgType) {
            case MAP_CREATE_CHAR: {
                // 处理创建角色请求
                // TODO: 验证是否允许创建角色，以及所创建角色信息的正确性，通过验证后向产生CreateCharDBMessage到DB message queue
                CreateCharMessage createCharMsg = (CreateCharMessage) message;
                if (isValidate(createCharMsg.charInfo)) {
                    System.out.println("Player[" + playerId + "] start create char.");

                    DBMessageQueue.queue().offer(DBMessageBuilder.buildCreateCharDBMessage(playerId, createCharMsg.charInfo));
                    p.state = CreatingCharPlayerState.INSTANCE;
                } else {
                    // TODO: 回复客户端无法创建角色
                    p.channelContext.write(S2CMessageBuilder.buildCreateCharError());
                }
                return true;
            }
            case MAP_SELECT_CHAR: {
                SelectCharMessage selectCharMsg = (SelectCharMessage) message;
                if (p.human != null && p.human.indexId == selectCharMsg.charInfo.getIndexId()) { // 玩家数据已关联
                    if (p.human.mapPlayer != null) {
                        GameServer.INSTANCE.enterGame(p);
                        p.state = NormalPlayerState.INSTANCE;
                    } else {
                        System.err.println("Player[" + playerId + "] Login2State human.mapPlayer == null error.");
                    }
                } else { // 玩家数据未关联
                    GameServer.INSTANCE.loadPlayerData(playerId);
                    p.state = UninitPlayerState.INSTANCE;
                }
                return true;
            }
            default: {
                // 错误消息类型（不作处理），记录日志
                System.err.println("Player[" + playerId + "] Login3State handle error message type[" + msgType + "].");

                return false;
            }
        }
    }

    public boolean isValidate(CSCreateCharProto charInfo) {
        // 验证玩家输入信息

        // 验证名字长度
        String name = charInfo.getCharName();
        int nameLenth = name.length();
        if ((nameLenth < 2) || (nameLenth > 8)) {
            System.err.println("Player Name Too Long or Too Short");
            return false;
        }

        // 验证职业
        int jobId = charInfo.getCharJob();
        return jobId >= 0 && jobId < 5;
    }
}
