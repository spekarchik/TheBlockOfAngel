package com.pekar.angelblock.armor;

import net.minecraft.world.item.ArmorMaterial;

public class LimoniteArmorWithDetector extends LimoniteArmor
{
    protected LimoniteArmorWithDetector(ArmorMaterial material, Type equipmentSlot, String armorModelName)
    {
        super(material, equipmentSlot, armorModelName);
    }

    @Override
    public boolean isModifiedWithDetector()
    {
        return true;
    }
}
