package com.pekar.angelblock.items;

import com.pekar.angelblock.network.packets.PlaySoundPacket;
import com.pekar.angelblock.potions.PotionRegistry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class EndSapphire extends ModItemWithDoubleHoverText
{
    private static final int COOLDOWN_TIME = 100;

    public EndSapphire()
    {
        super(new Properties());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand)
    {
        if (!player.hasEffect(MobEffects.LEVITATION) && !player.hasEffect(PotionRegistry.END_SAPPHIRE_COOLDOWN_EFFECT))
        {
            if (player instanceof ServerPlayer serverPlayer)
            {
                int duration = level.getRandom().nextIntBetweenInclusive(40, 200);
                int effectLevel = level.getRandom().nextIntBetweenInclusive(0, 20);
                player.addEffect(new MobEffectInstance(MobEffects.LEVITATION, duration, effectLevel, true, true));
                player.addEffect(new MobEffectInstance(PotionRegistry.END_SAPPHIRE_COOLDOWN_EFFECT, duration + COOLDOWN_TIME, 0, true, false, true));
                new PlaySoundPacket(SoundEvents.LEVER_CLICK, 2.0F).sendToPlayer(serverPlayer);
            }

            return InteractionResultHolder.sidedSuccess(player.getItemInHand(interactionHand), level.isClientSide());
        }

        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }
}
