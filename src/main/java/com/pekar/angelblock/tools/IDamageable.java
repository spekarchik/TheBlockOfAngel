package com.pekar.angelblock.tools;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface IDamageable
{
    default boolean hasCriticalDamage(ItemStack stack)
    {
        return stack.getMaxDamage() - stack.getDamageValue() <= getCriticalDurability();
    }

    default boolean hasLowEfficiencyDamage(ItemStack stack)
    {
        return stack.getMaxDamage() - stack.getDamageValue() <= getLowEfficiencyDurability(stack.getMaxDamage());
    }

    default boolean hasExtraLowEfficiencyDamage(ItemStack stack)
    {
        return stack.getMaxDamage() - stack.getDamageValue() <= getExtraLowEfficiencyDurability(stack.getMaxDamage());
    }

    default int getCriticalDurability()
    {
        return 2;
    }

    default int getLowEfficiencyDurability(int maxDurability)
    {
        return maxDurability / 3;
    }

    default int getExtraLowEfficiencyDurability(int maxDurability)
    {
        return maxDurability / 5;
    }

    default void damageMainHandItem(int amount, LivingEntity livingEntity)
    {
        var itemStack = livingEntity.getItemInHand(InteractionHand.MAIN_HAND);
        var durability = itemStack.getMaxDamage() - itemStack.getDamageValue();
        var modifiedAmount = durability > amount ? amount : durability - 1;

        if (itemStack.getItem().equals(this))
            itemStack.hurtAndBreak(modifiedAmount, livingEntity, EquipmentSlot.MAINHAND);
    }

    default void damageOffHandItem(int amount, LivingEntity livingEntity)
    {
        var itemStack = livingEntity.getItemInHand(InteractionHand.OFF_HAND);
        itemStack.hurtAndBreak(amount, livingEntity, EquipmentSlot.OFFHAND);
    }

    default void damageMainHandItemIfSurvivalIgnoreClient(Player player, Level level)
    {
        if (level.isClientSide()) return;

        if (player instanceof ServerPlayer serverPlayer && !serverPlayer.isCreative())
        {
            damageMainHandItem(1, player);
        }
    }

    default void damageOffHandItemIfSurvivalIgnoreClient(Player player, Level level)
    {
        if (level.isClientSide()) return;

        if (player instanceof ServerPlayer serverPlayer && !serverPlayer.isCreative())
        {
            damageOffHandItem(1, player);
        }
    }
}
