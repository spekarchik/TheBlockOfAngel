package com.pekar.angelblock.network.packets;

import com.pekar.angelblock.network.Packet;
import com.pekar.angelblock.network.ServerToClientPacket;
import com.pekar.angelblock.utils.SoundType;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class PlaySoundPacket extends ServerToClientPacket
{
    private final SoundEvent soundEvent;
    private final float pitch;

    public PlaySoundPacket()
    {
        this(null, 1.0F);
    }

    public PlaySoundPacket(SoundEvent soundEvent)
    {
        this(soundEvent, 1.0F);
    }

    public PlaySoundPacket(SoundEvent soundEvent, float pitch)
    {
        this.soundEvent = soundEvent;
        this.pitch = pitch;
    }

    @Override
    public String getPacketId()
    {
        return Packets.PlaySoundPacketId;
    }

    @Override
    public Packet decode(FriendlyByteBuf buffer)
    {
        var soundEvent = SoundEvent.createVariableRangeEvent(buffer.readResourceLocation());
        var pitch = buffer.readFloat();
        return new PlaySoundPacket(soundEvent, pitch);
    }

    @Override
    public void encode(FriendlyByteBuf buffer)
    {
        buffer.writeResourceLocation(soundEvent != null ? soundEvent.location() : SoundEvents.BONE_MEAL_USE.location());
        buffer.writeFloat(pitch);
    }

    @Override
    public void onReceive(IPayloadContext context)
    {
        var player = Minecraft.getInstance().player;
        if (player != null)
            player.playSound(soundEvent, 1.0F, pitch);
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
            case SCULK_FOUND -> SoundEvents.SCULK_CLICKING;
            case INFESTED_BLOCK -> SoundEvents.SILVERFISH_DEATH;
            case BONEMEAL -> SoundEvents.BONE_MEAL_USE;
            case RAIL_PLACED -> SoundEvents.METAL_PLACE;
            case REDSTONE_WIRE_PLACED, STONE_PLACED -> SoundEvents.STONE_PLACE;
            case WOOD_PLACED -> SoundEvents.WOOD_PLACE;
            case RAILS_FOUND -> SoundEvents.ANVIL_LAND;
            case MINECRAFT -> soundEvent;
            case UNDEFINED -> null;
        };
    }
}
