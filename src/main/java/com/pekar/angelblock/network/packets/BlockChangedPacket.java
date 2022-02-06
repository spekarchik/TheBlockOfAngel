package com.pekar.angelblock.network.packets;

import com.pekar.angelblock.network.Packet;
import com.pekar.angelblock.network.ServerToClientPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;

public class BlockChangedPacket extends ServerToClientPacket
{
    @Override
    protected int getPacketId()
    {
        return Packets.RodChangedBlockPacketId;
    }

    @Override
    protected Packet create(FriendlyByteBuf buffer)
    {
        return new BlockChangedPacket();
    }

    @Override
    protected void onReceive()
    {
        Minecraft.getInstance().player.playSound(SoundEvents.STONE_BREAK, 1.0F, 5.0F);
    }
}
