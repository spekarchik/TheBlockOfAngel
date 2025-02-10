package com.pekar.angelblock.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.LiquidBlock;

public class PlayerConditions
{
    PlayerConditions()
    {

    }

    public boolean isFallSafeWide(LivingEntity entityPlayer, BlockPos pos)
    {
        var playerPos = entityPlayer.blockPosition();

        if (playerPos.getY() > pos.getY())
        {
            return Math.abs(playerPos.getX() - pos.getX()) >= 2
                    || Math.abs(playerPos.getZ() - pos.getZ()) >= 2
                    || !isAboveLavaOrWaterOrAir(entityPlayer.level(), pos);
        }
        else
        {
            return !isStandingUnderFallingBlock(entityPlayer, pos);
        }
    }

    public boolean isFallSafeExact(LivingEntity entityPlayer, BlockPos pos)
    {
        if (!isAboveBreakingBlock(entityPlayer, pos))
        {
            return true;
        }

        return !isAboveLavaOrWaterOrAir(entityPlayer.level(), pos);
    }

    public boolean isStandingOnBreakingBlock(LivingEntity entityPlayer, BlockPos pos)
    {
        BlockPos playerPos = entityPlayer.blockPosition();
        return playerPos.getX() == pos.getX() && playerPos.getZ() == pos.getZ() && playerPos.getY() == pos.getY() + 1;
    }

    public boolean isAboveBreakingBlock(LivingEntity entityPlayer, BlockPos pos)
    {
        BlockPos playerPos = entityPlayer.blockPosition();
        return playerPos.getX() == pos.getX() && playerPos.getZ() == pos.getZ() && playerPos.getY() > pos.getY();
    }

    public boolean isStandingUnderFallingBlock(LivingEntity entity, BlockPos pos)
    {
        var entityPos = entity.blockPosition();
        if (Math.abs(entityPos.getX() - pos.getX()) > 1 || Math.abs(entityPos.getZ() - pos.getZ()) > 1 || entityPos.getY() >= pos.getY())
            return false;

        var blockAboveBreaking = entity.level().getBlockState(pos.above()).getBlock();
        return blockAboveBreaking instanceof FallingBlock;
    }

    public boolean isNearLavaOrWaterOrUnsafe(LivingEntity entityPlayer, BlockPos pos)
    {
        return isNearLavaOrWater(entityPlayer.level(), pos) || !isFallSafeWide(entityPlayer, pos);
    }

    public boolean isNearLavaOrWaterOrUnsafeOrStandingOnBreakingBlock(LivingEntity entityPlayer, BlockPos pos)
    {
        return isNearLavaOrWater(entityPlayer.level(), pos) || !isFallSafeWide(entityPlayer, pos) || isStandingOnBreakingBlock(entityPlayer, pos);
    }

    private boolean isAboveLavaOrWaterOrAir(Level level, BlockPos pos)
    {
        return level.isEmptyBlock(pos.below())
                || level.getBlockState(pos.below()).getBlock() instanceof LiquidBlock;
    }

    public boolean isNearLavaOrWater(Level level, BlockPos pos)
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
                    if (block == Blocks.LAVA || block == Blocks.WATER)
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean isNearMushroomOrMycelium(Level level, BlockPos pos)
    {
        final int posX = pos.getX(), posY = pos.getY(), posZ = pos.getZ();
        final int threshold = 5;

        for (int x = posX - threshold; x <= posX + threshold; x++)
        {
            for (int y = posY - threshold; y <= posY + threshold; y++)
            {
                for (int z = posZ - threshold; z <= posZ + threshold; z++)
                {
                    Block block = level.getBlockState(new BlockPos(x, y, z)).getBlock();
                    if (block == Blocks.RED_MUSHROOM || block == Blocks.BROWN_MUSHROOM
                            || block == Blocks.RED_MUSHROOM_BLOCK || block == Blocks.BROWN_MUSHROOM_BLOCK
                            || block == Blocks.MYCELIUM || block == Blocks.MOSSY_COBBLESTONE)
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
