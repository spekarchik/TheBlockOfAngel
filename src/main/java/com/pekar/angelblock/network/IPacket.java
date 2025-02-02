package com.pekar.angelblock.network;

import net.minecraft.network.FriendlyByteBuf;

public interface IPacket
{
    int getPacketId();
    default void encode(FriendlyByteBuf buffer) {}
    IPacket decode(FriendlyByteBuf buffer);
}
