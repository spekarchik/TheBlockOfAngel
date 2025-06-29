package com.pekar.angelblock.events.scheduler;

import net.minecraft.server.level.ServerPlayer;

import java.util.function.BiConsumer;

public abstract class ScheduledTask<T> implements IScheduledTask<T>
{
    private final T object;
    private final BiConsumer<ServerPlayer, T> work;
    private final ServerPlayer player;
    private int counter;
    private boolean isCompleted;

    public ScheduledTask(ServerPlayer player, int ticks, T object, BiConsumer<ServerPlayer, T> work)
    {
        this.player = player;
        this.counter = ticks;
        this.object = object;
        this.work = work;
    }

    @Override
    public final void decrementOrExecute()
    {
        if (--counter <= 0) execute();
    }

    @Override
    public final void execute()
    {
        if (!isCompleted())
        {
            work.accept(player, object);
            isCompleted = true;
        }
    }

    @Override
    public final boolean isCompleted()
    {
        return isCompleted;
    }

    @Override
    public final boolean belongsTo(ServerPlayer player)
    {
        return this.player.getUUID().equals(player.getUUID());
    }
}
