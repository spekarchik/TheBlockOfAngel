package com.pekar.angelblock.network;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.PacketDistributor;

public abstract class ServerToClientPacket extends Packet
{
    protected ServerToClientPacket()
    {}

    public final void sendToPlayer(ServerPlayer player)
    {
        PacketRegistry.INSTANCE.send(this, PacketDistributor.PLAYER.with(player));
    }

    public final void sendToEntity(Entity entity)
    {
        PacketRegistry.INSTANCE.send(this, PacketDistributor.TRACKING_ENTITY.with(entity));
    }

    public final void sendToChunk(LevelChunk chunk)
    {
        PacketRegistry.INSTANCE.send(this, PacketDistributor.TRACKING_CHUNK.with(chunk));
    }

    @Override
    protected final NetworkDirection getDirection()
    {
        return NetworkDirection.PLAY_TO_CLIENT;
    }

    @Override
    protected final void onReceive(CustomPayloadEvent.Context ctx)
    {
        onReceive();
    }

    protected abstract void onReceive();
}
