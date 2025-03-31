package com.pekar.angelblock.tools;

import com.pekar.angelblock.network.packets.PlaySoundPacket;
import com.pekar.angelblock.network.packets.SoundType;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public interface IToolService
{
    default void setBlock(Player player, BlockPos pos, Block block)
    {
        player.level().setBlock(pos, block.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
        new PlaySoundPacket(SoundType.BLOCK_CHANGED).sendToPlayer((ServerPlayer) player);
    }

    default void setBlock(Player player, BlockPos pos, BlockState blockState)
    {
        player.level().setBlock(pos, blockState, Block.UPDATE_ALL_IMMEDIATE);
        new PlaySoundPacket(SoundType.BLOCK_CHANGED).sendToPlayer((ServerPlayer) player);
    }

    default InteractionResult getToolInteractionResult(boolean applied, boolean isClientSide)
    {
        if (!applied) return InteractionResult.PASS;
        return isClientSide ? InteractionResult.SUCCESS: InteractionResult.SUCCESS_SERVER;
    }

    default void causePlayerExhaustion(Player player)
    {
        if (player != null)
        {
            var foodData = player.getFoodData();
            foodData.setSaturation(foodData.getSaturationLevel() * 0.5F);
            player.causeFoodExhaustion(0.5F);
        }
    }
}
