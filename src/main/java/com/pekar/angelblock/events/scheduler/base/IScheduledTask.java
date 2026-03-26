package com.pekar.angelblock.events.scheduler.base;

public interface IScheduledTask
{
    void decrementOrExecute();
    void execute();
    boolean isCompleted();
    int getCounter();
}
