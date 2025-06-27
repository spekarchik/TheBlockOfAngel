package com.pekar.angelblock.events.cleaners;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public interface ITrackedTarget
{
    BlockPos getPos();
    Player getOwner();
    Level getLevel();
    int getTicksBeforeRemoving();
    boolean needToRemoveWhenClosely();
    boolean remove();
    void decrementTick();
    boolean canBeRemoved();

    double getDistanceToDecreaseSqr();
    double getDistanceToRemoveImmediatelySqr();
}
