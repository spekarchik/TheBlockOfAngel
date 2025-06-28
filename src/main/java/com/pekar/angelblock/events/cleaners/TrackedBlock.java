package com.pekar.angelblock.events.cleaners;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;

public class TrackedBlock extends TrackedTarget<Block>
{
    private final boolean removeWhenClosely;
    private final BlockPos pos;

    public TrackedBlock(Block block, BlockPos pos, Player owner, int ticksBeforeRemoving, boolean removeWhenClosely)
    {
        super(block, owner, ticksBeforeRemoving);
        this.pos = pos;
        this.removeWhenClosely = removeWhenClosely;
    }

    @Override
    protected ITargetBehavior createBehavior()
    {
        return new BlockTargetBehavior(this);
    }

    @Override
    public ServerLevel getTargetLevel()
    {
        return (ServerLevel) getOwner().level();
    }

    @Override
    public void remove()
    {
        var level = getTargetLevel();
        if (!level.isEmptyBlock(pos))
        {
            level.destroyBlock(pos, false /*drop items*/);
        }
    }

    @Override
    public String getId()
    {
        return getTargetInstance().getDescriptionId() + pos.asLong();
    }

    public BlockPos getPos()
    {
        return pos;
    }

    public boolean needToRemoveWhenClosely()
    {
        return removeWhenClosely;
    }
}
