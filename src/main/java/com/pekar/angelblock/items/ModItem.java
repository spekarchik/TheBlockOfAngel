package com.pekar.angelblock.items;

import com.pekar.angelblock.utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;

public class ModItem extends Item
{
    protected final Utils utils = new Utils();

    public ModItem()
    {
        super(new Properties());
    }

    protected boolean updateNeighbors(Level level, BlockPos pos)
    {
        BlockState waterBlockState = level.getBlockState(pos);
        if (waterBlockState.getBlock() instanceof LiquidBlock)
        {
            utils.notifyNeighbourChanged(waterBlockState, level, pos);
            return true;
        }

        return false;
    }
}
