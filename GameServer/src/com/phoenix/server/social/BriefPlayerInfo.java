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
    public int job;             // 玩家职业
    public int gender;          // 玩家性别
    public int level;           // 玩家等级   
    public boolean inGame;      // 在线标志（也用于判断是否可清理出内存）（玩家上线时设为true，离线时设为false）
    public long lastVisitTime;  // 最近访问时间
    
    public BriefPlayerInfo(int id, String name, int job, int gender, int level){
        this.id = id;
        this.name = name;
        this.job = job;
        this.gender = gender;
        this.level = level;
    }
}
