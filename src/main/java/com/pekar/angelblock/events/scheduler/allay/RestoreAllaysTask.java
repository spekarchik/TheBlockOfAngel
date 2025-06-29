package com.pekar.angelblock.events.scheduler.allay;

import com.pekar.angelblock.events.cleaners.AllayManager;
import com.pekar.angelblock.events.scheduler.ScheduledTask;
import net.minecraft.server.level.ServerPlayer;

public class RestoreAllaysTask extends ScheduledTask<Object>
{
    public RestoreAllaysTask(ServerPlayer player, int ticks)
    {
        super(player, ticks, null, RestoreAllaysTask::restoreAllays);
    }

    private static void restoreAllays(ServerPlayer player, Object object)
    {
        AllayManager.restoreSavedAllays(player);
    }
}
