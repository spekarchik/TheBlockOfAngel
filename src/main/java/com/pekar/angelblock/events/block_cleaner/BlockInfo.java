package com.pekar.angelblock.events.block_cleaner;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;

class BlockInfo
{
    private final BlockPos pos;
    private final Player player;
    private int ticksBeforeRemoving;
    private final boolean setToAir;

    BlockInfo(Player player, BlockPos blockPos, int ticks, boolean setToAir)
    {
        pos = blockPos;
        this.player = player;
        ticksBeforeRemoving = ticks;
        this.setToAir = setToAir;
    }

    public void decrease()
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

    public boolean setToAir()
    {
        return setToAir;
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
            if (!(obj instanceof BlockInfo))
                return false;
        }

        BlockInfo other = (BlockInfo) obj;
        return pos.equals(other.pos);
    }

    @Override
    public int hashCode()
    {
        return pos.hashCode();
    }
}
