package com.pekar.angelblock.events;

import com.pekar.angelblock.Main;
import com.pekar.angelblock.keybinds.KeyRegistry;
import com.pekar.angelblock.potions.PotionRegistry;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@EventBusSubscriber(modid = Main.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
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

    @SubscribeEvent
    public static void onClientSetup(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerEntityRenderer(PotionRegistry.BLOCK_BREAKER_POTION.get(), ThrownItemRenderer::new);
    }
}
