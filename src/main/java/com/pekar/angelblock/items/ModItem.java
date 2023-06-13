package com.pekar.angelblock.items;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;

public class ModItem extends Item
{
    public ModItem()
    {
        super(new Properties());
    }

    protected boolean updateNeighbors(Level level, BlockPos pos)
    {
        BlockState waterBlockState = level.getBlockState(pos);
        if (waterBlockState.getBlock() instanceof LiquidBlock liquidBlock)
        {
            liquidBlock.neighborChanged(waterBlockState, level, pos, liquidBlock, pos, false /* ignored */);
            return true;
        }

        return false;
    }
}
