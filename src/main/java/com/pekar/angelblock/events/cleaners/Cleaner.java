package com.pekar.angelblock.events.cleaners;

import net.minecraft.world.entity.player.Player;

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

        var iterator = targets.iterator();

        while (iterator.hasNext())
        {
            var target = iterator.next();
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
                iterator.remove();
            }
        }
    }

    public static void clean(Player player)
    {
        if (targets.isEmpty()) return;

        var iterator = targets.iterator();

        while (iterator.hasNext())
        {
            var target = iterator.next();

            if (target.getOwner().getUUID().equals(player.getUUID()))
            {
                var behavior = target.getBehavior();
                if (behavior.canBeRemovedOnClean())
                {
                    target.remove();
                    behavior.onRemove();
                }
                else
                {
                    behavior.onUnableToRemove();
                }

                iterator.remove();
            }
        }
    }
}
