package com.pekar.angelblock.events.scheduler;

import com.pekar.angelblock.events.scheduler.base.IScheduledTask;
import net.minecraft.server.level.ServerLevel;

public interface ILevelScheduledTask extends IScheduledTask
{
    boolean belongsTo(ServerLevel level);
}
