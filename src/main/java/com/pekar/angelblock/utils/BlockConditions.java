package com.pekar.angelblock.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
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

    public boolean isNearFrostedIceHorizontal(Level level, BlockPos pos)
    {
        return level.getBlockState(pos.east()).is(Blocks.FROSTED_ICE) || level.getBlockState(pos.west()).is(Blocks.FROSTED_ICE)
                || level.getBlockState(pos.north()).is(Blocks.FROSTED_ICE) || level.getBlockState(pos.south()).is(Blocks.FROSTED_ICE);
    }

    public final boolean canSustainSugarCane(Level level, BlockPos pos)
    {
        if (!isNearWaterHorizontal(level, pos) && !isNearFrostedIceHorizontal(level, pos)) return false;

        var soilBlockState = level.getBlockState(pos);

        return soilBlockState.is(BlockTags.DIRT) || soilBlockState.is(BlockTags.SAND);
    }

    public final boolean canSustainBamboo(Level level, BlockPos pos)
    {
        var soilBlockState = level.getBlockState(pos);
        return soilBlockState.is(BlockTags.BAMBOO_PLANTABLE_ON) && !soilBlockState.is(Blocks.BAMBOO) && !soilBlockState.is(Blocks.BAMBOO_SAPLING);
    }

    public final boolean canSustainPlant(Level level, BlockPos soilPos, BlockState plantBlockState)
    {
        var soilBlockState = level.getBlockState(soilPos);

        if (plantBlockState.is(BlockTags.CROPS))
        {
            return soilBlockState.is(Blocks.FARMLAND);
        }
        else if (plantBlockState.is(Blocks.NETHER_WART))
        {
            return soilBlockState.is(Blocks.SOUL_SAND);
        }
        else if (plantBlockState.is(Blocks.SWEET_BERRY_BUSH))
        {
            return soilBlockState.is(BlockTags.DIRT) || soilBlockState.is(Blocks.FARMLAND);
        }
        else if (plantBlockState.is(Blocks.SUGAR_CANE))
        {
            return canSustainSugarCane(level, soilPos);
        }
        else if (plantBlockState.is(Blocks.BAMBOO))
        {
            return canSustainBamboo(level, soilPos);
        }
        else if (plantBlockState.is(Blocks.CHORUS_FLOWER))
        {
            return soilBlockState.is(Blocks.END_STONE);
        }
        else
        {
            return true;
        }
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
