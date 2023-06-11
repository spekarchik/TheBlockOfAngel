package com.pekar.angelblock.armor;

import net.minecraft.world.item.ArmorMaterial;

public class DiamithicArmorWithDetector extends ModArmor
{
    protected DiamithicArmorWithDetector(ArmorMaterial material, Type equipmentSlot, String armorModelName)
    {
        super(material, equipmentSlot, armorModelName);
    }

    @Override
    public boolean isModifiedWithDetector()
    {
        return true;
    }
}
