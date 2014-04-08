/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phoenix.server.config.equipment;

import java.util.HashMap;

/**
 * 
 * @author Administrator
 */
public class EquipmentTemplates {

    public final HashMap<Integer, EquipmentTemplate> templates = new HashMap<>();

    public static final EquipmentTemplates INSTANCE = new EquipmentTemplates();

    private EquipmentTemplates() {
    }

    protected void init(EquipmentTemplate[] equipmentTemplates) {
        for (EquipmentTemplate equipmentTemplate : equipmentTemplates) {
            templates.put(equipmentTemplate.equipId, equipmentTemplate);
        }
    }

    public EquipmentTemplate getEquipmentTemplate(int id) {
        return templates.get(id);
    }
}
