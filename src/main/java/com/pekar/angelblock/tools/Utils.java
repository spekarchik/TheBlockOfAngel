package com.pekar.angelblock.tools;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class Utils
{
    private Utils()
    {}

    public static Random random = new Random();

    public static Direction getDirection(LivingEntity entityLiving, BlockPos pos)
    {
        BlockPos entityPos = entityLiving.blockPosition();
        int entityX = entityPos.getX(), entityY = entityPos.getY(), entityZ = entityPos.getZ();

        if (pos.getY() > entityY + 2)
        {
            return Direction.UP;
        }
        else if (entityY - pos.getY() > 1)
        {
            return Direction.DOWN;
        }
        else if (pos.getY() < entityY)
        {
            if (Math.abs(pos.getX() - entityX) < 2 && Math.abs(pos.getZ() - entityZ) < 2)
            {
                return Direction.DOWN;
            }
        }

        return entityLiving.getDirection();
    }

    public static Direction getDirectionForShovel(LivingEntity entityLiving, BlockPos pos)
    {
        BlockPos entityPos = entityLiving.blockPosition();
        int entityY = entityPos.getY();

        if (pos.getY() > entityY + 2)
        {
            return Direction.UP;
        }
        else if (entityY >= pos.getY())
        {
            return Direction.DOWN;
        }

        return entityLiving.getDirection();
    }

    public static boolean isNearLava(Level level, BlockPos pos)
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
                    if (block == Blocks.LAVA || block == Blocks.LAVA_CAULDRON)
                        return true;
                }
            }
        }

        return false;
    }

    public static boolean isNearWater(Level level, BlockPos pos)
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
                    if (block == Blocks.WATER || block == Blocks.WATER_CAULDRON)
                        return true;
                }
            }
        }

        return false;
    }

    public static boolean isNearWaterHorizoltal(Level level, BlockPos pos)
    {
        return level.isWaterAt(pos.east()) || level.isWaterAt(pos.west()) || level.isWaterAt(pos.north())
                || level.isWaterAt(pos.south());
    }

    public static boolean isFallSafeWide(LivingEntity entityPlayer, BlockPos pos)
    {
        BlockPos playerPos = entityPlayer.blockPosition();

        if (playerPos.getY() <= pos.getY()
                || Math.abs(playerPos.getX() - pos.getX()) >= 2
                || Math.abs(playerPos.getZ() - pos.getZ()) >= 2)
        {
            return true;
        }

        return !isAboveLavaOrWaterOrAir(entityPlayer.level, pos);
    }

    public static boolean isFallSafeExact(LivingEntity entityPlayer, BlockPos pos)
    {
        BlockPos playerPos = entityPlayer.blockPosition();

        if (playerPos.getY() != pos.getY() + 1
                || playerPos.getX() != pos.getX()
                || playerPos.getZ() != pos.getZ())
        {
            return true;
        }

        return !isAboveLavaOrWaterOrAir(entityPlayer.level, pos);
    }

    public static boolean isStandingOnBreakingBlock(LivingEntity entityPlayer, BlockPos pos)
    {
        BlockPos playerPos = entityPlayer.blockPosition();
        return playerPos.getX() == pos.getX() && playerPos.getZ() == pos.getZ() && playerPos.getY() == pos.getY() + 1;
    }

    public static boolean isNearLavaOrWaterOrUnsafe(LivingEntity entityPlayer, BlockPos pos)
    {
        return isNearLavaOrWater(entityPlayer.level, pos) || !isFallSafeWide(entityPlayer, pos);
    }

    public static boolean isNearLavaOrWaterOrUnsafeOrStandingOnBreakingBlock(LivingEntity entityPlayer, BlockPos pos)
    {
        return isNearLavaOrWater(entityPlayer.level, pos) || !isFallSafeWide(entityPlayer, pos) || isStandingOnBreakingBlock(entityPlayer, pos);
    }

    public static boolean isNearMushroomOrMycelium(Level level, BlockPos pos)
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

    public static void setMyceliumInRadius(Level level, BlockPos centerPos, int radius)
    {
        final int posX = centerPos.getX(), posY = centerPos.getY(), posZ = centerPos.getZ();

        for (int x = posX - radius; x <= posX + radius; x++)
        {
            for (int z = posZ - radius; z <= posZ + radius; z++)
            {
                for (int y = posY + radius; y >= posY - radius; y--)
                {
                    BlockPos currentPos = new BlockPos(x, y, z);

                    BlockState currentBlockState = level.getBlockState(currentPos);
                    Block currentBlock = currentBlockState.getBlock();

                    boolean canTurnIntoMycelium = currentBlock == Blocks.GRASS || currentBlock == Blocks.DIRT;
                    boolean canTurnIntoMossyCobleStone = currentBlock == Blocks.COBBLESTONE
                            || (currentBlock == Blocks.STONE);

                    if (!canTurnIntoMycelium && !canTurnIntoMossyCobleStone)
                    {
                        continue;
                    }

                    if (level.getBlockState(currentPos.above()).getBlock() != Blocks.GRASS)
                    {
                        continue;
                    }

                    Block upper2Block = level.getBlockState(currentPos.above(2)).getBlock();

                    if (level.isEmptyBlock(currentPos.above(2)) || upper2Block == Blocks.BROWN_MUSHROOM
                        || upper2Block == Blocks.RED_MUSHROOM)
                    {
                        if (canTurnIntoMycelium)
                        {
                            level.setBlock(currentPos, Blocks.MYCELIUM.defaultBlockState(), 11);
                        }
                        else
                        {
                            level.setBlock(currentPos, Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 11);
                        }

                        break;
                    }
                }
            }
        }
    }

    private static boolean isAboveLavaOrWaterOrAir(Level level, BlockPos pos)
    {
        Block block = level.getBlockState(pos.below()).getBlock();
        return level.isEmptyBlock(pos.below())
                || block == Blocks.LAVA || block == Blocks.LAVA_CAULDRON
                || block == Blocks.WATER || block == Blocks.WATER_CAULDRON;
    }

    public static boolean isNearLavaOrWater(Level level, BlockPos pos)
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
                    if (block == Blocks.LAVA || block == Blocks.LAVA_CAULDRON
                            || block == Blocks.WATER || block == Blocks.WATER_CAULDRON)
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static boolean mossyTransforming(Level level, BlockPos pos, Block block)
    {
        // stones
        if (block == Blocks.COBBLESTONE || block == Blocks.COBBLED_DEEPSLATE
                || block == Blocks.DEEPSLATE)
        {
            level.setBlock(pos, Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 11);
            return true;
        }

        if (block == Blocks.MOSSY_COBBLESTONE)
        {
            var resultBlock = pos.getY() > 4 ? Blocks.COBBLESTONE : Blocks.COBBLED_DEEPSLATE;
            level.setBlock(pos, resultBlock.defaultBlockState(), 11);
            return true;
        }

        if (block == Blocks.COBBLESTONE_SLAB || block == Blocks.STONE_SLAB)
        {
            level.setBlock(pos, Blocks.MOSSY_COBBLESTONE_SLAB.defaultBlockState(), 11);
            return true;
        }

        if (block == Blocks.MOSSY_COBBLESTONE_SLAB)
        {
            level.setBlock(pos, Blocks.COBBLESTONE_SLAB.defaultBlockState(), 11);
            return true;
        }

        // bricks
        if (block == Blocks.STONE_BRICKS || block == Blocks.DEEPSLATE_BRICKS)
        {
            level.setBlock(pos, Blocks.MOSSY_STONE_BRICKS.defaultBlockState(), 11);
            return true;
        }

        if (block == Blocks.MOSSY_STONE_BRICKS)
        {
            var resultBlock = pos.getY() > 4 ? Blocks.STONE_BRICKS : Blocks.DEEPSLATE_BRICKS;
            level.setBlock(pos, resultBlock.defaultBlockState(), 11);
            return true;
        }

        if (block == Blocks.STONE_BRICK_SLAB || block == Blocks.DEEPSLATE_BRICK_SLAB)
        {
            level.setBlock(pos, Blocks.MOSSY_STONE_BRICK_SLAB.defaultBlockState(), 11);
            return true;
        }

        if (block == Blocks.MOSSY_STONE_BRICK_SLAB)
        {
            var resultBlock = pos.getY() > 4 ? Blocks.STONE_BRICK_SLAB : Blocks.DEEPSLATE_BRICK_SLAB;
            level.setBlock(pos, resultBlock.defaultBlockState(), 11);
            return true;
        }

        return false;
    }
}
