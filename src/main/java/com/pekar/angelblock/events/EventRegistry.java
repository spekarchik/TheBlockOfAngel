package com.pekar.angelblock.events;

import static net.neoforged.neoforge.common.NeoForge.EVENT_BUS;

public class EventRegistry
{
    public static void registerEvents()
    {
        register((PlayerManager)PlayerManager.instance());
        register(new PlayerDataEvents());
        register(new PlayerInteractionEvents());
        register(new TickEvents());
        register(new WorldEvents());
    }

    private static void register(IEventHandler eventHandler)
    {
        EVENT_BUS.register(eventHandler);
    }
}
