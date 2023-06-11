package com.pekar.angelblock.armor;

import net.minecraft.world.item.ArmorMaterial;

public class SuperArmorWithDetector extends SuperArmor
{
    protected SuperArmorWithDetector(ArmorMaterial material, Type equipmentSlot, String armorItemName)
    {
        super(material, equipmentSlot, armorItemName);
    }

    @Override
    public boolean isModifiedWithDetector()
    {
        return true;
    }
}
