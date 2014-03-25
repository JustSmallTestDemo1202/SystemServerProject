/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.server.timer;

import com.phoenix.server.actor.Human;

/**
 *
 * @author rachel
 */
public class HumanUpdateTimer implements MTimer {

    private final Human human;

    public HumanUpdateTimer(Human human) {
        this.human = human;
    }

    @Override
    public boolean update(int difftime) {        
        this.human.update(difftime);
        return true;        
    }
}
