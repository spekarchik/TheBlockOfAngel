package com.pekar.angelblock.events;

import com.pekar.angelblock.keybinds.KeyRegistry;
import com.pekar.angelblock.network.ClientToServerPacket;
import com.pekar.angelblock.network.packets.KeyPressedPacket;
import com.pekar.angelblock.network.packets.ToolsModeChangePacket;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.time.Clock;
import java.util.HashMap;
import java.util.Map;

public class KeyboardMouseEvents implements IEventHandler
{
    private final Map<String, Long> lastTime = new HashMap<>();

    @SubscribeEvent
    public void onRegisterKeyMappingsEvent(RegisterKeyMappingsEvent event)
    {
        event.register(KeyRegistry.NIGHT_VISION);
        event.register(KeyRegistry.JUMP_BOOST);
        event.register(KeyRegistry.SUPER_JUMP);
        event.register(KeyRegistry.GLOWING);
        event.register(KeyRegistry.REGENERATION);
        event.register(KeyRegistry.LEVITATION);
        event.register(KeyRegistry.SWORD_EFFECT);
    }

    @SubscribeEvent
    public void onKeyInputEvent(InputEvent.Key event)
    {
        if (event.getKey() == KeyRegistry.JUMP_BOOST.getKey().getValue())
        {
            var keyName = KeyRegistry.JUMP_BOOST.getName();
            trySendPacket(keyName, new KeyPressedPacket(keyName));
        }

        if (event.getKey() == KeyRegistry.NIGHT_VISION.getKey().getValue())
        {
            var keyName = KeyRegistry.NIGHT_VISION.getName();
            trySendPacket(keyName, new KeyPressedPacket(keyName));
        }

        if (event.getKey() == KeyRegistry.GLOWING.getKey().getValue())
        {
            var keyName = KeyRegistry.GLOWING.getName();
            trySendPacket(keyName, new KeyPressedPacket(keyName));
        }

        if (event.getKey() == KeyRegistry.REGENERATION.getKey().getValue())
        {
            var keyName = KeyRegistry.REGENERATION.getName();
            trySendPacket(keyName, new KeyPressedPacket(keyName));
        }

        if (event.getKey() == KeyRegistry.LEVITATION.getKey().getValue())
        {
            var keyName = KeyRegistry.LEVITATION.getName();
            trySendPacket(keyName, new KeyPressedPacket(keyName));
        }

        if (event.getKey() == KeyRegistry.SWORD_EFFECT.getKey().getValue())
        {
            var keyName = KeyRegistry.SWORD_EFFECT.getName();
            trySendPacket(keyName, new ToolsModeChangePacket());
        }

        if (event.getKey() == KeyRegistry.SUPER_JUMP.getKey().getValue())
        {
            var keyName = KeyRegistry.SUPER_JUMP.getName();
            trySendPacket(keyName, new KeyPressedPacket(keyName));
        }
    }

    private synchronized void trySendPacket(String keyName, ClientToServerPacket packet)
    {
        long time2 = Clock.systemUTC().millis();
        var last = lastTime.get(keyName);

        if (last != null)
        {
            long time1 = last;
            if (time2 - time1 < 100) return;
        }

        lastTime.put(keyName, time2);
        packet.sendToServer();
    }
}
