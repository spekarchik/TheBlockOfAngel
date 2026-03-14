package com.pekar.angelblock.armor;

import net.minecraft.world.item.ItemStack;

public interface IModArmor
{
    ModArmorMaterial getArmorMaterial();

    boolean isBroken(ItemStack stack);

    int getMaxDamage();
}
