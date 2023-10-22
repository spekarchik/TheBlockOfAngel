package com.pekar.angelblock.tools;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface IModTool
{
    boolean isEnhancedTool();

    boolean isEnhancedWeapon();

    boolean isEnhancedRod();

    default void damageItem(int amount, LivingEntity livingEntity)
    {
        var itemStack = livingEntity.getItemInHand(InteractionHand.MAIN_HAND);
        if (itemStack.getItem().equals(this))
            itemStack.hurtAndBreak(amount, livingEntity, player -> player.broadcastBreakEvent(InteractionHand.MAIN_HAND));
    }

    default void damageItemIfSurvival(Player player, Level level, BlockPos pos, BlockState blockState)
    {
        if (level.isClientSide()) return;

        if (player instanceof ServerPlayer serverPlayer && !serverPlayer.isCreative())
        {
            damageItem(1, player);
        }
    }
}
