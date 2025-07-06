package com.pekar.angelblock.tools;

import com.pekar.angelblock.utils.SoundType;
import com.pekar.angelblock.utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public interface IToolService
{
    default void setBlock(Player player, BlockPos pos, Block block)
    {
        setBlock(player, pos, block.defaultBlockState());
    }

    default void setBlock(Player player, BlockPos pos, BlockState blockState)
    {
        player.level().setBlock(pos, blockState, Block.UPDATE_ALL_IMMEDIATE);
        Utils.instance.sound.playSoundByBlock(player, pos, SoundType.BLOCK_CHANGED);
    }

    default InteractionResult getToolInteractionResult(boolean applied, boolean isClientSide)
    {
        if (!applied) return InteractionResult.PASS;
        return isClientSide ? InteractionResult.SUCCESS: InteractionResult.SUCCESS_SERVER;
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
