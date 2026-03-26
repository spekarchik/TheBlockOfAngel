package com.pekar.angelblock.events.scheduler;

import com.pekar.angelblock.events.scheduler.base.IScheduledTask;
import net.minecraft.server.level.ServerPlayer;

public interface IPlayerScheduledTask extends IScheduledTask
{
    boolean belongsTo(ServerPlayer player);
}
