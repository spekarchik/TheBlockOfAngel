package com.pekar.angelblock.armor;

import net.minecraft.world.item.ArmorMaterial;

public class SuperArmorWithSeaPower extends SuperArmor
{
    protected SuperArmorWithSeaPower(ArmorMaterial material, Type equipmentSlot, String armorItemName)
    {
        super(material, equipmentSlot, armorItemName);
    }

    @Override
    public boolean isModifiedWithSeaPower()
    {
        return true;
    }
}
