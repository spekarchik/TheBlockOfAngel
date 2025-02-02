package com.pekar.angelblock.network;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

public abstract class ClientToServerPacket extends Packet implements IClientToServerPacket
{
    protected ClientToServerPacket()
    {}

    public final void sendToServer()
    {
        PacketRegistry.INSTANCE.send(this, PacketDistributor.SERVER.noArg());
    }

    @Override
    public final boolean isServerToClient()
    {
        return false;
    }

    @Override
    protected final <TCtx> void onReceive(@NotNull ContextContainer<TCtx> contextContainer)
    {
        onReceive(contextContainer.getContext().getSender());
    }
}
