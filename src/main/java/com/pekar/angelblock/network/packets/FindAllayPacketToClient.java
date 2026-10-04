package com.pekar.angelblock.network.packets;

import com.pekar.angelblock.network.base.IPacket;
import com.pekar.angelblock.network.base.ServerToClientPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public class FindAllayPacketToClient extends ServerToClientPacket
{
    @Override
    public void onReceive(Player player)
    {
        new FindAllayPacketToServer().sendToServer();
    }

    @Override
    public String getPacketId()
    {
        return Packets.FindAllayPacketToClientId;
    }

    @Override
    public IPacket decode(FriendlyByteBuf buffer)
    {
        return new FindAllayPacketToClient();
    }
}
