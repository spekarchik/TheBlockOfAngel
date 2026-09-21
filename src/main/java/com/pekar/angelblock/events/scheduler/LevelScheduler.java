package com.pekar.angelblock.events.scheduler;

import net.minecraft.server.level.ServerLevel;

import java.util.ArrayList;
import java.util.List;

public abstract class LevelScheduler
{
    private static final List<ILevelScheduledTask> tasks = new ArrayList<>();

    public static void add(ILevelScheduledTask task)
    {
        tasks.add(task);
    }

    public static void doOnTick(ServerLevel level)
    {
        var iterator = tasks.iterator();

        while (iterator.hasNext())
        {
            var task = iterator.next();
            if (!task.belongsTo(level)) continue;
            task.decrementOrExecute();
            if (task.isCompleted()) iterator.remove();
        }
    }
}
