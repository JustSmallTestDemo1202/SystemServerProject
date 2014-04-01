/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.icee.myth.network.upstream;

import com.icee.myth.model.actor.Human;
import com.icee.myth.network.builder.MessageBuilder;
import com.icee.myth.network.message.Message;
import com.icee.myth.network.protobufmessage.ProtobufMessageType;
import com.icee.myth.utils.RandomGenerator;
import com.phoenix.protobuf.ExternalCommonProtocol.SCEnterGameRetProto;
import java.util.concurrent.LinkedTransferQueue;
//import org.jboss.netty.util.internal.LinkedTransferQueue;

/**
 *
 * @author lidonglin
 */
public class NetReceiver {
    public Human human;
    public LinkedTransferQueue<Message> messageQueue = new LinkedTransferQueue<Message>();

    public NetReceiver(Human human){
        this.human = human;
        messageQueue = new LinkedTransferQueue<Message>();
    }
    
    public void handleMessage() {
        Message msg = messageQueue.poll();
        while (msg != null) {
            switch (msg.id) {
                case ProtobufMessageType.S2C_NO_CHAR_RET:{
                    //无角色 - 创建角色
                    human.sendMessage(MessageBuilder.buildCreateChar(RandomGenerator.INSTANCE.generator.nextInt(3), human.name));
                    break;
                }
                case ProtobufMessageType.S2C_ENTER_GAME_RET:{
                    SCEnterGameRetProto enterGameRetProto = (SCEnterGameRetProto)msg.content;
                    //初始化角色信息
                    if(enterGameRetProto.getResult() == 0){
                        human.init(enterGameRetProto.getEnterGameChar());
                        //human.sendMessage(MessageBuilder.buildIgnoreGuideStep());
                    }
                    break;
                }                
            }
            msg = messageQueue.poll();
        }
    }
}
