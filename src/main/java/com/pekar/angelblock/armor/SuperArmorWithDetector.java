package com.pekar.angelblock.armor;

public class SuperArmorWithDetector extends SuperArmor
{
    protected SuperArmorWithDetector(ModArmorMaterial material, Type equipmentSlot)
    {
        super(material, equipmentSlot);
    }

    @Override
    public boolean isModifiedWithDetector()
    {
        return true;
    }
}
