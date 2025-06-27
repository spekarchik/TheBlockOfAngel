package com.pekar.angelblock.events.cleaners;

import net.minecraft.world.entity.player.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Cleaner
{
    private static final Set<ITrackedTarget> targets = ConcurrentHashMap.newKeySet();
    private static final double CloseDistanceToRemoveImmediately = 4.0;

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
            var pos = target.getPos();
            double distance = target.getOwner().distanceToSqr(pos.getX(), pos.getY(), pos.getZ());

            if (distance > target.getDistanceToDecreaseSqr())
            {
                target.decrementTick();
            }

            if (target.canBeRemoved() || distance > target.getDistanceToRemoveImmediatelySqr() ||
                (target.needToRemoveWhenClosely() && distance <= CloseDistanceToRemoveImmediately))
            {
                if (target.remove())
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
                if (target.remove())
                    targetsToRemove.add(target);
            }
        }

        for (var target : targetsToRemove)
        {
            targets.remove(target);
        }
    }
}
