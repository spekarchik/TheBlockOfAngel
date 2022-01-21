package com.pekar.angelblock.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

class PacketContainer<T extends Packet>
{
    private final T packet;

    PacketContainer(T packet)
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
        return packet.getDirection();
    }

    public BiConsumer<T, FriendlyByteBuf> getEncoder()
    {
        return Packet::encode;
    }

    public Function<FriendlyByteBuf, T> getDecoder()
    {
        return buffer -> (T)packet.create(buffer);
    }

    public BiConsumer<T, Supplier<NetworkEvent.Context>> getPacketHandler()
    {
        return Packet::handlePacket;
    }
}
