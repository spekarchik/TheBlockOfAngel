package com.pekar.angelblock.events;

import com.pekar.angelblock.Main;
import com.pekar.angelblock.keybinds.KeyRegistry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
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
    public static void onRegisterKeyMappingsEvent(RegisterKeyMappingsEvent event)
    {
        event.register(KeyRegistry.NIGHT_VISION);
        event.register(KeyRegistry.JUMP_BOOST);
        event.register(KeyRegistry.SUPER_JUMP);
        event.register(KeyRegistry.GLOWING);
        event.register(KeyRegistry.REGENERATION);
        event.register(KeyRegistry.LEVITATION);
        event.register(KeyRegistry.SWORD_EFFECT);
    }

    //@SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
        //KeyRegistry.registerKeys();
    }
}
