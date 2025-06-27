package com.pekar.angelblock.events.cleaners;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public interface ITrackedTarget
{
    ITargetBehavior getBehavior();
    Player getOwner();
    Level getLevel();
    int getTicksLeft();

    void decrementTick();
    void resetTick();
    void remove();

    String getId();
}
