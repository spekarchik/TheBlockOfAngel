package com.pekar.angelblock.armor;

import net.minecraft.world.item.ItemStack;

public interface IModArmor
{
    ModArmorMaterial getArmorMaterial();

    String getArmorFamilyName();

    boolean isBroken(ItemStack stack);

    int getMaxDamage();
}
