package com.pekar.angelblock.armor;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorMaterial;

public class SuperArmorWithSeaPower extends SuperArmor
{
    protected SuperArmorWithSeaPower(Holder<ArmorMaterial> material, Type equipmentSlot, String armorItemName)
    {
        super(material, equipmentSlot, armorItemName);
    }

    @Override
    public boolean isModifiedWithSeaPower()
    {
        return true;
    }
}
