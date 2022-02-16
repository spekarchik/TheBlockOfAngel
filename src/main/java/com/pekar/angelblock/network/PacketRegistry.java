package com.pekar.angelblock.network;

import com.pekar.angelblock.Main;
import com.pekar.angelblock.network.packets.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public final class PacketRegistry
{
    private static final String PROTOCOL_VERSION = "1";

    static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Main.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals);

    private PacketRegistry()
    {
    }

    public static void init()
    {
        // Register concrete packets
        registerPacket(new CreeperDetectedPacket());
        registerPacket(new ClientTickPacket());
        registerPacket(new KeyPressedPacket());
        registerPacket(new ToolsModeChangePacket());
        registerPacket(new PlaySoundPacket(SoundType.UNDEFINED));
    }

    private static <T extends Packet> void registerPacket(T packet)
    {
        var packetContainer = new PacketContainer<>(packet);
        INSTANCE.messageBuilder(packetContainer.getType(), packetContainer.getPacketId(), packetContainer.getDirection())
                .encoder(packetContainer.getEncoder()).decoder(packetContainer.getDecoder())
                .consumer(packetContainer.getPacketHandler()).add();
    }
}
