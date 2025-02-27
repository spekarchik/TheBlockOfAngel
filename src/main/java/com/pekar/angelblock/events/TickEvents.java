package com.pekar.angelblock.events;

import com.pekar.angelblock.events.block_cleaner.BlockCleaner;
import com.pekar.angelblock.events.block_cleaner.LightCleaner;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

public class TickEvents implements IEventHandler
{
    @SubscribeEvent
    public void onWorldTickEvent(LevelTickEvent.Post event)
    {
        var level = event.getLevel();
        if (level.isClientSide) return;

        BlockCleaner.decrementOrRemove();
        LightCleaner.decrementOrRemove();
    }

//    @SubscribeEvent
//    public void onTick(PlayerTickEvent.Post event)
//    {
//    }
}
