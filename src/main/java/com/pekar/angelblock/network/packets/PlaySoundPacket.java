package com.pekar.angelblock.network.packets;

import com.pekar.angelblock.network.Packet;
import com.pekar.angelblock.network.ServerToClientPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

public class PlaySoundPacket extends ServerToClientPacket
{
    private final SoundType soundType;
    private final SoundEvent soundEvent;

    public PlaySoundPacket(SoundType soundType)
    {
        this(soundType, SoundEvents.NOTE_BLOCK_XYLOPHONE.value());
    }

    public PlaySoundPacket(SoundEvent soundEvent)
    {
        this(SoundType.MINECRAFT, soundEvent);
    }

    private PlaySoundPacket(SoundType soundType, SoundEvent soundEvent)
    {
        this.soundType = soundType;
        this.soundEvent = soundEvent;
    }

    @Override
    protected int getPacketId()
    {
        return Packets.PlaySoundPacketId;
    }

    @Override
    protected Packet create(FriendlyByteBuf buffer)
    {
        var soundType = SoundType.getByIndex(buffer.readInt());
        var soundEvent = SoundEvent.createVariableRangeEvent(buffer.readResourceLocation());
        return new PlaySoundPacket(soundType, soundEvent);
    }

    @Override
    protected void encode(FriendlyByteBuf buffer)
    {
        buffer.writeInt(SoundType.getIndex(soundType));
        buffer.writeResourceLocation(soundEvent.getLocation());
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
            case AMETHYST_FOUND -> SoundEvents.NOTE_BLOCK_XYLOPHONE.value();
            case DIAMOND_FOUND -> SoundEvents.NOTE_BLOCK_IRON_XYLOPHONE.value();
            case ORE_FOUND -> SoundEvents.GRAVEL_PLACE;
            case SCULK_FOUND -> SoundEvents.SCULK_SHRIEKER_SHRIEK;
            case INFESTED_BLOCK -> SoundEvents.SILVERFISH_DEATH;
            case BONEMEAL -> SoundEvents.BONE_MEAL_USE;
            case RAIL_PLACED -> SoundEvents.METAL_PLACE;
            case REDSTONE_WIRE_PLACED, STONE_PLACED -> SoundEvents.STONE_PLACE;
            case WOOD_PLACED -> SoundEvents.WOOD_PLACE;
            case MINECRAFT -> soundEvent;
            case UNDEFINED -> null;
        };
    }
}
