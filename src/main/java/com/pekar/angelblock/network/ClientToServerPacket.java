package com.pekar.angelblock.network;

import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.ServerPayloadContext;

public abstract class ClientToServerPacket extends Packet implements IClientToServerPacket
{
    protected ClientToServerPacket()
    {}

    public final void sendToServer()
    {
        PacketDistributor.sendToServer(this);
    }

    @Override
    public final boolean isServerToClient()
    {
        return false;
    }

    @Override
    protected final void onReceive(IPayloadContext context)
    {
        var serverContext = (ServerPayloadContext)context;
        onReceive(serverContext.player());
    }
}
