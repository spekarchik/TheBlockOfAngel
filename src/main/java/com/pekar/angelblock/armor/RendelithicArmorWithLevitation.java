package com.pekar.angelblock.armor;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorMaterial;

public class RendelithicArmorWithLevitation extends RendelithicArmor
{
    protected RendelithicArmorWithLevitation(Holder<ArmorMaterial> material, Type equipmentSlot, String armorItemName)
    {
        super(material, equipmentSlot, armorItemName);
    }

    @Override
    public boolean isModifiedWithLevitation()
    {
        return true;
    }
}
