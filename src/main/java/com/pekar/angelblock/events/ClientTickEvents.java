package com.pekar.angelblock.events;

import com.pekar.angelblock.network.packets.ClientTickPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClientTickEvents
{
    private static final Map<String, Integer> tickCounter = new ConcurrentHashMap<>();

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event)
    {
        if (event.phase != TickEvent.Phase.END) return;
        if (event.side != LogicalSide.CLIENT) return;
        LocalPlayer localPlayer = Minecraft.getInstance().player;
//        if (localPlayer == null) return;

        String playerName = localPlayer.getName().getContents();
        if (!tickCounter.containsKey(playerName))
        {
            tickCounter.put(playerName, 0);
        }
        else if (tickCounter.get(playerName) > 10)
        {
            new ClientTickPacket().sendToServer();
            tickCounter.put(playerName, 0);
        }
        else
        {
            tickCounter.put(playerName, tickCounter.get(playerName) + 1);
        }
    }
}
