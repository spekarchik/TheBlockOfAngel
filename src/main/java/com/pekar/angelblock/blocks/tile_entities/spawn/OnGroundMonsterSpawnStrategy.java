package com.pekar.angelblock.blocks.tile_entities.spawn;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class OnGroundMonsterSpawnStrategy implements ISpawnStrategy
{
    @Override
    public boolean canSpawnAtPos(Level level, BlockPos pos, Player player)
    {
        return level.getBlockState(pos).isSolidRender()
                && Math.abs(pos.getY() - player.getOnPos().getY()) <= 5
                && hasSpaceAbove(level, pos);
    }

    protected boolean hasSpaceAbove(Level level, BlockPos pos)
    {
        return level.getBlockState(pos.above()).isAir()
                && level.getBlockState(pos.above(2)).isAir()
                && level.getBlockState(pos.above(3)).isAir();
    }
}
