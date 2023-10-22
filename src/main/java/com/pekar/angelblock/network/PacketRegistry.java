package com.pekar.angelblock.network;

import com.pekar.angelblock.Main;
import com.pekar.angelblock.network.packets.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.SimpleChannel;

public final class PacketRegistry
{
    static final SimpleChannel INSTANCE = ChannelBuilder.named(new ResourceLocation(Main.MODID, "main"))
            .simpleChannel();

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
        registerPacket(new HoldingAngelRodPacket());
    }

    private static <T extends Packet> void registerPacket(T packet)
    {
        var packetContainer = new PacketContainer<>(packet);
        INSTANCE.messageBuilder(packetContainer.getType(), packetContainer.getPacketId(), packetContainer.getDirection())
                .encoder(packetContainer.getEncoder()).decoder(packetContainer.getDecoder())
                .consumerNetworkThread(packetContainer.getPacketHandler()).add();
    }
}
