package com.pekar.angelblock.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class BlockTransformations
{
    BlockTransformations()
    {

    }

    public boolean mossyTransforming(Player player, BlockPos pos, Block block)
    {
        // stones
        if (block == Blocks.STONE || block == Blocks.COBBLESTONE || block == Blocks.COBBLED_DEEPSLATE
                || block == Blocks.DEEPSLATE)
        {
            setBlock(player, pos, Blocks.MOSSY_COBBLESTONE);
            return true;
        }

        if (block == Blocks.MOSSY_COBBLESTONE)
        {
            var resultBlock = pos.getY() > 4 ? Blocks.COBBLESTONE : Blocks.COBBLED_DEEPSLATE;
            setBlock(player, pos, resultBlock);
            return true;
        }

        if (block == Blocks.COBBLESTONE_SLAB || block == Blocks.STONE_SLAB)
        {
            setBlock(player, pos, Blocks.MOSSY_COBBLESTONE_SLAB);
            return true;
        }

        if (block == Blocks.MOSSY_COBBLESTONE_SLAB)
        {
            setBlock(player, pos, Blocks.COBBLESTONE_SLAB);
            return true;
        }

        // bricks
        if (block == Blocks.STONE_BRICKS || block == Blocks.DEEPSLATE_BRICKS)
        {
            setBlock(player, pos, Blocks.MOSSY_STONE_BRICKS);
            return true;
        }

        if (block == Blocks.MOSSY_STONE_BRICKS)
        {
            var resultBlock = pos.getY() > 4 ? Blocks.STONE_BRICKS : Blocks.DEEPSLATE_BRICKS;
            setBlock(player, pos, resultBlock);
            return true;
        }

        if (block == Blocks.STONE_BRICK_SLAB || block == Blocks.DEEPSLATE_BRICK_SLAB)
        {
            setBlock(player, pos, Blocks.MOSSY_STONE_BRICK_SLAB);
            return true;
        }

        if (block == Blocks.MOSSY_STONE_BRICK_SLAB)
        {
            var resultBlock = pos.getY() > 4 ? Blocks.STONE_BRICK_SLAB : Blocks.DEEPSLATE_BRICK_SLAB;
            setBlock(player, pos, resultBlock);
            return true;
        }

        return false;
    }

    public void setMyceliumInRadius(Level level, BlockPos centerPos, int radius)
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

                    boolean canTurnIntoMycelium = currentBlock == Blocks.GRASS_BLOCK || currentBlock == Blocks.DIRT;
                    boolean canTurnIntoMossyCobleStone = currentBlock == Blocks.COBBLESTONE
                            || (currentBlock == Blocks.STONE);

                    if (!canTurnIntoMycelium && !canTurnIntoMossyCobleStone)
                    {
                        continue;
                    }

                    if (level.getBlockState(currentPos.above()).getBlock() != Blocks.GRASS_BLOCK)
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

    public void setBlockWithClientSound(Player player, BlockPos pos, BlockState blockState)
    {
        if (player instanceof ServerPlayer)
            player.level().setBlock(pos, blockState, Block.UPDATE_ALL_IMMEDIATE);

        Utils.instance.sound.playSoundByBlock(player, pos, SoundType.BLOCK_CHANGED);
    }

    private void setBlock(Player player, BlockPos pos, Block block)
    {
        setBlockWithClientSound(player, pos, block.defaultBlockState());
    }
}
