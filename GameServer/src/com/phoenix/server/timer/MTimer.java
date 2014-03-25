/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.server.timer;

/**
 *
 * @author rachel
 */
public interface MTimer {

    public boolean update(int difftime); //返回值表示该timer是否还有用，false表示无用（要清除）
}
