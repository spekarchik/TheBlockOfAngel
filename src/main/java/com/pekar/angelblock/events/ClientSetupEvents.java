package com.pekar.angelblock.events;

import com.pekar.angelblock.Main;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientSetupEvents
{
    private ClientSetupEvents()
    {}

    public static void initStatic()
    {
        // do nothing
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
        //KeyRegistry.registerKeys();
    }
}
