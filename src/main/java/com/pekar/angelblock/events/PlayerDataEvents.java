package com.pekar.angelblock.events;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PlayerDataEvents implements IEventHandler
{
    private final IPlayerManager playerBasic = PlayerManager.instance();

    @SubscribeEvent
    public void onBreakSpeed(PlayerEvent.BreakSpeed event)
    {
        IPlayer player = playerBasic.getPlayerByEntityName(event.getEntityLiving().getName().getContents());
        if (player == null) return;

        for (IArmor armor : player.getArmorTypesUsed())
        {
            armor.onBreakSpeed(event);
        }
    }
}
