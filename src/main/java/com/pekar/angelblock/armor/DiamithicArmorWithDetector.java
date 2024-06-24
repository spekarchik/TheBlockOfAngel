package com.pekar.angelblock.armor;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorMaterial;

public class DiamithicArmorWithDetector extends ModArmor
{
    protected DiamithicArmorWithDetector(ModArmorMaterial material, Type equipmentSlot)
    {
        super(material, equipmentSlot);
    }

    @Override
    public boolean isModifiedWithDetector()
    {
        return true;
    }
}
