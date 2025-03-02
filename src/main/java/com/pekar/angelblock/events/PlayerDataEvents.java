package com.pekar.angelblock.events;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import com.pekar.angelblock.tools.IModTool;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class PlayerDataEvents implements IEventHandler
{
    private final IPlayerManager playerBasic = PlayerManager.instance();

    @SubscribeEvent
    public void onBreakSpeed(PlayerEvent.BreakSpeed event)
    {
        var entityPlayer = event.getEntity();
        var mainHandItem = entityPlayer.getMainHandItem();
        if (!mainHandItem.isEmpty() && mainHandItem.getItem() instanceof IModTool modTool)
        {
            if (modTool.hasExtraLowEfficiencyDurability(mainHandItem))
                event.setNewSpeed(event.getNewSpeed() * 0.2F);
            else if (modTool.hasLowEfficiencyDurability(mainHandItem))
                event.setNewSpeed(event.getNewSpeed() * 0.5F);
        }

        IPlayer player = playerBasic.getPlayerByUUID(event.getEntity().getUUID());
        if (player == null) return;

        for (IArmor armor : player.getArmorTypesUsed())
        {
            armor.onBreakSpeed(event);
        }
    }
}
