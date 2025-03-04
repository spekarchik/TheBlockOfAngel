package com.pekar.angelblock.network.packets;

import com.pekar.angelblock.network.ClientToServerPacket;
import com.pekar.angelblock.network.IPacket;
import com.pekar.angelblock.utils.Utils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public class UpdateArmorDurabilityPacketToServer extends ClientToServerPacket
{
    @Override
    public void onReceive(ServerPlayer serverPlayer)
    {
        Utils.instance.attributeModifiers.updateArmorAttributeModifier(serverPlayer);
    }

    @Override
    public String getPacketId()
    {
        return Packets.UpdateArmorDurabilityPacketToServerId;
    }

    @Override
    public IPacket decode(FriendlyByteBuf buffer)
    {
        return new UpdateArmorDurabilityPacketToServer();
    }
}
