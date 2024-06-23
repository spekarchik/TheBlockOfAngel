package com.pekar.angelblock.armor;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorMaterial;

public class LapisArmorWithSeaPower extends ModArmor
{
    protected LapisArmorWithSeaPower(Holder<ArmorMaterial> material, Type equipmentSlot, String armorModelName)
    {
        super(material, equipmentSlot, armorModelName);
    }

    @Override
    public boolean isModifiedWithSeaPower()
    {
        return true;
    }
}
