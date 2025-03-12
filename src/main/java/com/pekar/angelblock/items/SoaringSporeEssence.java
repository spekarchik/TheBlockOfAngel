package com.pekar.angelblock.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class SoaringSporeEssence extends ModItemWithMultipleHoverText
{
    public SoaringSporeEssence()
    {
        super(new Properties().stacksTo(4));
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity entity, InteractionHand usedHand)
    {
        var level = player.level();
        var isClientSide = level.isClientSide();

        if (!isClientSide)
        {
            if (entity.hasEffect(MobEffects.GLOWING) && entity.hasEffect(MobEffects.SLOW_FALLING))
            {
                entity.removeEffect(MobEffects.GLOWING);
                entity.removeEffect(MobEffects.SLOW_FALLING);
            }
            else
            {
                entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, MobEffectInstance.INFINITE_DURATION, 0, true, true));
                entity.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, MobEffectInstance.INFINITE_DURATION, 0, true, true));
            }

            if (!player.isCreative())
                stack.shrink(1);
        }

        return InteractionResult.sidedSuccess(isClientSide);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {
        if (!utils.text.showExtendedDescription(tooltipComponents)) return;

        for (int i = 1; i <= 3; i++)
        {
            var component = getDescription();
            tooltipComponents.add(component);
        }
    }
}
