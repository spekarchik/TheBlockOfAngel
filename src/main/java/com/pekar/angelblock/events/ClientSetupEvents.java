package com.pekar.angelblock.events;

import com.pekar.angelblock.Main;
import com.pekar.angelblock.keybinds.KeyRegistry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientSetupEvents
{
    private ClientSetupEvents()
    {}

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
        KeyRegistry.registerKeys();
        MinecraftForge.EVENT_BUS.register(new ClientTickEvents());
    }
}
