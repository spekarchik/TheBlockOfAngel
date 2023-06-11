package com.pekar.angelblock.armor;

import net.minecraft.world.item.ArmorMaterial;

public class RendelithicArmorWithLevitation extends RendelithicArmor
{
    protected RendelithicArmorWithLevitation(ArmorMaterial material, Type equipmentSlot, String armorItemName)
    {
        super(material, equipmentSlot, armorItemName);
    }

    @Override
    public boolean isModifiedWithLevitation()
    {
        return true;
    }
}
