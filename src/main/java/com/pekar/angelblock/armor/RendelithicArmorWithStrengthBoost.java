package com.pekar.angelblock.armor;

public class RendelithicArmorWithStrengthBoost extends RendelithicArmor
{
    protected RendelithicArmorWithStrengthBoost(ModArmorMaterial material, Type equipmentSlot)
    {
        super(material, equipmentSlot);
    }

    @Override
    public boolean isModifiedWithStrengthBooster()
    {
        return true;
    }
}
