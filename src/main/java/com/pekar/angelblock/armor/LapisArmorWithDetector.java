package com.pekar.angelblock.armor;

import net.minecraft.world.item.ArmorMaterial;

public class LapisArmorWithDetector extends ModArmor
{
    protected LapisArmorWithDetector(ArmorMaterial material, Type equipmentSlot, String armorModelName)
    {
        super(material, equipmentSlot, armorModelName);
    }

    @Override
    public boolean isModifiedWithDetector()
    {
        return true;
    }
}
