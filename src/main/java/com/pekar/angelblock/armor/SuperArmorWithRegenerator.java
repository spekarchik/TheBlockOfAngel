package com.pekar.angelblock.armor;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorMaterial;

public class SuperArmorWithRegenerator extends SuperArmor
{
    protected SuperArmorWithRegenerator(Holder<ArmorMaterial> material, Type equipmentSlot, String armorItemName, int durabilityMultiplier)
    {
        super(material, equipmentSlot, armorItemName, durabilityMultiplier);
    }

    @Override
    public boolean isModifiedWithHealthRegenerator()
    {
        return true;
    }
}
