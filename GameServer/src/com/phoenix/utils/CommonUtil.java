/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.utils;

/**
 *
 * @author rachel
 */
public class CommonUtil {

    public static boolean ValidateIPAddress(String ipAddress) {
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
    }
}
