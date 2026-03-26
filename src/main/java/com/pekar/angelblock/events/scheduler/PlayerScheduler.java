package com.pekar.angelblock.events.scheduler;

import net.minecraft.server.level.ServerPlayer;

import java.util.ArrayList;
import java.util.List;

public abstract class PlayerScheduler
{
    private static final List<IPlayerScheduledTask> tasks = new ArrayList<>();

    public static void add(IPlayerScheduledTask task)
    {
        tasks.add(task);
    }

    public static void doOnTick(ServerPlayer player)
    {
        var iterator = tasks.iterator();

        while (iterator.hasNext())
        {
            var task = iterator.next();
            if (!task.belongsTo(player)) continue;
            task.decrementOrExecute();
            if (task.isCompleted()) iterator.remove();
        }
    }

    public static void cancelAll(ServerPlayer player)
    {
        var iterator = tasks.iterator();

        while (iterator.hasNext())
        {
            var task = iterator.next();
            if (!task.belongsTo(player)) continue;
            task.execute();
            iterator.remove();
        }
    }
}
