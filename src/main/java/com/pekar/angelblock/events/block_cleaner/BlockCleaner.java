package com.pekar.angelblock.events.block_cleaner;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BlockCleaner
{
    private static final Set<BlockInfoExtended> blocks = ConcurrentHashMap.newKeySet();
    private static final double DistanceToDecrease = 100.0;
    private static final double DistanceToRemoveImmediately = 3600.0;
    private static final double CloseDistanceToRemoveImmediately = 4.0;

    public static void add(Player player, BlockPos blockPos, int ticks, boolean setToAir, boolean removeWhenClosely)
    {
        blocks.add(new BlockInfoExtended(player, blockPos, ticks, setToAir, removeWhenClosely));
    }

    public static void decrementOrRemove()
    {
        if (blocks.isEmpty()) return;

        Set<BlockInfoExtended> blockToRemove = new HashSet<>();

        for (BlockInfoExtended blockInfo : blocks)
        {
            BlockPos pos = blockInfo.getPos();
            double distance = blockInfo.getPlayer().distanceToSqr(pos.getX(), pos.getY(), pos.getZ());

            if (distance > DistanceToDecrease)
            {
                blockInfo.decrement();
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

        for (BlockInfoExtended blockInfo : blockToRemove)
        {
            blocks.remove(blockInfo);
        }
    }

    public static void clean(Player player)
    {
        if (blocks.isEmpty()) return;

        Set<BlockInfoExtended> blockToRemove = new HashSet<>();

        for (BlockInfoExtended blockInfo : blocks)
        {
            if (blockInfo.getPlayer().getUUID().equals(player.getUUID()))
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

        for (BlockInfoExtended blockInfo : blockToRemove)
        {
            blocks.remove(blockInfo);
        }
    }
}
