package com.pekar.angelblock.armor;

public class SuperArmorWithStrengthBoost extends SuperArmor
{
    protected SuperArmorWithStrengthBoost(ModArmorMaterial material, Type equipmentSlot)
    {
        super(material, equipmentSlot);
    }

    @Override
    public boolean isModifiedWithStrengthBooster()
    {
        return true;
    }
}
