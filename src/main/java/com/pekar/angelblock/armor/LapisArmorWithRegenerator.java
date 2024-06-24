package com.pekar.angelblock.armor;

public class LapisArmorWithRegenerator extends ModArmor
{
    protected LapisArmorWithRegenerator(ModArmorMaterial material, Type equipmentSlot)
    {
        super(material, equipmentSlot);
    }

    @Override
    public boolean isModifiedWithHealthRegenerator()
    {
        return true;
    }
}
