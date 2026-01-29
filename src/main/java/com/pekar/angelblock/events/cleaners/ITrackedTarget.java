package com.pekar.angelblock.events.cleaners;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

public interface ITrackedTarget
{
    ITargetBehavior getBehavior();
    Player getOwner();
    ServerLevel getTargetLevel();
    int getTicksLeft();

    void decrementTick();
    void resetTick();
    void remove();

    String getId();
}
