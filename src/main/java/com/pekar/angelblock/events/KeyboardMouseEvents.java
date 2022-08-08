package com.pekar.angelblock.events;

import com.pekar.angelblock.Main;
import com.pekar.angelblock.keybinds.KeyRegistry;
import com.pekar.angelblock.network.ClientToServerPacket;
import com.pekar.angelblock.network.packets.KeyPressedPacket;
import com.pekar.angelblock.network.packets.ToolsModeChangePacket;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.time.Clock;
import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class KeyboardMouseEvents
{
    private static final Map<String, Long> lastTime = new HashMap<>();

    public static void initStatic()
    {
        // do nothing
    }

    @SubscribeEvent
    public static void onKeyInputEvent(InputEvent.Key event)
    {
        if (KeyRegistry.JUMP_BOOST.isDown())
        {
            var keyName = KeyRegistry.JUMP_BOOST.getName();
            trySendPacket(keyName, new KeyPressedPacket(keyName));
        }

        if (KeyRegistry.NIGHT_VISION.isDown())
        {
            var keyName = KeyRegistry.NIGHT_VISION.getName();
            trySendPacket(keyName, new KeyPressedPacket(keyName));
        }

        if (KeyRegistry.GLOWING.isDown())
        {
            var keyName = KeyRegistry.GLOWING.getName();
            trySendPacket(keyName, new KeyPressedPacket(keyName));
        }

        if (KeyRegistry.REGENERATION.isDown())
        {
            var keyName = KeyRegistry.REGENERATION.getName();
            trySendPacket(keyName, new KeyPressedPacket(keyName));
        }

        if (KeyRegistry.LEVITATION.isDown())
        {
            var keyName = KeyRegistry.LEVITATION.getName();
            trySendPacket(keyName, new KeyPressedPacket(keyName));
        }

        if (KeyRegistry.SWORD_EFFECT.isDown())
        {
            var keyName = KeyRegistry.SWORD_EFFECT.getName();
            trySendPacket(keyName, new ToolsModeChangePacket());
        }

        if (KeyRegistry.SUPER_JUMP.isDown())
        {
            var keyName = KeyRegistry.SUPER_JUMP.getName();
            trySendPacket(keyName, new KeyPressedPacket(keyName));
        }
    }

    private static synchronized void trySendPacket(String keyName, ClientToServerPacket packet)
    {
        long time2 = Clock.systemUTC().millis();
        var last = lastTime.get(keyName);

        if (last != null)
        {
            long time1 = last;
            if (time2 - time1 < 200) return;
        }

        lastTime.put(keyName, time2);
        packet.sendToServer();
    }
}
