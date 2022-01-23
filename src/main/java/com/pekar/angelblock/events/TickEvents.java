package com.pekar.angelblock.events;

import com.pekar.angelblock.events.block_cleaner.BlockCleaner;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;

public class TickEvents implements IEventHandler
{
    @SubscribeEvent
    public void onWorldTickEvent(TickEvent.WorldTickEvent event)
    {
        if (event.phase != TickEvent.Phase.END) return;
        if (event.side != LogicalSide.SERVER) return;

        BlockCleaner.decrementOrRemove();
    }
}
