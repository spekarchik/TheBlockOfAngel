package com.pekar.angelblock.events.block_cleaner;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;

import java.util.*;

public class BlockCleaner
{
    private static final Set<BlockInfo> blocks = new HashSet<>();
    private static final double DistanceToDecrease = 100.0;
    private static final double DistanceToRemoveImmediately = 3600.0;
    private static final double CloseDistanceToRemoveImmediately = 4.0;

    public synchronized static void add(Player player, BlockPos blockPos, int ticks, boolean setToAir, boolean removeWhenClosely)
    {
        blocks.add(new BlockInfo(player, blockPos, ticks, setToAir, removeWhenClosely));
    }

    public synchronized static void decrementOrRemove()
    {
        if (blocks.isEmpty()) return;

        Set<BlockInfo> blockToRemove = new HashSet<>();

        for (BlockInfo blockInfo : blocks)
        {
            BlockPos pos = blockInfo.getPos();
            double distance = blockInfo.getPlayer().distanceToSqr(pos.getX(), pos.getY(), pos.getZ());

            if (distance > DistanceToDecrease)
            {
                blockInfo.decrease();
            }

            if (blockInfo.canBeRemoved() || distance > DistanceToRemoveImmediately ||
                (blockInfo.removeWhenClosely() && distance <= CloseDistanceToRemoveImmediately))
            {
                var level = blockInfo.getPlayer().level();
                blockToRemove.add(blockInfo);
                if (level.isEmptyBlock(pos)) continue;

                if (blockInfo.setToAir())
                {
                    level.removeBlock(pos, true /*update neighbour etc.? - actually does nothing*/);
                }
                else
                {
                    level.destroyBlock(pos, false /*drop items*/);
                }
            }
        }

        for (BlockInfo blockInfo : blockToRemove)
        {
            blocks.remove(blockInfo);
        }
    }

    public synchronized static void clean(Player player)
    {
        if (blocks.isEmpty()) return;

        Set<BlockInfo> blockToRemove = new HashSet<>();

        for (BlockInfo blockInfo : blocks)
        {
            if (blockInfo.getPlayer().getName().equals(player.getName()))
            {
                BlockPos pos = blockInfo.getPos();
                var level = blockInfo.getPlayer().level();

                blockToRemove.add(blockInfo);

                if (level.isEmptyBlock(pos)) continue;

                if (blockInfo.setToAir())
                {
                    level.removeBlock(pos, true);
                }
                else
                {
                    level.destroyBlock(pos, false);
                }
            }
        }

        for (BlockInfo blockInfo : blockToRemove)
        {
            blocks.remove(blockInfo);
        }
    }
}
