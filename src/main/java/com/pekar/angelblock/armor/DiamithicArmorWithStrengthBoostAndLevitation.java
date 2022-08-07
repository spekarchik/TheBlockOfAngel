package com.pekar.angelblock.armor;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;

public class DiamithicArmorWithStrengthBoostAndLevitation extends DiamithicArmorWithStrengthBoost
{
    protected DiamithicArmorWithStrengthBoostAndLevitation(ArmorMaterial material, EquipmentSlot equipmentSlot, String armorModelName)
    {
        super(material, equipmentSlot, armorModelName);
    }

    @Override
    public boolean isModifiedWithLevitation()
    {
        return true;
    }
}
