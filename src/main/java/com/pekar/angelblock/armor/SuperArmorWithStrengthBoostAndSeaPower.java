package com.pekar.angelblock.armor;

public class SuperArmorWithStrengthBoostAndSeaPower extends SuperArmorWithStrengthBoost
{
    protected SuperArmorWithStrengthBoostAndSeaPower(ModArmorMaterial material, Type equipmentSlot)
    {
        super(material, equipmentSlot);
    }

    @Override
    public boolean isModifiedWithSeaPower()
    {
        return true;
    }
}
