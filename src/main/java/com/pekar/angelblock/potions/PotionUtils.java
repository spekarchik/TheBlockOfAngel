package com.pekar.angelblock.potions;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;

import javax.annotation.Nullable;

public class PotionUtils
{
    public static int DURATION_UNLIMITED = Integer.MAX_VALUE;

    public static Potion getPotion(ItemStack itemStack) {
        return getPotion(itemStack.getTag());
    }

    public static Potion getPotion(@Nullable CompoundTag p_43578_) {
        return p_43578_ == null ? Potions.EMPTY : Potion.byName(p_43578_.getString("Potion"));
    }
}
