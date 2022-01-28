package com.pekar.angelblock.events;

import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public interface IPlayerManager
{
    IPlayer getPlayerByEntityName(String entityName);
    IPlayer getPlayerByUUID(UUID uuid);
    void addEntityPlayer(Player entity);
    void sendMessage(String message);
}
