package com.pekar.angelblock.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public abstract class Packet
{
    protected Packet()
    {
    }

    protected Packet(FriendlyByteBuf buffer)
    {
    }

    protected void encode(FriendlyByteBuf buffer)
    {
    }

    final void handlePacket(@NotNull Supplier<NetworkEvent.Context> ctx)
    {
        var context = ctx.get();
        context.enqueueWork(() -> onReceive(ctx));
        context.setPacketHandled(true);
    }

    protected abstract void onReceive(Supplier<NetworkEvent.Context> ctx);

    protected abstract int getPacketId();

    protected abstract NetworkDirection getDirection();

    protected abstract Packet create(FriendlyByteBuf buffer);
}
