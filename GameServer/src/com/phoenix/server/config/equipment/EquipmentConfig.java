/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.server.config.equipment;

import com.phoenix.server.GameServer;
import com.phoenix.server.config.ConfigConsts;
import com.phoenix.utils.JSONHelper;

/**
 *
 * @author Administrator
 */
public class EquipmentConfig {

    public static EquipmentConfig INSTANCE = JSONHelper.parseFileNoException(GameServer.INSTANCE.serverId + "/" + ConfigConsts.EQUIPMENT_CONFIG_FILEPATH, EquipmentConfig.class);

    private EquipmentConfig() {

    }
    
    public void buildEquipmentTemplates() {
        EquipmentTemplates.INSTANCE.init(equipmentTemplates);
    }

    public EquipmentTemplate[] equipmentTemplates;
}
