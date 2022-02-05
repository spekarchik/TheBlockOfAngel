package com.pekar.angelblock.tools;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;

public interface IModTool
{
    boolean isEnhancedTool();

    boolean isEnhancedWeapon();

    boolean isEnhancedRod();

    default void damageItem(int amount, LivingEntity livingEntity)
    {
        var itemStack = livingEntity.getItemInHand(InteractionHand.MAIN_HAND);
        itemStack.hurtAndBreak(amount, livingEntity, player -> player.broadcastBreakEvent(InteractionHand.MAIN_HAND));
    }
}
