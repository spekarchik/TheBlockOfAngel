package com.pekar.angelblock.events;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class PlayerDataEvents implements IEventHandler
{
    private final IPlayerManager playerBasic = PlayerManager.instance();

    @SubscribeEvent
    public void onBreakSpeed(PlayerEvent.BreakSpeed event)
    {
        IPlayer player = playerBasic.getPlayerByEntityName(event.getEntity().getName().getString());
        if (player == null) return;

        for (IArmor armor : player.getArmorTypesUsed())
        {
            armor.onBreakSpeed(event);
        }
    }
}
