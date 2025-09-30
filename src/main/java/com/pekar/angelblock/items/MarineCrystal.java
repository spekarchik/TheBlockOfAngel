package com.pekar.angelblock.items;

import com.pekar.angelblock.tooltip.ITooltip;
import com.pekar.angelblock.tooltip.ITooltipProvider;
import com.pekar.angelblock.tooltip.TextStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class MarineCrystal extends ModItem implements ITooltipProvider
{
    public MarineCrystal(Properties properties)
    {
        super(properties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity entity, InteractionHand usedHand)
    {
        var level = player.level();
        var isClientSide = level.isClientSide();

        if (entity instanceof Player) return InteractionResult.FAIL;

        if (usedHand != InteractionHand.MAIN_HAND)
            return InteractionResult.PASS;

        if (player instanceof ServerPlayer serverPlayer)
        {
            boolean result = entity.removeAllEffects();
            if (result)
                level.playSound(null, entity, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.NEUTRAL, 1F, 1F);
        }

        return sidedSuccess(isClientSide);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> component, TooltipFlag flag)
    {
        ITooltipProvider.appendHoverText(this, stack, context, display, component, flag);
    }

    @Override
    public void addTooltip(ItemStack stack, TooltipContext context, ITooltip tooltip, TooltipFlag flag)
    {
        if (!utils.text.showExtendedDescription(tooltip, flag)) return;

        for (int i = 0; i <= 3; i++)
        {
            tooltip.addLine(getDescriptionId(), i).styledAs(TextStyle.Notice, i == 3).apply();
        }
    }
}
