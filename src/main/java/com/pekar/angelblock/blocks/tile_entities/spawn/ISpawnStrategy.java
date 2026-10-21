package com.pekar.angelblock.blocks.tile_entities.spawn;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public interface ISpawnStrategy
{
    boolean canSpawnAtPos(Level level, BlockPos pos, BlockPos initialPos);
}
