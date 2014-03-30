/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.icee.myth.action;

import com.icee.myth.model.actor.Human;

/**
 *
 * @author lidonglin
 */
public abstract class AbstractAction implements IAction{
    public final Human human;
    public AbstractAction(Human human){
        this.human = human;
    }
}
