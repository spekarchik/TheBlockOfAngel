package com.pekar.angelblock.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class CrackedBlock extends ModDropExperienceBlock
{
    public CrackedBlock(BlockBehaviour.Properties properties)
    {
        super(properties);
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool)
    {
        if (player instanceof ServerPlayer serverPlayer && level instanceof ServerLevel serverLevel)
        {
            utils.player.awardAdvancement(serverPlayer, serverLevel, "cracked_block_powder", "cracked_block_powder");
        }

        super.playerDestroy(level, player, pos, state, blockEntity, tool);
    }

    @Override
    protected IntProvider getXpRange()
    {
        return UniformInt.of(1, 2);
    }
}
