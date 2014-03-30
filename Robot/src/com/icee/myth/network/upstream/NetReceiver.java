/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.icee.myth.network.upstream;

import com.icee.myth.model.actor.Human;
import com.icee.myth.network.builder.MessageBuilder;
import com.icee.myth.network.message.Message;
import com.icee.myth.network.protobufmessage.ProtobufMessageType;
import com.icee.myth.protobuf.ExternalCommonProtocol.EnterGameRetProto;
import com.icee.myth.protobuf.ExternalCommonProtocol.IntValueProto;
import com.icee.myth.utils.RandomGenerator;
import org.jboss.netty.util.internal.LinkedTransferQueue;

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
                case ProtobufMessageType.S2C_NOCHARRET:{
                    //无角色 - 创建角色
                    human.sendMessage(MessageBuilder.buildCreateChar(RandomGenerator.INSTANCE.generator.nextInt(3), human.name));
                    break;
                }
                case ProtobufMessageType.S2C_ENTERGAMERET:{
                    EnterGameRetProto enterGameRetProto = (EnterGameRetProto)msg.content;
                    //初始化角色信息
                    if(enterGameRetProto.getResult() == 0){
                        human.init(enterGameRetProto.getEnterGameChar());
                        human.sendMessage(MessageBuilder.buildIgnoreGuideStep());
                    }
                    break;
                }

                case ProtobufMessageType.S2C_ENERGY_CHANGE: {
                    IntValueProto energyProto = (IntValueProto)msg.content;
                    human.energy = energyProto.getValue();
                    break;
                }

                case ProtobufMessageType.S2C_STAGE_STATE:{
                    break;
                }

                case ProtobufMessageType.S2C_PVEBATTLERESULT: {
                    break;
                }

                case ProtobufMessageType.S2C_STAGE_LEAVE: {
                    break;
                }
            }
            msg = messageQueue.poll();
        }
    }
}
