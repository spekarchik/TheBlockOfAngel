package com.pekar.angelblock.armor;

import net.minecraft.world.item.ArmorMaterial;

public class LimoniteArmorWithStrengthBoost extends ModArmor
{
    protected LimoniteArmorWithStrengthBoost(ArmorMaterial material, Type equipmentSlot, String armorModelName)
    {
        super(material, equipmentSlot, armorModelName);
    }

    @Override
    public boolean isModifiedWithStrengthBooster()
    {
        return true;
    }
}
