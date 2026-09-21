package com.pekar.angelblock.blocks.tile_entities.spawn;

import com.pekar.angelblock.utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class OnGroundMonsterSpawnStrategy implements ISpawnStrategy
{
    @Override
    public boolean canSpawnAtPos(Level level, BlockPos pos, Player player)
    {
        return Utils.instance.blocks.types.isSafeGroundBlock(level, pos)
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
