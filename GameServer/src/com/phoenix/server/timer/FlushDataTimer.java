/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.server.timer;

import com.phoenix.server.player.MapPlayer;
import com.phoenix.utils.Consts;

/**
 *
 * @author rachel
 */
public class FlushDataTimer {

    public int actionTime = 0;
    private final MapPlayer player;

    public FlushDataTimer(MapPlayer player) {
        this.player = player;
    }

    public void update(int difftime) {
        actionTime += difftime;
        if (actionTime < Consts.FLUSH_PLAYER_DATA_PERIOD) {
            return;
        }

        player.human.flushData();

        actionTime -= Consts.FLUSH_PLAYER_DATA_PERIOD;
    }
}
