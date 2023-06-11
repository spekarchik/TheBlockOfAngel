package com.pekar.angelblock.armor;

import net.minecraft.world.item.ArmorMaterial;

public class DiamithicArmorWithStrengthBoostAndLevitation extends DiamithicArmorWithStrengthBoost
{
    protected DiamithicArmorWithStrengthBoostAndLevitation(ArmorMaterial material, Type equipmentSlot, String armorModelName)
    {
        super(material, equipmentSlot, armorModelName);
    }

    @Override
    public boolean isModifiedWithLevitation()
    {
        return true;
    }
}
