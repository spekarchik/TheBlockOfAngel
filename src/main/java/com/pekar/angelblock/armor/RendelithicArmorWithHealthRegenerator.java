package com.pekar.angelblock.armor;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorMaterial;

public class RendelithicArmorWithHealthRegenerator extends RendelithicArmor
{
    protected RendelithicArmorWithHealthRegenerator(Holder<ArmorMaterial> material, Type equipmentSlot, String armorItemName, int durabilityMultiplier)
    {
        super(material, equipmentSlot, armorItemName, durabilityMultiplier);
    }

    @Override
    public boolean isModifiedWithHealthRegenerator()
    {
        return true;
    }
}
