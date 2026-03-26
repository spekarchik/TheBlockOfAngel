package com.pekar.angelblock.events.scheduler.allay;

import com.pekar.angelblock.events.cleaners.AllayManager;
import com.pekar.angelblock.events.scheduler.PlayerScheduledTask;
import net.minecraft.server.level.ServerPlayer;

public class RestoreAllaysTask extends PlayerScheduledTask
{
    public RestoreAllaysTask(ServerPlayer player, int ticks)
    {
        super(player, ticks, AllayManager::restoreSavedAllays);
    }
}
