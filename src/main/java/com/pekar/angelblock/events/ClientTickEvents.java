package com.pekar.angelblock.events;

import com.pekar.angelblock.Main;
import com.pekar.angelblock.network.packets.ClientTickPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Mod.EventBusSubscriber(modid = Main.MODID, value = Dist.CLIENT)
public class ClientTickEvents
{
    private static final Map<String, Integer> tickCounter = new ConcurrentHashMap<>();

    public static void initStatic()
    {
        // do nothing
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event)
    {
        if (event.phase != TickEvent.Phase.END) return;

        LocalPlayer localPlayer = Minecraft.getInstance().player;
        if (localPlayer == null) return;

        String playerName = localPlayer.getName().getString();
        if (!tickCounter.containsKey(playerName))
        {
            tickCounter.put(playerName, 0);
        }
        else if (tickCounter.get(playerName) > 10)
        {
            var player = PlayerManager.instance().getPlayerByEntityName(playerName);
            if (player != null)
            {
                player.onClientTick();
            }

            new ClientTickPacket().sendToServer();
            tickCounter.put(playerName, 0);
        }
        else
        {
            tickCounter.put(playerName, tickCounter.get(playerName) + 1);
        }
    }
}
