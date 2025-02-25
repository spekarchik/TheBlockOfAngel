package com.pekar.angelblock.events;

import com.mojang.serialization.Codec;
import com.pekar.angelblock.Main;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;


public class ModDataComponents
{
    private static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS = DeferredRegister.create(BuiltInRegistries.DATA_COMPONENT_TYPE, Main.MODID);

    //public static final ResourceLocation ARMOR_UUID_ID = ResourceLocation.fromNamespaceAndPath(Main.MODID, "armor_uuid");

//    public static final DataComponentType<String> ARMOR_UUID = DataComponentType.<String>builder()
//            .persistent(Codec.string(1, 128)) // Храним в виде строки (UUID -> String)
//            .networkSynchronized(ByteBufCodecs.STRING_UTF8) // Синхронизация с клиентом
//            .build();
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> ARMOR_UUID =
            DATA_COMPONENTS.register("armor_uuid", () -> DataComponentType.<String>builder().persistent(Codec.string(1, 128)).build());

    public static void register(IEventBus modEventBus)
    {
        //NeoForge.EVENT_BUS.register(ModDataComponents.class);
        //DATA_COMPONENTS.register(BuiltInRegistries.DATA_COMPONENT_TYPE, ARMOR_UUID_ID, ARMOR_UUID);
        DATA_COMPONENTS.register(modEventBus);
    }
}
