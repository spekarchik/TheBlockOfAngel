package com.pekar.angelblock.blocks.tile_entities.spawn;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class FlyingMonsterSpawnStrategy implements ISpawnStrategy
{
    @Override
    public boolean canSpawnAtPos(Level level, BlockPos pos, Player player)
    {
        return level.getBlockState(pos).isAir()
                && Math.abs(pos.getY() - player.getOnPos().getY()) <= 10
                && hasSpaceBelow(level, pos)
                && hasSpaceAbove(level, pos);
    }

    protected boolean hasSpaceBelow(Level level, BlockPos pos)
    {
        return level.getBlockState(pos.below()).isAir()
                && level.getBlockState(pos.below(2)).isAir();
    }

    protected boolean hasSpaceAbove(Level level, BlockPos pos)
    {
        return level.getBlockState(pos.above()).isAir()
                && level.getBlockState(pos.above(2)).isAir();
    }
}
