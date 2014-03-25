/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.server.player;

import com.phoenix.common.message.serverRecvMessage.ServerRecvMessage;
import com.phoenix.server.GameServer;
import com.phoenix.server.player.state.DummyPlayerState;
import com.phoenix.server.player.state.PlayerState;

/**
 *
 * @author rachel
 */
public abstract class AbstractPlayer implements Player {
    protected final int id;
    public PlayerState state = DummyPlayerState.INSTANCE; //初始化player时应该将state设置为正确的初始状态，当state为DummyState时，player应被清除
    protected long lastActiveTime;

    public AbstractPlayer(int id, PlayerState state) {
        this.id = id;
        this.state = state;
        lastActiveTime = GameServer.INSTANCE.getCurrentTime();
    }

    @Override
    public int getId() {
        return id;
    }
    
    @Override
    public long getLastActiveTime() {
        return lastActiveTime;
    }

    @Override
    public boolean handleMessage(ServerRecvMessage message) {
        // 忽略结束状态后的消息
        if (state != DummyPlayerState.INSTANCE) {
            if(state.handleMessage(this, message)) {
                lastActiveTime = GameServer.INSTANCE.getCurrentTime();
                return true;
            }
        }
        return false;
    }
}
