package com.pekar.angelblock.blocks.tile_entities.spawn;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class BigFlyingMonsterSpawnStrategy extends FlyingMonsterSpawnStrategy
{
    @Override
    public boolean canSpawnAtPos(Level level, BlockPos pos, Player player)
    {
        return level.getBlockState(pos).isAir()
                && hasSpaceBelow(level, pos)
                && hasSpaceAbove(level, pos);
    }

    @Override
    protected boolean hasSpaceAbove(Level level, BlockPos pos)
    {
        return super.hasSpaceAbove(level, pos)
                && level.getBlockState(pos.above(3)).isAir();
    }

    @Override
    protected boolean hasSpaceBelow(Level level, BlockPos pos)
    {
        return super.hasSpaceBelow(level, pos)
                && level.getBlockState(pos.below(3)).isAir();
    }
}
