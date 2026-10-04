package com.pekar.angelblock.network.packets;

import com.pekar.angelblock.clientaccess.ClientAccessor;
import com.pekar.angelblock.network.base.Packet;
import com.pekar.angelblock.network.base.ServerToClientPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;

public class CreeperDetectedPacket extends ServerToClientPacket
{
    @Override
    public void onReceive(Player player)
    {
        var networkAccessor = ClientAccessor.networkAccessor();
        if (networkAccessor.isPauseScreen()) return;

        networkAccessor.playClientSound(SoundEvents.NOTE_BLOCK_BELL.value(), 1.0F, 15.0F);
    }

    @Override
    public String getPacketId()
    {
        return Packets.CreeperDetectedPacketId;
    }

    @Override
    public Packet decode(FriendlyByteBuf buffer)
    {
        return new CreeperDetectedPacket();
    }
}
