package com.pekar.angelblock.items;

import com.pekar.angelblock.potions.BlockBreakerPotion;
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
import net.minecraft.world.level.Level;

import java.util.List;

public class BlockBreakerPotionItem extends ModItemWithMultipleHoverText implements ProjectileItem
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
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {
        if (!utils.text.showExtendedDescription(tooltipComponents)) return;

        for (int i = 0; i <= 20; i++)
        {
            tooltipComponents.add(getDescription(i, i == 1 || i == 12, false, false, false, i == 20));
        }
    }

    @Override
    public Projectile asProjectile(Level level, Position position, ItemStack itemStack, Direction direction)
    {
        return new BlockBreakerPotion(position.x(), position.y(), position.z(), level, itemStack);
    }
}
