/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icee.myth.utils;

/**
 *
 * @author chencheng
 */
public class StackTraceUtil {

    public static String getStackTrace(Throwable t) {
        StringBuilder result = new StringBuilder();
        result.append(t.toString());
        String NEW_LINE = System.getProperty("line.separator");
        result.append(NEW_LINE);
        
        for (StackTraceElement element : t.getStackTrace()) {
            result.append(element);
            result.append(NEW_LINE);
        }
        return result.toString();
    }
}
