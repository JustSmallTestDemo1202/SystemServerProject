/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.common.web;

import com.phoenix.common.database.DBHandler;
import com.phoenix.server.ManagerServer;
import com.phoenix.server.info.PlayerInfo;
import com.phoenix.utils.Consts;
import com.phoenix.utils.RandomGenerator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.TreeMap;

/**
 *
 * @author rachel
 */
public class PlayerManager {

    private HashMap<String, String> playersToSessMap;               //PlayerInfo => sessionid
    private TreeMap<String, PlayerInfo> sessToPlayerInfoMap;        //sessionid => PlayerInfo
    public int maxCid;

    public boolean init(DBHandler dbHandler) {
        playersToSessMap = new HashMap<>();
        sessToPlayerInfoMap = new TreeMap<>();
        return dbHandler.getMaxCid(this);
    }

    synchronized public PlayerInfo GetPlayerInfoByPassport(String thirdPartyUserId) {
        String sessionId = playersToSessMap.get(thirdPartyUserId);
        if (sessionId != null) {
            PlayerInfo info = sessToPlayerInfoMap.get(sessionId);
            if (info != null) {
                if (info.time >= System.currentTimeMillis()) {
                    info.time = System.currentTimeMillis() + ManagerServer.INSTANCE.sessionPeriod;
                    return info;
                }
            } else {
                System.err.println("playersToSessMap contanis but sessToPlayerInfoMap not contains sesionid " + sessionId);
            }
        }
        return null;
    }

    synchronized public PlayerInfo newPlayerInfo(DBHandler dbHandler, String passport) {
        PlayerInfo playerInfo = dbHandler.getPlayerInfoFromDB(passport);
        if (playerInfo == null) {
            if (dbHandler.insertPlayerInfo(passport, maxCid)) {
                playerInfo = new PlayerInfo(passport, maxCid, 0, 0);
                maxCid++;
            }
        }
        if (playerInfo != null) {
            long now = System.currentTimeMillis();
            playerInfo.time = now + ManagerServer.INSTANCE.sessionPeriod;
            String sessionId = getSessionId();
            int retry = 0;
            while (retry++ < Consts.PRODUCE_KEY_MAX_RETRY_TIMES) {
                if (!sessToPlayerInfoMap.containsKey(sessionId)) {
                    playerInfo.sessionId = sessionId;
                    playersToSessMap.put(playerInfo.userPassportId, playerInfo.sessionId);
                    sessToPlayerInfoMap.put(playerInfo.sessionId, playerInfo);
                    if (sessToPlayerInfoMap.size() >= ManagerServer.INSTANCE.maxSessionNum) {
                        for (Iterator<PlayerInfo> it = sessToPlayerInfoMap.values().iterator(); it.hasNext();) {
                            PlayerInfo pi = it.next();
                            if (pi.time <= now) {
                                playersToSessMap.remove(pi.userPassportId);
                                it.remove();
                            }
                        }
                    }
                    return playerInfo;
                }
                sessionId = getSessionId();
            }
        }
        return null;
    }

    synchronized public PlayerInfo GetPlayerInfoBySessinoId(String sessionid) {
        PlayerInfo info = sessToPlayerInfoMap.get(sessionid);
        if (info != null) {
            if (info.time >= System.currentTimeMillis()) {
                return info;
            } else {
                System.err.println("[" + new Date(System.currentTimeMillis()) + "] session [" + sessionid + "] out of time");
            }
        } else {
            System.err.println("[" + new Date(System.currentTimeMillis()) + "] session [" + sessionid + "] not found");
        }
        return null;
    }

    synchronized public void logout(String passport) {
        String sessionId = playersToSessMap.get(passport);
        if (sessionId != null) {
            sessToPlayerInfoMap.remove(sessionId);
        }
        playersToSessMap.remove(passport);
    }

    private String getSessionId() {
        String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = RandomGenerator.INSTANCE.generator;
        for (int i = 0; i < 12; i++) {
            sb.append(base.charAt(random.nextInt(base.length())));
        }
        return sb.toString();
    }
}
