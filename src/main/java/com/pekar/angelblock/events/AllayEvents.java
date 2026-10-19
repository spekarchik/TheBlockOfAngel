package com.pekar.angelblock.events;

import com.pekar.angelblock.attachments.AttachmentRegistry;
import com.pekar.angelblock.events.cleaners.AllayManager;
import net.minecraft.world.entity.animal.allay.Allay;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

public class AllayEvents implements IEventHandler
{

    @SubscribeEvent
    public void onEntityJoinLevelEvent(EntityJoinLevelEvent event)
    {
        if (event.getLevel().isClientSide()) return;
        if (!(event.getEntity() instanceof Allay allay)) return;
        if (!allay.hasData(AttachmentRegistry.TRACKED_ALLAY)) return;

        AllayManager.track(allay);
    }

    @SubscribeEvent
    public void onPlayerInteractEvent(PlayerInteractEvent.EntityInteract event)
    {
        if (event.getSide() == LogicalSide.CLIENT) return;
        if (!(event.getTarget() instanceof Allay allay)) return;
        if (!allay.hasData(AttachmentRegistry.TRACKED_ALLAY)) return;

        AllayManager.track(allay);
    }
}
