package com.pekar.angelblock.armor;

public class LapisArmorWithStrengthBoost extends ModArmor
{
    protected LapisArmorWithStrengthBoost(ModArmorMaterial material, Type equipmentSlot)
    {
        super(material, equipmentSlot);
    }

    @Override
    public boolean isModifiedWithStrengthBooster()
    {
        return true;
    }
}
