package com.pekar.angelblock.items;

import com.pekar.angelblock.potions.BlockBreakerPotion;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class BlockBreakerPotionItem extends ModItemWithMultipleHoverText
{
    public BlockBreakerPotionItem()
    {
        super(new Properties().stacksTo(4));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
    {
        ItemStack itemStack = player.getItemInHand(hand);

        if (!level.isClientSide)
        {
            BlockBreakerPotion potionEntity = new BlockBreakerPotion(level, player);
            potionEntity.setItem(itemStack);
            potionEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), -20.0F, 0.5F, 1.0F);
            level.addFreshEntity(potionEntity);
        }

        if (!player.getAbilities().instabuild)
        {
            itemStack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {
        if (!utils.text.showExtendedDescription(tooltipComponents)) return;

        for (int i = 1; i <= 18; i++)
        {
            tooltipComponents.add(getDescription(i, i == 1 || i == 11, false, i == 18));
        }
    }
}
