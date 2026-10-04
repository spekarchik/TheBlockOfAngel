package com.pekar.angelblock.client.events;

import com.pekar.angelblock.Main;
import com.pekar.angelblock.keybinds.KeyBindNames;
import com.pekar.angelblock.client.keybinds.KeyRegistry;
import com.pekar.angelblock.network.base.ClientToServerPacket;
import com.pekar.angelblock.network.packets.KeyPressedPacket;
import com.pekar.angelblock.network.packets.ToolsModeChangePacket;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;

import java.time.Clock;
import java.util.HashMap;
import java.util.Map;

@EventBusSubscriber(modid = Main.MODID, value = Dist.CLIENT)
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
            var keyName = KeyBindNames.JUMP_BOOST;
            trySendPacket(keyName, new KeyPressedPacket(keyName));
        }

        if (KeyRegistry.NIGHT_VISION.isDown())
        {
            var keyName = KeyBindNames.NIGHT_VISION;
            trySendPacket(keyName, new KeyPressedPacket(keyName));
        }

        if (KeyRegistry.GLOWING.isDown())
        {
            var keyName = KeyBindNames.GLOWING;
            trySendPacket(keyName, new KeyPressedPacket(keyName));
        }

        if (KeyRegistry.REGENERATION.isDown())
        {
            var keyName = KeyBindNames.REGENERATION;
            trySendPacket(keyName, new KeyPressedPacket(keyName));
        }

        if (KeyRegistry.SLOW_FALLING.isDown())
        {
            var keyName = KeyBindNames.SLOW_FALLING;
            trySendPacket(keyName, new KeyPressedPacket(keyName));
        }

        if (KeyRegistry.TOOL_MODE.isDown())
        {
            var keyName = KeyBindNames.TOOL_MODE;
            trySendPacket(keyName, new ToolsModeChangePacket());
        }

        if (KeyRegistry.SUPER_JUMP.isDown())
        {
            var keyName = KeyBindNames.SUPER_JUMP;
            trySendPacket(keyName, new KeyPressedPacket(keyName));
        }
    }

    private static synchronized void trySendPacket(String keyName, ClientToServerPacket packet)
    {
        if (keyName == null) return;

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
