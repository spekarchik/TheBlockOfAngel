package com.pekar.angelblock.armor;

public class DiamithicArmorWithStrengthBoostAndLevitation extends DiamithicArmorWithStrengthBoost
{
    protected DiamithicArmorWithStrengthBoostAndLevitation(ModArmorMaterial material, Type equipmentSlot)
    {
        super(material, equipmentSlot);
    }

    @Override
    public boolean isModifiedWithLevitation()
    {
        return true;
    }
}
