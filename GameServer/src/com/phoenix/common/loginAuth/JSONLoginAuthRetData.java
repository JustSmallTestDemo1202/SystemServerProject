/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.common.loginAuth;

/**
 *
 * @author rachel
 */
public class JSONLoginAuthRetData {

    public int result;                      // 校验结果
    public boolean auth;                    // 认证标志
    public int privilege;                   // 特权等级
    public int id;                          // 用户id
    public String passport;                 //第三方passport
    public int forbiddenEndTalkTime;        //禁言结束时间
    public int forbiddenEndLoginTime;       //禁言登录时间
}
