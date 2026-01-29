package com.pekar.angelblock.blocks.tile_entities.spawn;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class TallMonsterSpawnStrategy extends OnGroundMonsterSpawnStrategy
{
    @Override
    protected boolean hasSpaceAbove(Level level, BlockPos pos)
    {
        return super.hasSpaceAbove(level, pos)
                && level.getBlockState(pos.above(4)).isAir();
    }
}
