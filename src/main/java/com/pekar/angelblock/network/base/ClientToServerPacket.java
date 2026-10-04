package com.pekar.angelblock.network.base;

import com.pekar.angelblock.clientaccess.ClientAccessor;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public abstract class ClientToServerPacket extends Packet implements IClientToServerPacket
{
    protected ClientToServerPacket()
    {}

    public final void sendToServer()
    {
        ClientAccessor.networkAccessor().sendToServer(this);
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
