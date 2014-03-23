/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.server.info;

/**
 *
 * @author rachel
 */
public class PlayerInfo {

    public String userPassportId;
    public int charId;
    public boolean auth;                       //是否实名验证
    public int forbiddenEndTalkTime;
    public int forbiddenEndLoginTime;
    public String clientIp;
    public String sessionId;
    public long time;

    public PlayerInfo(String passport) {
        this.userPassportId = passport;
    }

    public PlayerInfo(String thirdPartyUserId, int cid, int forbiddenEndTalkTime, int forbiddenEndLoginTime) {
        this.userPassportId = thirdPartyUserId;
        this.charId = cid;
        this.forbiddenEndTalkTime = forbiddenEndTalkTime;
        this.forbiddenEndLoginTime = forbiddenEndLoginTime;
    }
}
