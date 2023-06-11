package com.pekar.angelblock.armor;

import net.minecraft.world.item.ArmorMaterial;

public class DiamithicArmorWithLevitation extends ModArmor
{
    protected DiamithicArmorWithLevitation(ArmorMaterial material, Type equipmentSlot, String armorModelName)
    {
        super(material, equipmentSlot, armorModelName);
    }

    @Override
    public boolean isModifiedWithLevitation()
    {
        return true;
    }
}
