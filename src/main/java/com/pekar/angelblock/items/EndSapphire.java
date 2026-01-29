package com.pekar.angelblock.items;

import com.pekar.angelblock.potions.PotionRegistry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class EndSapphire extends ModItemWithDoubleHoverText
{
    private static final int COOLDOWN_TIME = 100;

    public EndSapphire(Properties properties)
    {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand interactionHand)
    {
        if (!player.hasEffect(MobEffects.LEVITATION) && !player.hasEffect(PotionRegistry.END_SAPPHIRE_COOLDOWN_EFFECT))
        {
            if (player instanceof ServerPlayer serverPlayer)
            {
                int duration = level.getRandom().nextIntBetweenInclusive(40, 200);
                int effectLevel = level.getRandom().nextIntBetweenInclusive(0, 20);
                player.addEffect(new MobEffectInstance(MobEffects.LEVITATION, duration, effectLevel, true, true));
                player.addEffect(new MobEffectInstance(PotionRegistry.END_SAPPHIRE_COOLDOWN_EFFECT, duration + COOLDOWN_TIME, 0, true, false, true));
            }

            utils.sound.playSound(player, player.blockPosition(), SoundEvents.LEVER_CLICK, SoundSource.PLAYERS, 1F, 2F);

            return sidedSuccess(level.isClientSide());
        }

        return InteractionResult.PASS;
    }
}
