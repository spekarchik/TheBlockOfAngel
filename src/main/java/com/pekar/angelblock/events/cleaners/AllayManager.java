package com.pekar.angelblock.events.cleaners;

import com.pekar.angelblock.attachments.AttachmentRegistry;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.player.Player;

public class AllayManager
{
    public static void setTrackingData(Allay allay, Player player)
    {
        allay.setData(AttachmentRegistry.TRACKED_ALLAY, new TrackedAllayData(allay.getUUID(), player.getUUID()));
    }

    public static void track(Allay allay)
    {
        var data = allay.getData(AttachmentRegistry.TRACKED_ALLAY);
        var trackedAllayData = new TrackedAllay(allay, data.ownerUuid());
        Cleaner.remove(trackedAllayData);
        Cleaner.add(trackedAllayData);
    }
}
