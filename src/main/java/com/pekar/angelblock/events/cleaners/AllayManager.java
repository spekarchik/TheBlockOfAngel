package com.pekar.angelblock.events.cleaners;

import com.pekar.angelblock.network.packets.FindAllayPacketToClient;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public abstract class AllayManager
{
    public static void restoreSavedAllays(Player player)
    {
        new FindAllayPacketToClient().sendToPlayer((ServerPlayer) player);
    }

    public static void removeFromStorage(TrackedAllay allay)
    {
        TrackedAllaysData.get(allay.getTargetLevel()).remove(allay);
    }

    public static void store(TrackedAllay allay)
    {
        TrackedAllaysData.get(allay.getTargetLevel()).store(allay);
    }
}
