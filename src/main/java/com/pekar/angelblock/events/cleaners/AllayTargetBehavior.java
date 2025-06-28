package com.pekar.angelblock.events.cleaners;

import net.minecraft.world.entity.animal.allay.Allay;

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
        if (isDied(target.getTargetInstance())) return true;

        if (isPersistent()) return false;

        if (target.getTicksLeft() <= 0) return true;

        var distanceToOwner = getDistanceToOwner();

        return distanceToOwner > DistanceToRemoveImmediately;
    }

    @Override
    public boolean canBeRemovedOnClean()
    {
        if (isDied(target.getTargetInstance())) return true;

        return !isPersistent();
    }

    @Override
    public void onRemove()
    {
        AllayManager.removeFromStorage(target);
    }

    @Override
    public void onUnableToRemove()
    {
        AllayManager.store(target);
    }

    private double getDistanceToOwner()
    {
        return target.getOwner().distanceTo(target.getTargetInstance());
    }

    private boolean isPersistent()
    {
        var allay = target.getTargetInstance();
        var removalReason = allay.getRemovalReason();
        if (allay.isRemoved() && removalReason != null && !removalReason.shouldDestroy()) return true;

        return allay.isHolding(stack -> !stack.isEmpty())
                || allay.hasCustomName();
    }

    private static boolean isDied(Allay targetInstance)
    {
        var removalReason = targetInstance.getRemovalReason();

        if (targetInstance.isRemoved() && removalReason != null && removalReason.shouldDestroy()) return true;
        return false;
    }
}
