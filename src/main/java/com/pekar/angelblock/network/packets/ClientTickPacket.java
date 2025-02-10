package com.pekar.angelblock.network.packets;

import com.pekar.angelblock.events.PlayerManager;
import com.pekar.angelblock.events.armor.IArmor;
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
        String playerName = serverPlayer.getName().getString();
        IPlayer player = PlayerManager.instance().getPlayerByEntityName(playerName);

        for (IArmor armor : player.getArmorTypesUsed())
        {
            armor.onCreeperCheck();

            if (player.getEntity().isInWater())
            {
                armor.onBeingInWater();
            }
            else if (player.getEntity().isInWaterOrRain())
            {
                armor.onBeingUnderRain();
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
