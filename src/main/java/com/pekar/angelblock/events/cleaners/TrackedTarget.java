package com.pekar.angelblock.events.cleaners;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.function.Predicate;

public abstract class TrackedTarget<T> implements ITrackedTarget
{
    private final T targetInstance;
    private final BlockPos pos;
    private final Player owner;
    private int ticksBeforeRemoving;
    private final boolean removeWhenClosely;
    private final Predicate<ITrackedTarget> doOnRemove;
    private final Level level;
    private final double distanceToDecreaseSqr;
    private final double distanceToRemoveImmediatelySqr;

    public TrackedTarget(T targetInstance, BlockPos pos, Player owner, int ticksBeforeRemoving, boolean removeWhenClosely,
                         Predicate<ITrackedTarget> doOnRemove, double distanceToDecrease, double distanceToRemoveImmediately)
    {
        this.targetInstance = targetInstance;
        this.pos = pos;
        this.owner = owner;
        this.ticksBeforeRemoving = ticksBeforeRemoving;
        this.removeWhenClosely = removeWhenClosely;
        this.doOnRemove = doOnRemove;
        this.level = owner.level();
        this.distanceToDecreaseSqr = distanceToDecrease * distanceToDecrease;
        this.distanceToRemoveImmediatelySqr = distanceToRemoveImmediately * distanceToRemoveImmediately;
    }

    @Override
    public BlockPos getPos()
    {
        return pos;
    }

    @Override
    public Player getOwner()
    {
        return owner;
    }

    @Override
    public Level getLevel()
    {
        return level;
    }

    @Override
    public int getTicksBeforeRemoving()
    {
        return ticksBeforeRemoving;
    }

    @Override
    public boolean needToRemoveWhenClosely()
    {
        return removeWhenClosely;
    }

    @Override
    public boolean remove()
    {
        return doOnRemove.test(this);
    }

    @Override
    public void decrementTick()
    {
        --ticksBeforeRemoving;
    }

    @Override
    public boolean canBeRemoved()
    {
        return ticksBeforeRemoving <= 0;
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

    private String getId()
    {
        return targetInstance.getClass().getName() + pos.asLong();
    }

    @Override
    public int hashCode()
    {
        return pos.hashCode();
    }

    @Override
    public double getDistanceToDecreaseSqr()
    {
        return distanceToDecreaseSqr;
    }

    @Override
    public double getDistanceToRemoveImmediatelySqr()
    {
        return distanceToRemoveImmediatelySqr;
    }

    protected final T getTargetInstance()
    {
        return targetInstance;
    }
}
