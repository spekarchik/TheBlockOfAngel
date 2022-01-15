package com.pekar.angelblock.events;

import net.minecraftforge.common.MinecraftForge;

public class EventRegistry
{
    public static void registerEvents()
    {
//        register((PlayerBasicEvents)PlayerBasicEvents.instance());
//        register(new PlayerDataEvents());
//        register(new PlayerInteractionEvents());
//        register(new KeyboardMouseEvents());
//        register(new TickEvents());
        register(new WorldEvents());
    }

    private static void register(IEventHandler eventHandler)
    {
        MinecraftForge.EVENT_BUS.register(eventHandler);
    }
}
