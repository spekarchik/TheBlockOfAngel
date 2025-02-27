package com.pekar.angelblock.events.block_cleaner;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class LightCleaner
{
    private static final Set<BlockInfo> blocks = ConcurrentHashMap.newKeySet();

    public static void add(Player player, BlockPos blockPos, int ticks)
    {
        blocks.add(new BlockInfo(player, blockPos, ticks));
    }

    public static void decrementOrRemove()
    {
        if (blocks.isEmpty()) return;

        blocks.removeIf(entry -> {
            BlockPos pos = entry.getPos();
            var serverLevel = entry.getPlayer().level();

            if (entry.canBeRemoved() && serverLevel.getBlockState(pos).is(Blocks.LIGHT))
            {
                serverLevel.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                return true;
            }
            else
            {
                entry.decrement();
                return false;
            }
        });
    }

    public static void clean(Player player)
    {
        Set<BlockInfo> blockToRemove = new HashSet<>();

        for (var blockInfo : blocks)
        {
            if (blockInfo.getPlayer().getUUID().equals(player.getUUID()))
            {
                BlockPos pos = blockInfo.getPos();
                var level = blockInfo.getPlayer().level();

                blockToRemove.add(blockInfo);

                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            }
        }

        for (BlockInfo blockInfo : blockToRemove)
        {
            blocks.remove(blockInfo);
        }
    }
}
