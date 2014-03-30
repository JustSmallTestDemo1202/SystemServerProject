/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.icee.myth.action;

import com.icee.myth.model.actor.Human;
import com.icee.myth.network.builder.MessageBuilder;

/**
 *
 * @author lidonglin
 */
public class GMAddEmergyAction extends AbstractAction{

    public GMAddEmergyAction(Human human) {
        super(human);
    }
    public void process() {
        human.sendMessage(MessageBuilder.buildGMAddEmergy());
    }
}
