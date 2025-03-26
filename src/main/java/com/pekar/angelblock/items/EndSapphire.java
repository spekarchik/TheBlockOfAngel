package com.pekar.angelblock.items;

import com.pekar.angelblock.network.packets.PlaySoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class EndSapphire extends ModItemWithDoubleHoverText
{
    public EndSapphire(Properties properties)
    {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand interactionHand)
    {
        if (!player.hasEffect(MobEffects.LEVITATION))
        {
            if (player instanceof ServerPlayer serverPlayer)
            {
                int duration = level.getRandom().nextIntBetweenInclusive(40, 300);
                int effectLevel = level.getRandom().nextIntBetweenInclusive(0, 50);
                player.addEffect(new MobEffectInstance(MobEffects.LEVITATION, duration, effectLevel, true, true));
                new PlaySoundPacket(SoundEvents.PLAYER_LEVELUP).sendToPlayer(serverPlayer);
            }

            return sidedSuccess(level.isClientSide());
        }

        return InteractionResult.PASS;
    }
}
