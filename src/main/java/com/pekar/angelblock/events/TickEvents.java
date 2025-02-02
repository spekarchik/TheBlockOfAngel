package com.pekar.angelblock.events;

import com.pekar.angelblock.events.block_cleaner.BlockCleaner;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

public class TickEvents implements IEventHandler
{
    @SubscribeEvent
    public void onWorldTickEvent(LevelTickEvent event)
    {
        if (event.getLevel().isClientSide) return;

        BlockCleaner.decrementOrRemove();
    }
}
