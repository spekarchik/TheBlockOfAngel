package com.pekar.angelblock.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Supplier;

public abstract class ServerToClientPacket extends Packet
{
    protected ServerToClientPacket()
    {}

    public ServerToClientPacket(FriendlyByteBuf buffer)
    {
        super(buffer);
    }

    public final void sendToClient(ServerPlayer player)
    {
        PacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), this);
    }

    @Override
    protected final NetworkDirection getDirection()
    {
        return NetworkDirection.PLAY_TO_CLIENT;
    }

    @Override
    protected final void onReceive(Supplier<NetworkEvent.Context> ctx)
    {
        onReceive();
    }

    protected abstract void onReceive();
}
