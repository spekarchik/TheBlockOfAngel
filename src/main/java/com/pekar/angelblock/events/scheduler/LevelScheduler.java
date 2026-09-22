package com.pekar.angelblock.events.scheduler;

import net.minecraft.server.level.ServerLevel;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

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

    public static void executeAll(ServerLevel level)
    {
        executeAll(task -> task.belongsTo(level));
    }

    public static void executeAll()
    {
        executeAll(task -> true);
    }

    private static void executeAll(Predicate<ILevelScheduledTask> shouldExecute)
    {
        var tasksToExecute = new ArrayList<ILevelScheduledTask>();
        var iterator = tasks.iterator();

        while (iterator.hasNext())
        {
            var task = iterator.next();
            if (!shouldExecute.test(task)) continue;

            tasksToExecute.add(task);
            iterator.remove();
        }

        tasksToExecute.forEach(ILevelScheduledTask::execute);
    }
}
