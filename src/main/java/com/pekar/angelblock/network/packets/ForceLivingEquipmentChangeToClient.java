package com.pekar.angelblock.network.packets;

import com.pekar.angelblock.network.base.IPacket;
import com.pekar.angelblock.network.base.ServerToClientPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public class ForceLivingEquipmentChangeToClient extends ServerToClientPacket
{
    @Override
    public void onReceive(Player player)
    {
        new ForceLivingEquipmentChangeToServer().sendToServer();
    }

    @Override
    public String getPacketId()
    {
        return Packets.ForceLivingEquipmentChangeToClientId;
    }

    @Override
    public IPacket decode(FriendlyByteBuf buffer)
    {
        return new ForceLivingEquipmentChangeToClient();
    }
}
