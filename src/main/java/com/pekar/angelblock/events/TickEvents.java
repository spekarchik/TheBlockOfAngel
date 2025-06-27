package com.pekar.angelblock.events;

import com.pekar.angelblock.events.cleaners.Cleaner;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

public class TickEvents implements IEventHandler
{
    @SubscribeEvent
    public void onWorldTickEvent(LevelTickEvent.Post event)
    {
        var level = event.getLevel();
        if (level.isClientSide) return;

        Cleaner.decrementOrRemove();
//        LightCleaner.decrementOrRemove();
    }

//    @SubscribeEvent
//    public void onTick(PlayerTickEvent.Post event)
//    {
//    }
}
