package com.pekar.angelblock.items;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
    public SoaringSporeEssence(Properties properties)
    {
        super(properties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity entity, InteractionHand usedHand)
    {
        var level = player.level();
        var isClientSide = level.isClientSide();

        if (entity instanceof Player) return InteractionResult.FAIL;

        if (player instanceof ServerPlayer serverPlayer)
        {
            if (!entity.hasEffect(MobEffects.GLOWING) || !entity.hasEffect(MobEffects.SLOW_FALLING))
            {
                entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, MobEffectInstance.INFINITE_DURATION, 0, true, true));
                entity.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, MobEffectInstance.INFINITE_DURATION, 0, true, true));

                if (!player.isCreative())
                    stack.shrink(1);
            }
        }

        utils.sound.playSoundByLivingEntity(player, entity, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.NEUTRAL, 1F, 1F);

        return sidedSuccess(isClientSide);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {
        if (!utils.text.showExtendedDescription(tooltipComponents)) return;

        for (int i = 1; i <= 4; i++)
        {
            var component = getDescription(i, false);
            tooltipComponents.add(component);
        }
    }
}
