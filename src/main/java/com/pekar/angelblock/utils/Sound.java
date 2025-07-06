package com.pekar.angelblock.utils;

import com.pekar.angelblock.network.packets.PlaySoundPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;

public class Sound
{
    public void playSoundByBlock(net.minecraft.world.entity.player.Player player, BlockPos pos, SoundEvent soundEvent, float volume, float pitch)
    {
        playSound(player, pos, soundEvent, SoundSource.BLOCKS, volume, pitch);
    }

    public void playSoundByBlock(net.minecraft.world.entity.player.Player player, BlockPos pos, SoundEvent soundEvent)
    {
        playSoundByBlock(player, pos, soundEvent, 1F, 1F);
    }

    public void playSoundByBlock(net.minecraft.world.entity.player.Player player, BlockPos pos, SoundType soundType)
    {
        var soundEvent = getSound(soundType);
        if (soundEvent == null) return;
        playSoundByBlock(player, pos, soundEvent, 1F, 5F);
    }

    public void playSoundOnBothSides(ServerPlayer player, BlockPos pos, SoundType soundType, SoundSource soundSource, float pitch)
    {
        var soundEvent = getSound(soundType);
        if (soundEvent == null) return;

        var level = player.level();
        new PlaySoundPacket(soundEvent, pitch).sendToPlayer(player);
        level.playSound(player, pos, soundEvent, soundSource, 1F, pitch);
    }

    public void playSoundByLivingEntity(net.minecraft.world.entity.player.Player player, LivingEntity sourceEntity, SoundEvent soundEvent, SoundSource soundSource, float volume, float pitch)
    {
        var level = player.level();
        if (level.isClientSide())
            level.playLocalSound(sourceEntity.blockPosition(), soundEvent, soundSource, volume, pitch, true);
        else
            level.playSound(player, sourceEntity, soundEvent, soundSource, volume, pitch);
    }

    public void playSound(net.minecraft.world.entity.player.Player player, BlockPos pos, SoundEvent soundEvent, SoundSource soundSource, float volume, float pitch)
    {
        var level = player.level();
        if (level.isClientSide())
            level.playLocalSound(pos, soundEvent, soundSource, volume, pitch, true);
        else
            level.playSound(player, pos, soundEvent, soundSource, volume, pitch);
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
            default -> null;
        };
    }
}
