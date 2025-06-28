package com.pekar.angelblock.events.cleaners;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.UUIDUtil;

public class TrackedAllayDataCodec
{
    public static final Codec<TrackedAllayData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    UUIDUtil.CODEC.fieldOf("allay").forGetter(TrackedAllayData::allayUuid),
                    UUIDUtil.CODEC.fieldOf("owner").forGetter(TrackedAllayData::ownerUuid)
            ).apply(instance, TrackedAllayData::new)
    );
}
