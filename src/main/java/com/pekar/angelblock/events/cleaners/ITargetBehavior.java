package com.pekar.angelblock.events.cleaners;

public interface ITargetBehavior
{
    boolean shouldDecrement();
    boolean shouldReset();
    boolean shouldRemove();
    boolean canBeRemovedOnClean();
}
