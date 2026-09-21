package com.pekar.angelblock.events.cleaners;

public class AnvilTargetBehavior extends TargetBehavior<TrackedBlock>
{
    public AnvilTargetBehavior(TrackedBlock target)
    {
        super(target);
    }

    @Override
    public boolean shouldDecrement()
    {
        return true;
    }

    @Override
    public boolean shouldReset()
    {
        return false;
    }

    @Override
    public boolean shouldRemove()
    {
        return target.getTicksLeft() <= 0;
    }

    @Override
    public boolean shouldUntrack()
    {
        return false;
    }

    @Override
    public boolean canBeRemovedOnClean()
    {
        return true;
    }

    @Override
    public void onRemove()
    {
        // do nothing
    }

    @Override
    public void onUnableToRemove()
    {
        // do nothing
    }
}
