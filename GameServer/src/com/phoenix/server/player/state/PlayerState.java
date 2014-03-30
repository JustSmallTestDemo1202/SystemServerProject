/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.server.player.state;

import com.phoenix.common.message.serverRecvMessage.ServerRecvMessage;
import com.phoenix.server.player.Player;

/**
 *
 * @author rachel
 */
public interface PlayerState {

    public boolean handleMessage(Player player, ServerRecvMessage message);
}

/*
 * 登录 -> Login1PlayerState -> 角色数量为0 -> Login2PlayerState -> 收到创建角色请求  -> CreateingCharPlayerState -> 创建角色 -> UninitPlayerState -> 获取角色信息 -> NormalPlayerState
 *                              角色数量为1 -> Login2PlayerState -> 收到选择角色请求 -> -> -> -> -> -> -> -> -> -> -> -> -> -> ->UninitPlayerState -> 获取角色信息 -> NormalPlayerState
 */
