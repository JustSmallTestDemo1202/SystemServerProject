/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.icee.myth.client;

import com.icee.myth.config.MiscConfig;
import com.icee.myth.model.actor.Human;
import com.icee.myth.utils.Consts;
import com.icee.myth.utils.JSONHelper;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author lidonglin
 */
public class Client {
    public ArrayList<Human> humanList;

    public Client(){
        humanList = new ArrayList<Human>();
    }

    private void init() {
        for (int i = MiscConfig.INSTANCE.robotStart; i < MiscConfig.INSTANCE.robotEnd; i++) {
            Human human = new Human(MiscConfig.INSTANCE.userNamePrefix + i, MiscConfig.INSTANCE.userNamePrefix + i);
            human.init(i);
            humanList.add(human);
        }
        loadConfig();
    }

    private void loadConfig() {
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Client client = new Client();
        client.init();
        
        long realCurrTime , realPrevTime ;
        realCurrTime = realPrevTime = System.currentTimeMillis();
        int diffTime;
        while(true) {
            realPrevTime = System.currentTimeMillis();
            for (Iterator it = client.humanList.iterator(); it.hasNext();) {
                Human human = (Human) it.next();
                human.update(realCurrTime);
            }
            realCurrTime = System.currentTimeMillis();
            diffTime = (int) (realCurrTime - realPrevTime);

            if (diffTime < MiscConfig.INSTANCE.diffInterval) {
                try {
                    Thread.sleep(MiscConfig.INSTANCE.diffInterval - diffTime);
                } catch (InterruptedException ex) {
                }
            }
        }         
    }
}
