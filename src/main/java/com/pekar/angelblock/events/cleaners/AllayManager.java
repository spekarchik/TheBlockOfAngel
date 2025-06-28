package com.pekar.angelblock.events.cleaners;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

public abstract class AllayManager
{
    public static void restoreSavedAllays(Player player)
    {
        var level = (ServerLevel) player.level();
        var storage = TrackedAllaysData.get(level);
        var trackedAllays = TrackedAllaysData.restoreAllays(level, storage);
        for (var target : trackedAllays)
        {
            Cleaner.add(target);
            storage.remove(target);
        }
    }

    public static void removeFromStorage(TrackedAllay allay)
    {
        TrackedAllaysData.get(allay.getTargetLevel()).remove(allay);
    }

    public static void store(TrackedAllay allay)
    {
        TrackedAllaysData.get(allay.getTargetLevel()).store(allay);
    }
}
