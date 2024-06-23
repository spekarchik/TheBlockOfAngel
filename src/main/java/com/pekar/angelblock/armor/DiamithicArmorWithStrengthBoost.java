package com.pekar.angelblock.armor;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorMaterial;

public class DiamithicArmorWithStrengthBoost extends ModArmor
{
    protected DiamithicArmorWithStrengthBoost(Holder<ArmorMaterial> material, Type equipmentSlot, String armorModelName)
    {
        super(material, equipmentSlot, armorModelName);
    }

    @Override
    public boolean isModifiedWithStrengthBooster()
    {
        return true;
    }
}
