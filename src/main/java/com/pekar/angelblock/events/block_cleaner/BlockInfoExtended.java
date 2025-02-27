package com.pekar.angelblock.events.block_cleaner;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;

class BlockInfoExtended extends BlockInfo
{
    private final boolean setToAir;

    private final boolean removeWhenClosely;

    BlockInfoExtended(Player player, BlockPos blockPos, int ticks, boolean setToAir, boolean removeWhenClosely)
    {
        super(player, blockPos, ticks);
        this.setToAir = setToAir;
        this.removeWhenClosely = removeWhenClosely;
    }

    public boolean setToAir()
    {
        return setToAir;
    }

    public boolean removeWhenClosely()
    {
        return removeWhenClosely;
    }

}
