package com.pekar.angelblock.events.cleaners;

public interface ITargetBehavior
{
    boolean shouldDecrement();
    boolean shouldReset();
    boolean shouldRemove();
    boolean shouldUntrack();
    boolean canBeRemovedOnClean();
    void onRemove();
    void onUnableToRemove();
}
