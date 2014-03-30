package com.icee.myth.action;

import com.icee.myth.model.actor.Human;
import com.icee.myth.network.builder.MessageBuilder;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lidonglin
 */
public class ContinueStageAction extends AbstractAction {

    public ContinueStageAction(Human human) {
        super(human);
    }
    public void process() {
         human.sendMessage(MessageBuilder.buildContinueStage());
    }

}
