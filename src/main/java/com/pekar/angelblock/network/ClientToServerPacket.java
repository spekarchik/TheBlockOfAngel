package com.pekar.angelblock.network;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

public abstract class ClientToServerPacket extends Packet
{
    protected ClientToServerPacket()
    {}

    public void sendToServer()
    {
        PacketRegistry.INSTANCE.send(this, PacketDistributor.SERVER.noArg());
    }

    @Override
    protected final NetworkDirection getDirection()
    {
        return NetworkDirection.PLAY_TO_SERVER;
    }

    @Override
    protected final void onReceive(@NotNull CustomPayloadEvent.Context context)
    {
        onReceive(context.getSender());
    }

    protected abstract void onReceive(ServerPlayer serverPlayer);
}
