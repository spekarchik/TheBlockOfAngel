package com.pekar.angelblock.armor;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;

public class DiamithicArmorWithHealthRegenerator extends ModArmor
{
    protected DiamithicArmorWithHealthRegenerator(ArmorMaterial material, EquipmentSlot equipmentSlot, String armorModelName)
    {
        super(material, equipmentSlot, armorModelName);
    }

    @Override
    public boolean isModifiedWithHealthRegenerator()
    {
        return true;
    }
}
