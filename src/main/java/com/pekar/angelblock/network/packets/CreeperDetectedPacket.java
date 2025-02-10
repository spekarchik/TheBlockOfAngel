package com.pekar.angelblock.network.packets;

import com.pekar.angelblock.network.Packet;
import com.pekar.angelblock.network.ServerToClientPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;

public class CreeperDetectedPacket extends ServerToClientPacket
{
    @Override
    public void onReceive()
    {
        Minecraft.getInstance().player.playSound(SoundEvents.NOTE_BLOCK_BELL.value(), 1.0F, 5.0F);
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
