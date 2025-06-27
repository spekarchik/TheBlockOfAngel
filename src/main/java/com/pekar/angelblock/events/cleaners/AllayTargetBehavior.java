package com.pekar.angelblock.events.cleaners;

public class AllayTargetBehavior extends TargetBehavior<TrackedAllay>
{
    private static final double DistanceToDecrease = 16.0;
    private static final double DistanceToRemoveImmediately = 60.0;

    public AllayTargetBehavior(TrackedAllay target)
    {
        super(target);
    }

    @Override
    public boolean shouldDecrement()
    {
        return getDistanceToOwner() > DistanceToDecrease;
    }

    @Override
    public boolean shouldReset()
    {
        if (getDistanceToOwner() <= DistanceToDecrease)
            return true;

        return isPersistent();
    }

    @Override
    public boolean shouldRemove()
    {
        if (target.getTicksLeft() <= 0)
            return true;

        var distanceToOwner = getDistanceToOwner();

        return distanceToOwner > DistanceToRemoveImmediately;
    }

    @Override
    public boolean canBeRemovedOnClean()
    {
        return !isPersistent();
    }

    private double getDistanceToOwner()
    {
        return target.getOwner().distanceTo(target.getTargetInstance());
    }

    private boolean isPersistent()
    {
        var allay = target.getTargetInstance();

        return allay.isHolding(stack -> !stack.isEmpty())
                || allay.hasCustomName();
    }
}
