package com.pekar.angelblock.events.cleaners;

class AllayTargetBehavior extends TargetBehavior<TrackedAllay>
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
        if (isPersistent()) return false;

        if (target.getTicksLeft() <= 0) return true;

        var distanceToOwner = getDistanceToOwner();

        return distanceToOwner >= DistanceToRemoveImmediately;
    }

    @Override
    public boolean shouldUntrack()
    {
        var allay = target.getTargetInstance();
        return allay.isRemoved() || allay.hasCustomName();
    }

    @Override
    public boolean canBeRemovedOnClean()
    {
        return !isPersistent();
    }

    @Override
    public void onRemove()
    {
    }

    @Override
    public void onUnableToRemove()
    {
    }

    private double getDistanceToOwner()
    {
        var owner = target.getOwner();
        return owner == null ? DistanceToRemoveImmediately : owner.distanceTo(target.getTargetInstance());
    }

    private boolean isPersistent()
    {
        var allay = target.getTargetInstance();
        var removalReason = allay.getRemovalReason();
        if (allay.isRemoved() && removalReason != null && !removalReason.shouldDestroy()) return true;

        return allay.isHolding(stack -> !stack.isEmpty())
                || allay.hasCustomName();
    }
}
