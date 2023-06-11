package com.pekar.angelblock.armor;

import net.minecraft.world.item.ArmorMaterial;

public class LimoniteArmorWithRegenerator extends ModArmor
{
    protected LimoniteArmorWithRegenerator(ArmorMaterial material, Type equipmentSlot, String armorModelName)
    {
        super(material, equipmentSlot, armorModelName);
    }

    @Override
    public boolean isModifiedWithHealthRegenerator()
    {
        return true;
    }
}
