/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rachel
 */
public class CommonUtil {

    /**
     * 判断是否是ip地址
     *
     * @param ipAddress
     * @return true：是ip地址；false：不是ip地址
     */
    public static boolean ValidateIPAddress(String ipAddress) {
        try {
            String[] parts = ipAddress.split("\\.");

            if (parts.length != 4) {
                return false;
            }

            for (String s : parts) {
                int i = Integer.parseInt(s);
                if ((i < 0) || (i > 255)) {
                    return false;
                }
            }

            return true;
        } catch (Exception ex) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static boolean ValidateLocalAddress(String ipAddress) {
        try {
            Enumeration<NetworkInterface> netInterfaces = null;
            netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = netInterfaces.nextElement();
                Enumeration<InetAddress> ips = ni.getInetAddresses();
                while (ips.hasMoreElements()) {
                    if (ipAddress.compareTo(ips.nextElement().getHostAddress()) == 0) {
                        return true;
                    }
                }
            }
        } catch (SocketException ex) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }
}
