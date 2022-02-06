package com.pekar.angelblock.network.packets;

import com.pekar.angelblock.network.Packet;
import com.pekar.angelblock.network.ServerToClientPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;

public class OreDetectedPacket extends ServerToClientPacket
{
    private boolean isDiamondOre;

    public OreDetectedPacket()
    {
    }

    public OreDetectedPacket(boolean isDiamondOre)
    {
        this.isDiamondOre = isDiamondOre;
    }

    @Override
    protected int getPacketId()
    {
        return Packets.OreDetectedPacketId;
    }

    @Override
    protected Packet create(FriendlyByteBuf buffer)
    {
        return new OreDetectedPacket(buffer.readBoolean());
    }

    @Override
    protected void encode(FriendlyByteBuf buffer)
    {
        buffer.writeBoolean(isDiamondOre);
    }

    @Override
    protected void onReceive()
    {
        var sound = isDiamondOre ? SoundEvents.NOTE_BLOCK_IRON_XYLOPHONE : SoundEvents.GRAVEL_PLACE;
        Minecraft.getInstance().player.playSound(sound, 1.0F, 5.0F);
    }
}
