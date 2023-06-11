package com.pekar.angelblock.armor;

import net.minecraft.world.item.ArmorMaterial;

public class LapisArmorWithStrengthBoost extends ModArmor
{
    protected LapisArmorWithStrengthBoost(ArmorMaterial material, Type equipmentSlot, String armorModelName)
    {
        super(material, equipmentSlot, armorModelName);
    }

    @Override
    public boolean isModifiedWithStrengthBooster()
    {
        return true;
    }
}
