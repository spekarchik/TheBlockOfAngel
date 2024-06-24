package com.pekar.angelblock.armor;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorMaterial;

public class LapisArmorWithSeaPower extends ModArmor
{
    protected LapisArmorWithSeaPower(Holder<ArmorMaterial> material, Type equipmentSlot, String armorModelName, int durabilityMultiplier)
    {
        super(material, equipmentSlot, armorModelName, durabilityMultiplier);
    }

    @Override
    public boolean isModifiedWithSeaPower()
    {
        return true;
    }
}
