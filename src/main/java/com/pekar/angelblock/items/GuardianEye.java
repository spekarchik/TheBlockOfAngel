package com.pekar.angelblock.items;

import com.pekar.angelblock.potions.PotionRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class GuardianEye extends ModItemWithMultipleHoverText
{
    public GuardianEye()
    {
        super(new Properties());
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {
        if (!utils.text.showExtendedDescription(tooltipComponents)) return;

        for (int i = 1; i <= 3; i++)
        {
            tooltipComponents.add(getDescription(i, false, false, i == 3, false, i == 2));
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand)
    {
        if (!player.hasEffect(MobEffects.NIGHT_VISION) && !player.hasEffect(PotionRegistry.ELDER_GUARDIAN_EYE_EFFECT))
        {
            if (player instanceof ServerPlayer serverPlayer)
            {
                player.addEffect(new MobEffectInstance(PotionRegistry.ELDER_GUARDIAN_EYE_EFFECT, MobEffectInstance.INFINITE_DURATION, 0, true, true));
            }

            utils.sound.playSound(player, player.blockPosition(), SoundEvents.LEVER_CLICK, SoundSource.PLAYERS, 1F, 2F);

            return InteractionResultHolder.sidedSuccess(player.getItemInHand(interactionHand), level.isClientSide());
        }

        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }
}
