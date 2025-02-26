package com.pekar.angelblock.events;

import com.pekar.angelblock.Main;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;


public class ModDataComponents
{
//    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> ARMOR_UUID =
//            Main.DATA_COMPONENTS.register("armor_uuid", () -> DataComponentType.<String>builder().persistent(Codec.string(1, 128)).build());

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ResourceLocation>> POTION_ID =
            Main.DATA_COMPONENTS.register(Main.MODID + "block_breaker_potion_id", () -> DataComponentType.<ResourceLocation>builder().persistent(ResourceLocation.CODEC).build());

    public static void register(IEventBus modEventBus)
    {
        //NeoForge.EVENT_BUS.register(ModDataComponents.class);
        //DATA_COMPONENTS.register(BuiltInRegistries.DATA_COMPONENT_TYPE, ARMOR_UUID_ID, ARMOR_UUID);
        Main.DATA_COMPONENTS.register(modEventBus);
    }
}
