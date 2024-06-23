package com.pekar.angelblock.armor;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorMaterial;

public class DiamithicArmorWithStrengthBoostAndLevitation extends DiamithicArmorWithStrengthBoost
{
    protected DiamithicArmorWithStrengthBoostAndLevitation(Holder<ArmorMaterial> material, Type equipmentSlot, String armorModelName)
    {
        super(material, equipmentSlot, armorModelName);
    }

    @Override
    public boolean isModifiedWithLevitation()
    {
        return true;
    }
}
