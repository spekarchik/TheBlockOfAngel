package com.pekar.angelblock.events.cleaners;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.HashSet;
import java.util.Set;

public class TrackedAllaysData extends SavedData
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

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider provider)
    {
        ListTag list = new ListTag();

        for (TrackedAllayData data : saved)
        {
            list.add(data.save());
        }

        tag.put("Tracked", list);
        return tag;
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
        return level.getDataStorage().computeIfAbsent(TYPE, "tracked_allays");
    }

    public static Set<TrackedAllay> restoreAllays(ServerPlayer player, TrackedAllaysData data)
    {
        Set<TrackedAllay> result = new HashSet<>();
        var level = (ServerLevel) player.level();

        for (TrackedAllayData dto : data.getSaved())
        {
            if (!player.getUUID().equals(dto.ownerUuid())) continue;

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

    public static final SavedData.Factory<TrackedAllaysData> TYPE = new SavedData.Factory<>(
            TrackedAllaysData::new,
            (compound, provider) -> {
                ListTag list = compound.getList("Tracked", Tag.TAG_COMPOUND);
                HashSet<TrackedAllayData> loaded = new HashSet<>();

                for (Tag tag : list) {
                    if (tag instanceof CompoundTag entry) {
                        loaded.add(TrackedAllayData.load(entry));
                    }
                }

                return new TrackedAllaysData(loaded);
            },
            null
    );
}
