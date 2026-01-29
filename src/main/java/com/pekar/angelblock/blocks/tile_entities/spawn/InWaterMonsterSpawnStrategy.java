package com.pekar.angelblock.blocks.tile_entities.spawn;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class InWaterMonsterSpawnStrategy implements ISpawnStrategy
{
    @Override
    public boolean canSpawnAtPos(Level level, BlockPos pos, Player player)
    {
        return level.getBlockState(pos).is(Blocks.WATER) && hasSpaceAbove(level, pos);
    }

    protected boolean hasSpaceAbove(Level level, BlockPos pos)
    {
        return level.getBlockState(pos.above()).is(Blocks.WATER)
                && level.getBlockState(pos.above(2)).is(Blocks.WATER);
    }
}
