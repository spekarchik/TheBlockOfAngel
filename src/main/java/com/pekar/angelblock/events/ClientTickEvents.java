package com.pekar.angelblock.events;

import com.pekar.angelblock.Main;
import com.pekar.angelblock.network.packets.ClientTickPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@EventBusSubscriber(modid = Main.MODID, value = Dist.CLIENT)
public class ClientTickEvents
{
    private static final Map<UUID, Integer> tickCounter = new ConcurrentHashMap<>();

    public static void initStatic()
    {
        // do nothing
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event)
    {
        LocalPlayer localPlayer = Minecraft.getInstance().player;
        if (localPlayer == null) return;

        var playerId = localPlayer.getUUID();
        if (!tickCounter.containsKey(playerId))
        {
            tickCounter.put(playerId, 0);
        }
        else if (tickCounter.get(playerId) > 10)
        {
            var player = PlayerManager.instance().getPlayerByUUID(playerId);
            if (player != null)
            {
                player.onClientTick();
            }

            new ClientTickPacket().sendToServer();
            tickCounter.put(playerId, 0);
        }
        else
        {
            tickCounter.put(playerId, tickCounter.get(playerId) + 1);
        }
    }
}
