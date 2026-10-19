package com.pekar.angelblock.events.cleaners;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.UUIDUtil;

import java.util.UUID;

public record TrackedAllayData(UUID allayUuid, UUID ownerUuid)
{
    public static final MapCodec<TrackedAllayData> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    UUIDUtil.CODEC.fieldOf("allay").forGetter(TrackedAllayData::allayUuid),
                    UUIDUtil.CODEC.fieldOf("owner").forGetter(TrackedAllayData::ownerUuid)
            ).apply(instance, TrackedAllayData::new)
    );
}
