package com.pekar.angelblock.network.base;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;

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
    protected final void onReceive(Player player)
    {
        onReceive((ServerPlayer) player);
    }
}
