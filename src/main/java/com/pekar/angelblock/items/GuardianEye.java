package com.pekar.angelblock.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class GuardianEye extends ModItemWithDoubleHoverText
{
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand)
    {
        if (!player.hasEffect(MobEffects.NIGHT_VISION))
        {
            if (!level.isClientSide())
            {
                player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 1200, 0, true, true));
            }

            return InteractionResultHolder.sidedSuccess(player.getItemInHand(interactionHand), level.isClientSide());
        }

        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }
}
