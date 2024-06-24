package com.pekar.angelblock.armor;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorMaterial;

public class LimoniteArmorWithDetector extends LimoniteArmor
{
    protected LimoniteArmorWithDetector(Holder<ArmorMaterial> material, Type equipmentSlot, String armorModelName, int durabilityMultiplier)
    {
        super(material, equipmentSlot, armorModelName, durabilityMultiplier);
    }

    @Override
    public boolean isModifiedWithDetector()
    {
        return true;
    }
}
