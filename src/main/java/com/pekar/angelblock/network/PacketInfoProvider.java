package com.pekar.angelblock.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.network.NetworkDirection;

import java.util.function.BiConsumer;
import java.util.function.Function;

class PacketInfoProvider<T extends Packet>
{
    private final T packet;

    PacketInfoProvider(T packet)
    {
        this.packet = packet;
    }

    public Class<T> getType()
    {
        return (Class<T>)packet.getClass();
    }

    public int getPacketId()
    {
        return packet.getPacketId();
    }

    public NetworkDirection getDirection()
    {
        return packet.isServerToClient() ? NetworkDirection.PLAY_TO_CLIENT : NetworkDirection.PLAY_TO_SERVER;
    }

    public BiConsumer<T, FriendlyByteBuf> getEncoder()
    {
        return Packet::encode;
    }

    public Function<FriendlyByteBuf, T> getDecoder()
    {
        return buffer -> (T)packet.decode(buffer);
    }

    public BiConsumer<T, CustomPayloadEvent.Context> getPacketHandler()
    {
        return (packet, ctx) -> packet.handlePacket(new ContextContainer<>(ctx));
    }
}
