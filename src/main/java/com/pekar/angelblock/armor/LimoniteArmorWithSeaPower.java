package com.pekar.angelblock.armor;

import net.minecraft.world.item.ArmorMaterial;

public class LimoniteArmorWithSeaPower extends LimoniteArmor
{
    protected LimoniteArmorWithSeaPower(ArmorMaterial material, Type equipmentSlot, String armorModelName)
    {
        super(material, equipmentSlot, armorModelName);
    }

    @Override
    public boolean isModifiedWithSeaPower()
    {
        return true;
    }
}
