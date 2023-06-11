package com.pekar.angelblock.armor;

import net.minecraft.world.item.ArmorMaterial;

public class RendelithicArmorWithHealthRegenerator extends RendelithicArmor
{
    protected RendelithicArmorWithHealthRegenerator(ArmorMaterial material, Type equipmentSlot, String armorItemName)
    {
        super(material, equipmentSlot, armorItemName);
    }

    @Override
    public boolean isModifiedWithHealthRegenerator()
    {
        return true;
    }
}
