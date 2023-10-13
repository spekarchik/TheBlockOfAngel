package com.pekar.angelblock.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.network.NetworkDirection;
import org.jetbrains.annotations.NotNull;

public abstract class Packet
{
    protected Packet()
    {
    }

    protected void encode(FriendlyByteBuf buffer)
    {
    }

    final void handlePacket(@NotNull CustomPayloadEvent.Context context)
    {
        context.enqueueWork(() -> onReceive(context));
        context.setPacketHandled(true);
    }

    protected abstract void onReceive(CustomPayloadEvent.Context ctx);

    protected abstract int getPacketId();

    protected abstract NetworkDirection getDirection();

    protected abstract Packet create(FriendlyByteBuf buffer);
}
