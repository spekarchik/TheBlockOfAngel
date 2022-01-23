package com.pekar.angelblock.events;

import com.pekar.angelblock.events.block_cleaner.BlockCleaner;
import com.pekar.angelblock.network.packets.ClientTickPacket;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;

public class TickEvents implements IEventHandler
{
    private static final ClientTickPacket clientTickPacket = new ClientTickPacket();
    private static int tickCounter;

    @SubscribeEvent
    public void onClientTickEvent(TickEvent.ClientTickEvent event)
    {
        if (event.phase != TickEvent.Phase.END) return;

        if (++tickCounter > 10)
        {
            clientTickPacket.sendToServer();
            tickCounter = 0;
        }
    }

    @SubscribeEvent
    public void onWorldTickEvent(TickEvent.WorldTickEvent event)
    {
        if (event.phase != TickEvent.Phase.END) return;
        if (event.side != LogicalSide.SERVER) return;

        BlockCleaner.decrementOrRemove();
    }
}
