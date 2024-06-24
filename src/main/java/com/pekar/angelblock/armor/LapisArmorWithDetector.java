package com.pekar.angelblock.armor;

public class LapisArmorWithDetector extends ModArmor
{
    protected LapisArmorWithDetector(ModArmorMaterial material, Type equipmentSlot)
    {
        super(material, equipmentSlot);
    }

    @Override
    public boolean isModifiedWithDetector()
    {
        return true;
    }
}
