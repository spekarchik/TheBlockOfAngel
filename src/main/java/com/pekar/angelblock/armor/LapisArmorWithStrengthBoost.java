package com.pekar.angelblock.armor;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;

public class LapisArmorWithStrengthBoost extends ModArmor
{
    protected LapisArmorWithStrengthBoost(ArmorMaterial material, EquipmentSlot equipmentSlot, String armorModelName)
    {
        super(material, equipmentSlot, armorModelName);
    }

    @Override
    public boolean isModifiedWithStrengthBooster()
    {
        return true;
    }
}
