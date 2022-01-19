package com.pekar.angelblock.events;

import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.entity.player.Player;

public interface IPlayerManager
{
    IPlayer getPlayerByEntityName(String entityName);
    void addEntityPlayer(Player entity);
    void sendMessage(String message);
}
