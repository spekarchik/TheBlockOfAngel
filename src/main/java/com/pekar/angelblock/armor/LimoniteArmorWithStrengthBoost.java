package com.pekar.angelblock.armor;

public class LimoniteArmorWithStrengthBoost extends LimoniteArmor
{
    protected LimoniteArmorWithStrengthBoost(ModArmorMaterial material, Type equipmentSlot)
    {
        super(material, equipmentSlot);
    }

    @Override
    public boolean isModifiedWithStrengthBooster()
    {
        return true;
    }
}
