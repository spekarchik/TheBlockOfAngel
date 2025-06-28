package com.pekar.angelblock.events.cleaners;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

class TrackedAllaysData extends SavedData
{
    private final HashSet<TrackedAllayData> saved;

    public TrackedAllaysData(HashSet<TrackedAllayData> saved)
    {
        this.saved = saved;
    }

    public TrackedAllaysData()
    {
        this.saved = new HashSet<>();
    }

    public void store(TrackedAllay allay)
    {
        saved.add(new TrackedAllayData(allay.getTargetInstance().getUUID(), allay.getOwner().getUUID()));
        setDirty();
    }

    public void remove(TrackedAllay allay)
    {
        var allayData = new TrackedAllayData(allay.getTargetInstance().getUUID(), allay.getOwner().getUUID());

        remove(allayData);
    }

    private void remove(TrackedAllayData data)
    {
        if (!saved.contains(data)) return;

        saved.remove(data);
        setDirty();
    }

    public Set<TrackedAllayData> getSaved()
    {
        return saved;
    }

    public static TrackedAllaysData get(ServerLevel level)
    {
        return level.getDataStorage().computeIfAbsent(TYPE);
    }

    public static Set<TrackedAllay> restoreAllays(ServerLevel level, TrackedAllaysData data)
    {
        Set<TrackedAllay> result = new HashSet<>();

        for (TrackedAllayData dto : data.getSaved())
        {
            var owner = level.getPlayerByUUID(dto.ownerUuid());
            if (owner == null) continue;

            var entity = level.getEntity(dto.allayUuid());
            if (!(entity instanceof Allay allay))
            {
                get(level).remove(dto);
                continue;
            }

            var tracked = new TrackedAllay(allay, owner);
            result.add(tracked);
        }

        return result;
    }

    public static final Codec<TrackedAllaysData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.list(TrackedAllayDataCodec.CODEC)
                            .xmap(HashSet::new, ArrayList::new)
                            .fieldOf("Tracked")
                            .forGetter(data -> data.saved)
            ).apply(instance, TrackedAllaysData::new)
    );

    public static final SavedDataType<TrackedAllaysData> TYPE = new SavedDataType<>(
            "tracked_allays",
            ctx -> new TrackedAllaysData(),
            ctx -> CODEC
    );
}
