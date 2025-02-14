package com.pekar.angelblock.events;

import com.pekar.angelblock.network.PacketRegistry;
import net.neoforged.bus.api.IEventBus;

import static net.neoforged.neoforge.common.NeoForge.EVENT_BUS;

public class EventRegistry
{
    public static void registerEvents()
    {
        register((PlayerManager)PlayerManager.instance());
        register(new PlayerDataEvents());
        register(new PlayerInteractionEvents());
        register(new TickEvents());
        //register(new WorldEvents());
    }

    public static void registerEventsOnModBus(IEventBus modEventBus)
    {
        register(modEventBus, new PacketRegistry());
    }

    private static void register(IEventHandler eventHandler)
    {
        EVENT_BUS.register(eventHandler);
    }

    private static void register(IEventBus modEventBus, IEventHandler eventHandler)
    {
        modEventBus.register(eventHandler);
    }
}
