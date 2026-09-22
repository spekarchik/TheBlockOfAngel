package com.pekar.angelblock.events;

import com.pekar.angelblock.events.scheduler.LevelScheduler;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;

public class LevelSchedulerEvents implements IEventHandler
{
    @SubscribeEvent
    public void onLevelUnload(LevelEvent.Unload event)
    {
        if (event.getLevel() instanceof ServerLevel serverLevel)
            LevelScheduler.executeAll(serverLevel);
    }

    @SubscribeEvent
    public void onServerStopping(ServerStoppingEvent event)
    {
        LevelScheduler.executeAll();
    }
}
