package com.pekar.angelblock.events.scheduler;

import net.minecraft.server.level.ServerPlayer;

public interface IScheduledTask<T>
{
    void decrementOrExecute();
    void execute();
    boolean isCompleted();
    boolean belongsTo(ServerPlayer player);
}
