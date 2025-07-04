package com.pekar.angelblock.items;

import com.pekar.angelblock.network.packets.PlaySoundPacket;
import com.pekar.angelblock.potions.PotionRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class BiosDiamond extends ModItemWithDoubleHoverText
{
    private static final int COOLDOWN_TIME = 100;

    public BiosDiamond(Properties properties)
    {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand interactionHand)
    {
        if (!player.hasEffect(MobEffects.ABSORPTION) && !player.hasEffect(PotionRegistry.BIOS_DIAMOND_COOLDOWN_EFFECT))
        {
            if (player instanceof ServerPlayer serverPlayer)
            {
                int duration = level.getRandom().nextIntBetweenInclusive(40, 200);
                int effectLevel = level.getRandom().nextIntBetweenInclusive(0, 4);
                player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, duration, effectLevel, true, true));
                player.addEffect(new MobEffectInstance(PotionRegistry.BIOS_DIAMOND_COOLDOWN_EFFECT, duration + COOLDOWN_TIME, 0, true, false, true));
                new PlaySoundPacket(SoundEvents.LEVER_CLICK, 2.0F).sendToPlayer(serverPlayer);
            }

            return sidedSuccess(level.isClientSide());
        }

        return InteractionResult.PASS;
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
