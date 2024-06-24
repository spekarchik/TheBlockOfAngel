package com.pekar.angelblock.armor;

public class LimoniteArmorWithSeaPower extends LimoniteArmor
{
    protected LimoniteArmorWithSeaPower(ModArmorMaterial material, Type equipmentSlot)
    {
        super(material, equipmentSlot);
    }

    @Override
    public boolean isModifiedWithSeaPower()
    {
        return true;
    }
}
