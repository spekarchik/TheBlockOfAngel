package com.pekar.angelblock.items;

import com.pekar.angelblock.network.packets.PlaySoundPacket;
import com.pekar.angelblock.tooltip.ITooltip;
import com.pekar.angelblock.tooltip.ITooltipProvider;
import com.pekar.angelblock.tooltip.TextStyle;
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
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;

import java.util.function.Consumer;

public class BiosDiamond extends ModItem implements ITooltipProvider
{
    public BiosDiamond(Properties properties)
    {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand interactionHand)
    {
        if (!player.hasEffect(MobEffects.ABSORPTION))
        {
            if (player instanceof ServerPlayer serverPlayer)
            {
                int duration = level.getRandom().nextIntBetweenInclusive(40, 200);
                int effectLevel = level.getRandom().nextIntBetweenInclusive(0, 4);
                player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, duration, effectLevel, true, true));
                new PlaySoundPacket(SoundEvents.LEVER_CLICK, 2.0F).sendToPlayer(serverPlayer);
            }

            return sidedSuccess(level.isClientSide());
        }

        return InteractionResult.PASS;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> component, TooltipFlag flag)
    {
        ITooltipProvider.appendHoverText(this, stack, context, display, component, flag);
    }

    @Override
    public void addTooltip(ItemStack stack, TooltipContext context, ITooltip tooltip, TooltipFlag flag)
    {
        if (!utils.text.showExtendedDescription(tooltip)) return;

        for (int i = 1; i <= 3; i++)
        {
            tooltip.addLine(getDescriptionId(), i).styledAs(TextStyle.Notice, i == 2).withFormatting(ChatFormatting.DARK_GREEN, i == 3).apply();
        }
    }
}
