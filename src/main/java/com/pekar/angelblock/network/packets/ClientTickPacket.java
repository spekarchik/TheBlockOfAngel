package com.pekar.angelblock.network.packets;

import com.pekar.angelblock.events.PlayerManager;
import com.pekar.angelblock.events.armor.IPlayerArmor;
import com.pekar.angelblock.events.player.IPlayer;
import com.pekar.angelblock.network.ClientToServerPacket;
import com.pekar.angelblock.network.Packet;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public class ClientTickPacket extends ClientToServerPacket
{
    @Override
    public void onReceive(ServerPlayer serverPlayer)
    {
        IPlayer player = PlayerManager.instance().getPlayerByUUID(serverPlayer.getUUID());
        var playerEntity = player.getPlayerEntity();

        for (IPlayerArmor armor : player.getArmorTypesUsed())
        {
            armor.onCreeperCheck();

            if (playerEntity.isInWater())
            {
                armor.onBeingInWater();
            }
            else if (playerEntity.isInWaterOrRain())
            {
                armor.onBeingUnderRain();
            }
            else if (playerEntity.isInLava())
            {
                armor.onBeingInLava();
            }
        }
    }

    @Override
    public String getPacketId()
    {
        return Packets.ClientTickPacketId;
    }

    @Override
    public Packet decode(FriendlyByteBuf buffer)
    {
        return new ClientTickPacket();
    }
}
