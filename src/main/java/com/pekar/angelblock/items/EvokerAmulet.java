package com.pekar.angelblock.items;

import com.pekar.angelblock.events.cleaners.Cleaner;
import com.pekar.angelblock.events.cleaners.TrackedAllay;
import com.pekar.angelblock.tooltip.ITooltip;
import com.pekar.angelblock.tooltip.ITooltipProvider;
import com.pekar.angelblock.tooltip.TextStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;

import java.util.function.Consumer;

public class EvokerAmulet extends ModItem implements ITooltipProvider
{
    private static final double EFFECTIVE_RADIUS = 120.0;

    public EvokerAmulet(Properties properties)
    {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand interactionHand)
    {
        var itemStack = player.getItemInHand(interactionHand);

        if (level instanceof ServerLevel serverLevel)
        {
            var allays = serverLevel.getEntities((Entity)null,
                    player.getBoundingBox().inflate(EFFECTIVE_RADIUS), entity -> entity instanceof Allay);

            if (allays.size() < 3)
            {
                var allay = EntityType.ALLAY.spawn(serverLevel, itemStack, player, player.blockPosition().relative(player.getDirection(), 2).above(), EntitySpawnReason.SPAWN_ITEM_USE, true, true);
                if (allay != null)
                {
                    level.getChunk(player.getOnPos()).addEntity(allay);

                    var targetToTrack = new TrackedAllay(allay, player);
                    Cleaner.add(targetToTrack);
                }

                level.playSound(null, player, SoundEvents.LEVER_CLICK, SoundSource.PLAYERS, 1F, 2F);
            }
        }

        return sidedSuccess(level.isClientSide());
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
            tooltip.addLine(getDescriptionId(), i)
                    .styledAs(TextStyle.DarkGray, i == 2)
                    .styledAs(TextStyle.Notice, i == 3)
                    .apply();
        }
    }
}
