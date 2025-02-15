package com.pekar.angelblock.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class EndSapphire extends ModItemWithDoubleHoverText
{
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand)
    {
        if (!player.hasEffect(MobEffects.LEVITATION))
        {
            if (!level.isClientSide())
            {
                int duration = level.getRandom().nextIntBetweenInclusive(40, 300);
                int effectLevel = level.getRandom().nextIntBetweenInclusive(0, 50);
                player.addEffect(new MobEffectInstance(MobEffects.LEVITATION, duration, effectLevel, false, true));
            }

            return InteractionResultHolder.sidedSuccess(player.getItemInHand(interactionHand), level.isClientSide());
        }

        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }
}
