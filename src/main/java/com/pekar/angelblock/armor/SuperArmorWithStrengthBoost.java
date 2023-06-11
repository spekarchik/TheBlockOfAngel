package com.pekar.angelblock.armor;

import net.minecraft.world.item.ArmorMaterial;

public class SuperArmorWithStrengthBoost extends SuperArmor
{
    protected SuperArmorWithStrengthBoost(ArmorMaterial material, Type equipmentSlot, String armorItemName)
    {
        super(material, equipmentSlot, armorItemName);
    }

    @Override
    public boolean isModifiedWithStrengthBooster()
    {
        return true;
    }
}
