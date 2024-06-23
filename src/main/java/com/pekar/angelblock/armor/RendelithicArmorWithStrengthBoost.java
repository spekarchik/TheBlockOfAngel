package com.pekar.angelblock.armor;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorMaterial;

public class RendelithicArmorWithStrengthBoost extends RendelithicArmor
{
    protected RendelithicArmorWithStrengthBoost(Holder<ArmorMaterial> material, Type equipmentSlot, String armorItemName)
    {
        super(material, equipmentSlot, armorItemName);
    }

    @Override
    public boolean isModifiedWithStrengthBooster()
    {
        return true;
    }
}
