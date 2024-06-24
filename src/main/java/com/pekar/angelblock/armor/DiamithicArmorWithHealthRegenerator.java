package com.pekar.angelblock.armor;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorMaterial;

public class DiamithicArmorWithHealthRegenerator extends ModArmor
{
    protected DiamithicArmorWithHealthRegenerator(ModArmorMaterial material, Type equipmentSlot)
    {
        super(material, equipmentSlot);
    }

    @Override
    public boolean isModifiedWithHealthRegenerator()
    {
        return true;
    }
}
