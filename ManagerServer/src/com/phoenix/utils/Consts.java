/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.utils;

/**
 *
 * @author rachel
 */
public class Consts {    

    public static final int GETSESSION_ERR_FORBIDDEN_LOGIN = -1;           //玩家被禁止登陆
    public static final int GETSESSION_ERR_SERVER = -2;                    //系统错误
    public static final int ERR_SERVER = -1;                               //获取排名列表错误
    public static final int PARAMETER_SERVER = -100;
    public static final int ERR_SERVER_NOTHISPLAYER = -3;                  //根据passport没有找到cid。passport错误或者db查询失败
    
    public static int PRODUCE_KEY_MAX_RETRY_TIMES = 2;
    
    public static final int CHECK_SESSION_ERR_SESSION = -1;    //没有这个session
}
