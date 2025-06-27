package com.pekar.angelblock.events.cleaners;

public abstract class TargetBehavior<T extends ITrackedTarget> implements ITargetBehavior
{
    protected final T target;

    public TargetBehavior(T target)
    {
        this.target = target;
    }
}
