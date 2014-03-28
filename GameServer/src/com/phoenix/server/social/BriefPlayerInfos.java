/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.server.social;

import com.phoenix.server.GameServer;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author rachel
 */
public class BriefPlayerInfos {

    private final HashMap<Integer, BriefPlayerInfo> playerId2InfoMap;
    private long lastRefreshTime;

    public BriefPlayerInfos() {
        playerId2InfoMap = new HashMap<Integer, BriefPlayerInfo>();
    }

    public void init(long curTime) {
        lastRefreshTime = curTime;
    }

    public BriefPlayerInfo get(int playerId) {
        BriefPlayerInfo briefPlayerInfo = playerId2InfoMap.get(playerId);

        if (briefPlayerInfo != null) {
            briefPlayerInfo.lastVisitTime = GameServer.INSTANCE.getCurrentTime();
        }

        return briefPlayerInfo;
    }

    public void addAll(LinkedList<BriefPlayerInfo> briefPlayerInfos) {
        for (BriefPlayerInfo briefPlayerInfo : briefPlayerInfos) {
            BriefPlayerInfo tmpBriefPlayerInfo = playerId2InfoMap.get(briefPlayerInfo.id);
            if (tmpBriefPlayerInfo != null) {
                briefPlayerInfo.inGame = tmpBriefPlayerInfo.inGame;
            } else {
                briefPlayerInfo.inGame = false;
                briefPlayerInfo.lastVisitTime = GameServer.INSTANCE.getCurrentTime();

                playerId2InfoMap.put(briefPlayerInfo.id, briefPlayerInfo);
            }
        }
    }

    public void addBriefPlayerInfo(int playerId, String name, int job, int gender, int level, boolean inGame) {
        BriefPlayerInfo briefPlayerInfo = playerId2InfoMap.get(playerId);

        if (briefPlayerInfo == null) {
            briefPlayerInfo = new BriefPlayerInfo(playerId, name, job, gender, level);
            briefPlayerInfo.inGame = inGame;
            briefPlayerInfo.lastVisitTime = GameServer.INSTANCE.getCurrentTime();

            playerId2InfoMap.put(playerId, briefPlayerInfo);

            // 加入等级表
            // briefPlayerInfosOfLevels[briefPlayerInfo.level - 1].addBriefPlayerInfo(briefPlayerInfo);
        } else {
            System.err.println("Add brief player[" + playerId + "] info error because info already exist.");
        }
    }

    public boolean setOffGame(int playerId) {
        BriefPlayerInfo briefPlayerInfo = playerId2InfoMap.get(playerId);

        if (briefPlayerInfo != null) {
            briefPlayerInfo.inGame = false;
            briefPlayerInfo.lastVisitTime = GameServer.INSTANCE.getCurrentTime();
            return true;
        }

        return false;
    }

    public boolean setInGame(int playerId) {
        BriefPlayerInfo briefPlayerInfo = playerId2InfoMap.get(playerId);

        if (briefPlayerInfo != null) {
            briefPlayerInfo.inGame = true;
            briefPlayerInfo.lastVisitTime = GameServer.INSTANCE.getCurrentTime();
            return true;
        }

        return false;
    }
}
