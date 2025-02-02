package com.pekar.angelblock.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.network.NetworkDirection;
import org.jetbrains.annotations.NotNull;

public abstract class Packet implements IPacket
{
    protected Packet()
    {
    }

    final <TCtx> void handlePacket(@NotNull ContextContainer<TCtx> contextContainer)
    {
        var context = contextContainer.getContext();
        context.enqueueWork(() -> onReceive(contextContainer));
        context.setPacketHandled(true);
    }

    protected abstract boolean isServerToClient();

    protected abstract <TCtx> void onReceive(ContextContainer<TCtx> contextContainer);
}
