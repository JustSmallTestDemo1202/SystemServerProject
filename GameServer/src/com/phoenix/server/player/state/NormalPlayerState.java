/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.server.player.state;

import com.phoenix.common.message.serverRecvMessage.ServerRecvMessage;
import com.phoenix.server.actor.Human;
import com.phoenix.server.player.MapPlayer;
import com.phoenix.server.player.Player;

/**
 *
 * @author rachel
 */
public class NormalPlayerState implements PlayerState {

    public final static NormalPlayerState INSTANCE = new NormalPlayerState();

    private NormalPlayerState() {
    }

    @Override
    public boolean handleMessage(Player player, ServerRecvMessage message) {
        MapPlayer p = (MapPlayer) player;
        int playerId = p.getId();
        Human human = p.human;

        ServerRecvMessage.ServerRecvMessageType msgType = message.getType();
        switch (msgType) {
            case MAP_CONTSIGN_CONSECUTIVE_REWARD_RECEIVE: {
                //human.contSign.receiveCumulativeSignReward();
                return true;
            }
            case MAP_CONTSIGN_CUMULATIVE_REWARD_RECEIVE: {
                //ContSignConsecutiveRewardReceiveMessage contSignReceiveConsecutiveSignRewardMessage = (ContSignConsecutiveRewardReceiveMessage) message;
                //human.contSign.receiveConsecutiveSignReward(contSignReceiveConsecutiveSignRewardMessage.day);
                return true;
            }
            case MAP_VIP_GIFT_RECEIVE: {
                //VipGiftReceiveMessage vipGiftReceiveMessage = (VipGiftReceiveMessage) message;
                //human.vipGift.receive(vipGiftReceiveMessage.vip);
                return true;
            }
        }
        return true;
    }
}
