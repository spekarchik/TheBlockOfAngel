package com.pekar.angelblock.network.packets;

import com.pekar.angelblock.network.IPacket;
import com.pekar.angelblock.network.ServerToClientPacket;
import net.minecraft.network.FriendlyByteBuf;

public class UpdateArmorDurabilityPacketToClient extends ServerToClientPacket
{
    @Override
    public void onReceive()
    {
        new UpdateArmorDurabilityPacketToServer().sendToServer();
    }

    @Override
    public String getPacketId()
    {
        return Packets.UpdateArmorDurabilityPacketToClientId;
    }

    @Override
    public IPacket decode(FriendlyByteBuf buffer)
    {
        return new UpdateArmorDurabilityPacketToClient();
    }
}
