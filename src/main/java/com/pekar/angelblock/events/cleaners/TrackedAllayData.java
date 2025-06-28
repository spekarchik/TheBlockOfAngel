package com.pekar.angelblock.events.cleaners;

import net.minecraft.nbt.CompoundTag;

import java.util.UUID;

public record TrackedAllayData(UUID allayUuid, UUID ownerUuid)
{
    public CompoundTag save()
    {
        CompoundTag tag = new CompoundTag();
        tag.putUUID("Allay", allayUuid);
        tag.putUUID("Owner", ownerUuid);
        return tag;
    }

    public static TrackedAllayData load(CompoundTag tag)
    {
        return new TrackedAllayData(tag.getUUID("Allay"), tag.getUUID("Owner"));
    }
}
