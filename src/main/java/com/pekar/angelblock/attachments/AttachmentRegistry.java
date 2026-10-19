package com.pekar.angelblock.attachments;

import com.pekar.angelblock.events.cleaners.TrackedAllayData;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

import static com.pekar.angelblock.Main.MODID;

public class AttachmentRegistry
{
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, MODID);

    public static final Supplier<AttachmentType<TrackedAllayData>> TRACKED_ALLAY =
            ATTACHMENT_TYPES.register("tracked_allay", () ->
                    AttachmentType.<TrackedAllayData>builder(() -> {
                                throw new IllegalStateException("TrackedAllayData has no default value");
                            })
                            .serialize(TrackedAllayData.CODEC)
                            .build()
            );

    public static void register(IEventBus eventBus)
    {
        ATTACHMENT_TYPES.register(eventBus);
    }
}
