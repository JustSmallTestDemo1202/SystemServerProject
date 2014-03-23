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
    public static final int DB_KEEPALIVE_INTERVAL = 3600000;                // 3600000ms,保证数据库连接不中断
    public static final String DB_KEEPALIVE_TEST_STATEMENT = "SELECT 1 FROM INFORMATION_SCHEMA.VIEWS WHERE table_name IS NULL";
}
