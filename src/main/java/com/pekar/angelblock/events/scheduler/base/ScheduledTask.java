package com.pekar.angelblock.events.scheduler.base;

import java.util.function.Consumer;

public class ScheduledTask<T> implements IScheduledTask
{
    private final T object;
    private final Consumer<T> doOnComplete;
    private int counter;
    private boolean isCompleted;

    public ScheduledTask(int ticks, T object, Consumer<T> doOnComplete)
    {
        this.counter = ticks;
        this.object = object;
        this.doOnComplete = doOnComplete;
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
            doOnComplete.accept(object);
            isCompleted = true;
        }
    }

    @Override
    public final boolean isCompleted()
    {
        return isCompleted;
    }

    @Override
    public int getCounter()
    {
        return counter;
    }

    protected T getObject()
    {
        return object;
    }
}
