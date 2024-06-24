package com.pekar.angelblock.armor;

public class SuperArmorWithRegenerator extends SuperArmor
{
    protected SuperArmorWithRegenerator(ModArmorMaterial material, Type equipmentSlot)
    {
        super(material, equipmentSlot);
    }

    @Override
    public boolean isModifiedWithHealthRegenerator()
    {
        return true;
    }
}
