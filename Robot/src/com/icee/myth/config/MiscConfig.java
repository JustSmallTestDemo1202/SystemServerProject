/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.icee.myth.config;

import com.icee.myth.utils.Consts;
import com.icee.myth.utils.JSONHelper;

/**
 *
 * @author lidonglin
 */
public class MiscConfig {
    public String managerIp = "192.168.100.202";
    public int managerPort = 8082;
    public int serverId = 0;
    public String userNamePrefix = "robot";
    public int robotStart = 0;
    public int robotEnd = 10;
    public int diffInterval = 100;      //每diffInterval毫秒一个循环
    public int robotIntervalTime = 5;

    public static MiscConfig INSTANCE = JSONHelper.parseFileNoException(Consts.MISCCONFIG_FILEPATH, MiscConfig.class);
    
    private MiscConfig(){
    }
}
