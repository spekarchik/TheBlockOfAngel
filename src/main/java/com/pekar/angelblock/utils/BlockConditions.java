package com.pekar.angelblock.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.FluidState;

public class BlockConditions
{
    BlockConditions()
    {

    }

    public boolean isNearLava(Level level, BlockPos pos)
    {
        final int posX = pos.getX(), posY = pos.getY(), posZ = pos.getZ();

        for (int x = posX - 1; x <= posX + 1; x++)
        {
            for (int y = posY; y <= posY + 1; y++)
            {
                if (x != posX && y != posY) continue;

                for (int z = posZ - 1; z <= posZ + 1; z++)
                {
                    if (z != posZ && (x != posX || y != posY)) continue;

                    Block block = level.getBlockState(new BlockPos(x, y, z)).getBlock();
                    if (block == Blocks.LAVA)
                        return true;
                }
            }
        }

        return false;
    }

    public boolean isNearWater(Level level, BlockPos pos)
    {
        final int posX = pos.getX(), posY = pos.getY(), posZ = pos.getZ();

        for (int x = posX - 1; x <= posX + 1; x++)
        {
            for (int y = posY; y <= posY + 1; y++)
            {
                if (x != posX && y != posY) continue;

                for (int z = posZ - 1; z <= posZ + 1; z++)
                {
                    if (z != posZ && (x != posX || y != posY)) continue;

                    Block block = level.getBlockState(new BlockPos(x, y, z)).getBlock();
                    if (block == Blocks.WATER)
                        return true;
                }
            }
        }

        return false;
    }

    public boolean isNearWaterHorizontal(Level level, BlockPos pos)
    {
        return level.isWaterAt(pos.east()) || level.isWaterAt(pos.west()) || level.isWaterAt(pos.north())
                || level.isWaterAt(pos.south());
    }

    public boolean isAboveWaterBlock(Level level, BlockPos pos)
    {
        var belowPos = pos.below();
        return level.isWaterAt(belowPos) && level.getFluidState(belowPos).getAmount() == FluidState.AMOUNT_FULL;
    }

    public boolean isUnderAirBlock(Level level, BlockPos pos)
    {
        return level.isEmptyBlock(pos.above());
    }
}
