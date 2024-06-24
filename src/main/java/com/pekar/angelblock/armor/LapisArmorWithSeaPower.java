package com.pekar.angelblock.armor;

public class LapisArmorWithSeaPower extends ModArmor
{
    protected LapisArmorWithSeaPower(ModArmorMaterial material, Type equipmentSlot)
    {
        super(material, equipmentSlot);
    }

    @Override
    public boolean isModifiedWithSeaPower()
    {
        return true;
    }
}
