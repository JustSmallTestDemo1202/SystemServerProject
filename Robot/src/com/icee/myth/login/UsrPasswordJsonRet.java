/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.icee.myth.login;

import com.google.gson.JsonSyntaxException;
import com.icee.myth.config.MiscConfig;
import com.icee.myth.utils.JSONHelper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author lidonglin
 */
public class UsrPasswordJsonRet {
    public int result;
    public String sessionid;
    public String serverip;
    public int serverport;
    public String serverstatus;

    public static UsrPasswordJsonRet CheckAccount(int passport) {
        //String url = "http://" + MiscConfig.INSTANCE.managerIp + ":" + MiscConfig.INSTANCE.managerPort + "/session/get";
        String url = "http://127.0.0.1:8083/session/get";
        HttpURLConnection con = null;
        BufferedReader in = null;
        UsrPasswordJsonRet retData = new UsrPasswordJsonRet();
        try {
            con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);

            String postParam = "serverid=1&passport=" + passport + "&auth=0";
            con.getOutputStream().write(postParam.getBytes("UTF-8"));
            con.getOutputStream().flush();
            con.getOutputStream().close();

            //获取结果
            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String ret = in.readLine();
            try {
                retData = JSONHelper.parseString(ret, UsrPasswordJsonRet.class);
            } catch (JsonSyntaxException jse) {
                //TODO: 记录JSON parse出错
                retData.result = -10001;
            }
        } catch (IOException ex) {
            retData.result = -10002;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                }
            }
            if (con != null) {
                con.disconnect();
            }
        }
        return retData;
    }
}
