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
public class EnterStageAction extends AbstractAction{

    public EnterStageAction(Human human) {
        super(human);
    }
    public void process() {
        human.sendMessage(MessageBuilder.buildEnterStage(0, false, 0));
    }
}
