package com.pekar.angelblock.events.cleaners;

public class BlockTargetBehavior extends TargetBehavior<TrackedBlock>
{
    private static final double DistanceToDecreaseSqr = 100.0;
    private static final double DistanceToRemoveImmediatelySqr = 3600.0;
    private static final double CloseDistanceToRemoveImmediatelySqr = 4.0;

    public BlockTargetBehavior(TrackedBlock target)
    {
        super(target);
    }

    @Override
    public boolean shouldDecrement()
    {
        return getDistanceToOwnerSqr() > DistanceToDecreaseSqr;
    }

    @Override
    public boolean shouldReset()
    {
        return false;
    }

    @Override
    public boolean shouldRemove()
    {
        if (target.getTicksLeft() <= 0)
            return true;

        var distanceToOwnerSqr = getDistanceToOwnerSqr();

        return distanceToOwnerSqr > DistanceToRemoveImmediatelySqr
            || (target.needToRemoveWhenClosely() && distanceToOwnerSqr <= CloseDistanceToRemoveImmediatelySqr);
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

    private double getDistanceToOwnerSqr()
    {
        var pos = target.getPos();
        return target.getOwner().distanceToSqr(pos.getX(), pos.getY(), pos.getZ());
    }
}
