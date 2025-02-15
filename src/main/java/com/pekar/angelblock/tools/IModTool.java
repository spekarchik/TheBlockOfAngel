package com.pekar.angelblock.tools;

import com.pekar.angelblock.network.packets.PlaySoundPacket;
import com.pekar.angelblock.network.packets.SoundType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
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

    default void damageItem(int amount, LivingEntity livingEntity)
    {
        var itemStack = livingEntity.getItemInHand(InteractionHand.MAIN_HAND);
        if (itemStack.getItem().equals(this))
            itemStack.hurtAndBreak(amount, livingEntity, EquipmentSlot.MAINHAND);
    }

    default void damageItemIfSurvival(Player player, Level level, BlockPos pos, BlockState blockState)
    {
        if (level.isClientSide()) return;

        if (player instanceof ServerPlayer serverPlayer && !serverPlayer.isCreative())
        {
            damageItem(1, player);
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
}
