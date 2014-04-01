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
    public int indexId;             // 角色id（唯一)
    public int charId;              // 玩家id    
    public String charName;         // 玩家名
    public int charJob;             // 玩家职业
    public int charGender;          // 玩家性别
    public int charLevel;           // 玩家等级   
    public boolean inGame;      // 在线标志（也用于判断是否可清理出内存）（玩家上线时设为true，离线时设为false）
    public long lastVisitTime;  // 最近访问时间
    
    public BriefPlayerInfo(int indexId, int charId, String charName, int charJob, int charGender, int charLevel){
        this.indexId = indexId;
        this.charId = charId;        
        this.charName = charName;
        this.charJob = charJob;
        this.charGender = charGender;
        this.charLevel = charLevel;
    }
}
