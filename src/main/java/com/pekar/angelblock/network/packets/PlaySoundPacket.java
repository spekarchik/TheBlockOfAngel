package com.pekar.angelblock.network.packets;

import com.pekar.angelblock.network.Packet;
import com.pekar.angelblock.network.ServerToClientPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

public class PlaySoundPacket extends ServerToClientPacket
{
    private SoundType soundType;

    public PlaySoundPacket(SoundType soundType)
    {
        this.soundType = soundType;
    }

    @Override
    protected int getPacketId()
    {
        return Packets.PlaySoundPacketId;
    }

    @Override
    protected Packet create(FriendlyByteBuf buffer)
    {
        return new PlaySoundPacket(SoundType.getByIndex(buffer.readInt()));
    }

    @Override
    protected void encode(FriendlyByteBuf buffer)
    {
        buffer.writeInt(SoundType.getIndex(soundType));
    }

    @Override
    protected void onReceive()
    {
        var sound = getSound(soundType);
        if (sound == null) return;

        Minecraft.getInstance().player.playSound(sound, 1.0F, 5.0F);
    }

    private SoundEvent getSound(SoundType soundType)
    {
        return switch (soundType)
        {
            case PLANT -> SoundEvents.CROP_PLANTED;
            case BLOCK_CHANGED -> SoundEvents.STONE_BREAK;
            case WATER_PLACED -> SoundEvents.PLAYER_SPLASH_HIGH_SPEED;
            case LAVA_PLACED -> SoundEvents.LAVA_POP;
            case STEAM -> SoundEvents.LAVA_EXTINGUISH;
            case AMETHYST_FOUND -> SoundEvents.NOTE_BLOCK_XYLOPHONE;
            case DIAMOND_FOUND -> SoundEvents.NOTE_BLOCK_IRON_XYLOPHONE;
            case ORE_FOUND -> SoundEvents.GRAVEL_PLACE;
            case SCULK_FOUND -> SoundEvents.SCULK_SHRIEKER_SHRIEK;
            case UNDEFINED -> null;
        };
    }
}
