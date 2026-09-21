package com.pekar.angelblock.events.scheduler;

import com.pekar.angelblock.events.scheduler.base.ScheduledTask;
import net.minecraft.server.level.ServerLevel;

import java.util.function.Consumer;

public class LevelScheduledTask extends ScheduledTask<ServerLevel> implements ILevelScheduledTask
{
    public LevelScheduledTask(ServerLevel level, int ticks, Consumer<ServerLevel> doOnComplete)
    {
        super(ticks, level, doOnComplete);
    }

    @Override
    public final boolean belongsTo(ServerLevel level)
    {
        return getObject() == level;
    }
}
