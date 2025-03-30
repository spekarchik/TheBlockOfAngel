package com.pekar.angelblock.tools;

import com.pekar.angelblock.tools.properties.IMaterialProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public interface IModToolEnhanceable extends IModTool
{

    IMaterialProperties getMaterialProperties();

    default boolean isToolEffective(LivingEntity entityLiving, BlockPos pos)
    {
        BlockState blockState = entityLiving.level().getBlockState(pos);
        return isCorrectToolForDrops(entityLiving.getMainHandItem(), blockState);
    }

    default boolean preventBlockBreak(Player player, ItemStack itemStack, BlockPos pos)
    {
        if (!canPreventBlockDestroying(player, pos)) return false;

        return !getMaterialProperties().isSafeToBreak(player, pos);
    }

    default boolean canPreventBlockDestroying(LivingEntity entity, BlockPos pos)
    {
        return isTool() && isEnhanced() && isToolEffective(entity, pos) && !entity.isShiftKeyDown();
    }
}
