package com.pekar.angelblock.tools;

import com.pekar.angelblock.network.packets.PlaySoundPacket;
import com.pekar.angelblock.network.packets.SoundType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public interface IModTool extends IModDescriptionItem
{
    boolean isEnhancedTool();

    boolean isEnhancedWeapon();

    boolean isEnhancedRod();

    TieredItem getTool();

    default void damageMainHandItem(int amount, LivingEntity livingEntity)
    {
        var itemStack = livingEntity.getItemInHand(InteractionHand.MAIN_HAND);
        if (itemStack.getItem().equals(this))
            itemStack.hurtAndBreak(amount, livingEntity, EquipmentSlot.MAINHAND);
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

    default boolean isToolEffective(LivingEntity entityLiving, BlockPos pos)
    {
        BlockState blockState = entityLiving.level().getBlockState(pos);
        return getTool().isCorrectToolForDrops(entityLiving.getMainHandItem(), blockState);
    }

    default void setBlock(Player player, BlockPos pos, Block block)
    {
        player.level().setBlock(pos, block.defaultBlockState(), 11);
        new PlaySoundPacket(SoundType.BLOCK_CHANGED).sendToPlayer((ServerPlayer) player);
    }

    @Override
    default MutableComponent getDisplayName(int lineNumber)
    {
        return Component.translatable(getTool().getDescriptionId() + ".desc" + lineNumber);
    }

    default InteractionResult getToolInteractionResult(boolean applied, boolean isClientSide)
    {
        if (!applied) return InteractionResult.PASS;
        return isClientSide ? InteractionResult.SUCCESS_NO_ITEM_USED: InteractionResult.CONSUME_PARTIAL;
    }
}
