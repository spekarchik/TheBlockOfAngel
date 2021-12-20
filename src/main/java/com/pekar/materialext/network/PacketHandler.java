package com.pekar.materialext.network;

import com.pekar.materialext.MaterialExt;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public final class PacketHandler
{
    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(MaterialExt.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals);

    private PacketHandler()
    {

    }

    public static void init()
    {
        int index = 0;
        // Register concrete packets
        //INSTANCE.messageBuilder()
    }
}
