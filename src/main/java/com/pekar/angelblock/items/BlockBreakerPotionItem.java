package com.pekar.angelblock.items;

import com.pekar.angelblock.potions.BlockBreakerPotion;
import com.pekar.angelblock.tooltip.ITooltip;
import com.pekar.angelblock.tooltip.ITooltipProvider;
import com.pekar.angelblock.tooltip.TextStyle;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;

import java.util.function.Consumer;

public class BlockBreakerPotionItem extends ModItem implements ProjectileItem, ITooltipProvider
{
    public static float PROJECTILE_SHOOT_POWER = 1.0F;

    public BlockBreakerPotionItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand)
    {
        ItemStack itemStack = player.getItemInHand(hand);

        if (level instanceof ServerLevel serverLevel)
        {
            Projectile.spawnProjectileFromRotation(BlockBreakerPotion::new, serverLevel, itemStack, player, 0.0F, PROJECTILE_SHOOT_POWER, 1.0F);
        }

        if (!player.getAbilities().instabuild)
        {
            itemStack.shrink(1);
        }

        return sidedSuccess(level.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> component, TooltipFlag flag)
    {
        ITooltipProvider.appendHoverText(this, stack, context, display, component, flag);
    }

    @Override
    public Projectile asProjectile(Level level, Position position, ItemStack itemStack, Direction direction)
    {
        return new BlockBreakerPotion(position.x(), position.y(), position.z(), level, itemStack);
    }

    @Override
    public void addTooltip(ItemStack stack, TooltipContext context, ITooltip tooltip, TooltipFlag flag)
    {
        if (!utils.text.showExtendedDescription(tooltip, flag)) return;

        for (int i = 0; i <= 20; i++)
        {
            tooltip.addLine(getDescriptionId(), i).styledAs(TextStyle.Header, i == 1 || i == 12).styledAs(TextStyle.DarkGray, i == 20).apply();
        }
    }
}
