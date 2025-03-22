package com.pekar.angelblock.items;

import com.pekar.angelblock.network.packets.PlaySoundPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class BiosDiamond extends ModItemWithDoubleHoverText
{
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand)
    {
        if (!player.hasEffect(MobEffects.ABSORPTION))
        {
            if (player instanceof ServerPlayer serverPlayer)
            {
                int duration = level.getRandom().nextIntBetweenInclusive(40, 200);
                int effectLevel = level.getRandom().nextIntBetweenInclusive(0, 4);
                player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, duration, effectLevel, true, true));
                new PlaySoundPacket(SoundEvents.PLAYER_LEVELUP).sendToPlayer(serverPlayer);
            }

            return InteractionResultHolder.sidedSuccess(player.getItemInHand(interactionHand), level.isClientSide());
        }

        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {
        if (!utils.text.showExtendedDescription(tooltipComponents)) return;

        for (int i = 1; i <= 3; i++)
        {
            var component = getDisplayName(i).withStyle(ChatFormatting.GRAY);
            if (i == 2)
                component.withStyle(ChatFormatting.ITALIC);
            if (i == 3)
                component.withStyle(ChatFormatting.DARK_GREEN);
            tooltipComponents.add(component);
        }
    }
}
