/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.server.social;

/**
 *
 * @author rachel
 */
public class BriefPlayerInfo {

    public int id;              // 玩家id
    public String name;         // 玩家名
    public int level;           // 玩家等级
    public int leaderCardId;    // 队长卡片id
    public int leaderCardLevel; // 队长卡片等级
    public boolean inGame;  // 在线标志（也用于判断是否可清理出内存）（玩家上线时设为true，离线时设为false）
    public long lastVisitTime;  // 最近访问时间
}
