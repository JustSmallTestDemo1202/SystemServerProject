/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.phoenix.server.actor.charInfo;

import com.phoenix.protobuf.InternalCommonProtocol.DBPlayerDetailProto;

/**
 *
 * @author Administrator
 */
public class DetailCharInfo {
    public int charId;                  // 玩家id
    public String charName;             // 玩家名
    public int charJob;                 // 玩家职业
    public int charGender;              // 玩家性别
    public int charLevel;               // 玩家等级 
    public int charExp;                 // 玩家经验
    
    public DBPlayerDetailProto playerDetail;    
}
