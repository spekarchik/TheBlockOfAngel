package com.pekar.angelblock.network.packets;

import com.pekar.angelblock.network.IPacket;
import com.pekar.angelblock.network.ServerToClientPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class UpdateArmorDurabilityPacketToClient extends ServerToClientPacket
{
    @Override
    public void onReceive(IPayloadContext context)
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
