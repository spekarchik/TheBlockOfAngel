package com.pekar.angelblock.events;

import com.pekar.angelblock.keybinds.KeyRegistry;
import com.pekar.angelblock.network.packets.KeyPressedPacket;
import com.pekar.angelblock.network.packets.ToolsModeChangePacket;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class KeyboardMouseEvents implements IEventHandler
{
    @SubscribeEvent
    public void onKeyInputEvent(InputEvent.KeyInputEvent event)
    {
        if (KeyRegistry.JUMP_BOOST.isDown())
        {
            new KeyPressedPacket(KeyRegistry.JUMP_BOOST.getName()).sendToServer();
        }

        if (KeyRegistry.NIGHT_VISION.isDown())
        {
            new KeyPressedPacket(KeyRegistry.NIGHT_VISION.getName()).sendToServer();
        }

        if (KeyRegistry.GLOWING.isDown())
        {
            new KeyPressedPacket(KeyRegistry.GLOWING.getName()).sendToServer();
        }

        if (KeyRegistry.REGENERATION.isDown())
        {
            new KeyPressedPacket(KeyRegistry.REGENERATION.getName()).sendToServer();
        }

        if (KeyRegistry.LEVITATION.isDown())
        {
            new KeyPressedPacket(KeyRegistry.LEVITATION.getName()).sendToServer();
        }

        if (KeyRegistry.SWORD_EFFECT.isDown())
        {
            new ToolsModeChangePacket().sendToServer();
        }
    }
}
