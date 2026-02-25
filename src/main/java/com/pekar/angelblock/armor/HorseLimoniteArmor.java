package com.pekar.angelblock.armor;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class HorseLimoniteArmor extends ModHorseArmor
{
    protected HorseLimoniteArmor(ModArmorMaterial material, Properties properties)
    {
        super(material, properties);
    }

    @Override
    public boolean canWalkOnPowderedSnow(ItemStack stack, LivingEntity wearer)
    {
        return true;
    }
}
