package com.pekar.angelblock.events.cleaners;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;

public class TrackedAnvil extends TrackedBlock
{
    private final ServerLevel targetLevel;

    public TrackedAnvil(Block block, BlockPos pos, ServerLevel targetLevel, int ticksBeforeRemoving)
    {
        super(block, pos, null, ticksBeforeRemoving, false);
        this.targetLevel = targetLevel;
    }

    @Override
    public ServerLevel getTargetLevel()
    {
        return targetLevel;
    }

    @Override
    protected ITargetBehavior createBehavior()
    {
        return new AnvilTargetBehavior(this);
    }

    @Override
    public void remove()
    {
        var level = getTargetLevel();
        var pos = getPos();
        if (level.getBlockState(pos).is(getTargetInstance()))
            level.destroyBlock(pos, false);
    }
}
