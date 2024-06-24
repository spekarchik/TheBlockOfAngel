package com.pekar.angelblock.armor;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorMaterial;

public class SuperArmorWithDetector extends SuperArmor
{
    protected SuperArmorWithDetector(Holder<ArmorMaterial> material, Type equipmentSlot, String armorItemName, int durabilityMultiplier)
    {
        super(material, equipmentSlot, armorItemName, durabilityMultiplier);
    }

    @Override
    public boolean isModifiedWithDetector()
    {
        return true;
    }
}
