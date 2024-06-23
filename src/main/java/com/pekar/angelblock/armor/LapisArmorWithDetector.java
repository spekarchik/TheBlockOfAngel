package com.pekar.angelblock.armor;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorMaterial;

public class LapisArmorWithDetector extends ModArmor
{
    protected LapisArmorWithDetector(Holder<ArmorMaterial> material, Type equipmentSlot, String armorModelName)
    {
        super(material, equipmentSlot, armorModelName);
    }

    @Override
    public boolean isModifiedWithDetector()
    {
        return true;
    }
}
