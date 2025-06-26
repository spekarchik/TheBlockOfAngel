package com.pekar.angelblock.events.cleaners;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;

public class BlockInfo
{
    protected final BlockPos pos;
    protected final Player player;
    protected int ticksBeforeRemoving;

    BlockInfo(Player player, BlockPos blockPos, int ticks)
    {
        pos = blockPos;
        this.player = player;
        ticksBeforeRemoving = ticks;
    }

    public void decrement()
    {
        --ticksBeforeRemoving;
    }

    public boolean canBeRemoved()
    {
        return ticksBeforeRemoving <= 0;
    }

    public BlockPos getPos()
    {
        return pos;
    }

    public Player getPlayer()
    {
        return player;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        else
        {
            if (!(obj instanceof BlockInfoExtended))
            {
                return false;
            }
        }

        BlockInfoExtended other = (BlockInfoExtended) obj;
        return pos.equals(other.pos);
    }

    @Override
    public int hashCode()
    {
        return pos.hashCode();
    }
}
