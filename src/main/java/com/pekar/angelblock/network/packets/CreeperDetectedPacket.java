package com.pekar.angelblock.network.packets;

import com.pekar.angelblock.network.Packet;
import com.pekar.angelblock.network.ServerToClientPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;

public class CreeperDetectedPacket extends ServerToClientPacket
{
    public CreeperDetectedPacket()
    {
    }

    protected CreeperDetectedPacket(FriendlyByteBuf buffer)
    {
        super(buffer);
    }

    @Override
    protected void onReceive()
    {
        Minecraft.getInstance().player.playSound(SoundEvents.NOTE_BLOCK_BELL, 1.0F, 5.0F);
    }

    @Override
    protected int getPacketId()
    {
        return Packets.CreeperDetectedPacketId;
    }

    @Override
    protected Packet create(FriendlyByteBuf buffer)
    {
        return new CreeperDetectedPacket(buffer);
    }
}
