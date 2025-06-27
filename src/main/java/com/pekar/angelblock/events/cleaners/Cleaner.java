package com.pekar.angelblock.events.cleaners;

import net.minecraft.world.entity.player.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Cleaner
{
    private static final Set<ITrackedTarget> targets = ConcurrentHashMap.newKeySet();

    public static void add(ITrackedTarget trackedTarget)
    {
        targets.add(trackedTarget);
    }

    public static void decrementOrRemove()
    {
        if (targets.isEmpty()) return;

        Set<ITrackedTarget> targetsToRemove = new HashSet<>();

        for (var target : targets)
        {
            var targetBehavior = target.getBehavior();
            if (targetBehavior.shouldDecrement())
            {
                target.decrementTick();
            }

            if (targetBehavior.shouldReset())
            {
                target.resetTick();
            }

            if (targetBehavior.shouldRemove())
            {
                target.remove();
                targetsToRemove.add(target);
            }
        }

        for (var target : targetsToRemove)
        {
            targets.remove(target);
        }
    }

    public static void clean(Player player)
    {
        if (targets.isEmpty()) return;

        Set<ITrackedTarget> targetsToRemove = new HashSet<>();

        for (var target : targets)
        {
            if (target.getOwner().getUUID().equals(player.getUUID()))
            {
                if (target.getBehavior().canBeRemovedOnClean())
                {
                    target.remove();
                    targetsToRemove.add(target);
                }
            }
        }

        for (var target : targetsToRemove)
        {
            targets.remove(target);
        }
    }
}
