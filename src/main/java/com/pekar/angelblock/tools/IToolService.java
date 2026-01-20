package com.pekar.angelblock.tools;

import com.pekar.angelblock.utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public interface IToolService
{
    default void setBlockWithClientSound(Player player, BlockPos pos, Block block)
    {
        setBlockWithClientSound(player, pos, block.defaultBlockState());
    }

    default void setBlockWithClientSound(Player player, BlockPos pos, BlockState blockState)
    {
        Utils.instance.blocks.transformations.setBlockWithClientSound(player, pos, blockState);
    }

    default InteractionResult getToolInteractionResult(boolean applied, boolean isClientSide)
    {
        if (!applied) return InteractionResult.PASS;
        return isClientSide ? InteractionResult.SUCCESS_NO_ITEM_USED: InteractionResult.CONSUME_PARTIAL;
    }

    default void causePlayerExhaustion(Player player)
    {
        if (player != null && !player.level().isClientSide())
        {
            var foodData = player.getFoodData();
            foodData.setSaturation(foodData.getSaturationLevel() * 0.5F);
            player.causeFoodExhaustion(0.5F);
        }
    }
}
