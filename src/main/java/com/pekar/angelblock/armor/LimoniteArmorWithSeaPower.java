package com.pekar.angelblock.armor;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorMaterial;

public class LimoniteArmorWithSeaPower extends LimoniteArmor
{
    protected LimoniteArmorWithSeaPower(Holder<ArmorMaterial> material, Type equipmentSlot, String armorModelName)
    {
        super(material, equipmentSlot, armorModelName);
    }

    @Override
    public boolean isModifiedWithSeaPower()
    {
        return true;
    }
}
