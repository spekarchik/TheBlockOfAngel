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

public class TrackedAllaysData extends SavedData
{
    private final HashSet<TrackedAllayData> tracked;

    public TrackedAllaysData(HashSet<TrackedAllayData> tracked)
    {
        this.tracked = tracked;
    }

    public TrackedAllaysData()
    {
        this.tracked = new HashSet<>();
    }

    public void track(TrackedAllay allay)
    {
        tracked.add(new TrackedAllayData(allay.getTargetInstance().getUUID(), allay.getOwner().getUUID()));
        setDirty();
    }

    public void untrack(TrackedAllay allay)
    {
        var allayData = new TrackedAllayData(allay.getTargetInstance().getUUID(), allay.getOwner().getUUID());

        if (!tracked.contains(allayData)) return;

        tracked.remove(allayData);
        setDirty();
    }

    public Set<TrackedAllayData> getTracked()
    {
        return tracked;
    }

    public static TrackedAllaysData get(ServerLevel level)
    {
        return level.getDataStorage().computeIfAbsent(TYPE);
    }

    public static Set<TrackedAllay> restoreAllays(ServerLevel level, TrackedAllaysData data)
    {
        Set<TrackedAllay> result = new HashSet<>();

        for (TrackedAllayData dto : data.getTracked())
        {
            var owner = level.getPlayerByUUID(dto.ownerUuid());
            if (owner == null) continue;

            var entity = level.getEntity(dto.allayUuid());
            if (!(entity instanceof Allay allay)) continue;

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
                            .forGetter(data -> data.tracked)
            ).apply(instance, TrackedAllaysData::new)
    );

    public static final SavedDataType<TrackedAllaysData> TYPE = new SavedDataType<>(
            "tracked_allays",
            ctx -> new TrackedAllaysData(),
            ctx -> CODEC
    );

}
