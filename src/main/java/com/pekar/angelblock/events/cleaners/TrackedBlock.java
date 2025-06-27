package com.pekar.angelblock.events.cleaners;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;

public class TrackedBlock extends TrackedTarget<Block>
{
    public TrackedBlock(Block block, BlockPos pos, Player owner, int ticksBeforeRemoving, boolean removeWhenClosely)
    {
        super(block, pos, owner, ticksBeforeRemoving, removeWhenClosely, TrackedBlock::removeTarget, 10.0, 60.0);
    }

    private static boolean removeTarget(ITrackedTarget target)
    {
        var level = target.getLevel();
        var pos = target.getPos();
        if (!level.isEmptyBlock(pos))
        {
            level.destroyBlock(pos, false /*drop items*/);
        }

        return true;
    }
}
