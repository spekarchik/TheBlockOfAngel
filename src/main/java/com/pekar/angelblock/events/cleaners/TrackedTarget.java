package com.pekar.angelblock.events.cleaners;

import net.minecraft.world.entity.player.Player;

public abstract class TrackedTarget<T> implements ITrackedTarget
{
    private final T targetInstance;
    private final Player owner;
    private int ticksLeftBeforeRemoving;
    private final int ticksBeforeRemoving;
    private ITargetBehavior behavior;

    public TrackedTarget(T targetInstance,
                         Player owner,
                         int ticksBeforeRemoving)
    {
        this.targetInstance = targetInstance;
        this.owner = owner;
        this.ticksBeforeRemoving = ticksBeforeRemoving;
        this.ticksLeftBeforeRemoving = ticksBeforeRemoving;
    }

    @Override
    public final ITargetBehavior getBehavior()
    {
        if (behavior == null)
        {
            behavior = createBehavior();
        }

        return behavior;
    }

    @Override
    public Player getOwner()
    {
        return owner;
    }

    @Override
    public int getTicksLeft()
    {
        return ticksLeftBeforeRemoving;
    }

    @Override
    public void decrementTick()
    {
        --ticksLeftBeforeRemoving;
    }

    @Override
    public void resetTick()
    {
        ticksLeftBeforeRemoving = ticksBeforeRemoving;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        else
        {
            if (!(obj instanceof TrackedTarget<?>))
            {
                return false;
            }
        }

        var other = (TrackedTarget<?>) obj;
        return getId().equals(other.getId());
    }

    @Override
    public int hashCode()
    {
        return getId().hashCode();
    }

    protected final T getTargetInstance()
    {
        return targetInstance;
    }

    protected abstract ITargetBehavior createBehavior();
}
