package com.pekar.angelblock.armor;

public class SuperArmorWithSeaPower extends SuperArmor
{
    protected SuperArmorWithSeaPower(ModArmorMaterial material, Type equipmentSlot)
    {
        super(material, equipmentSlot);
    }

    @Override
    public boolean isModifiedWithSeaPower()
    {
        return true;
    }
}
