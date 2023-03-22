package com.pekar.angelblock.items;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class EvokerAmulet extends ModItemWithDoubleHoverText
{
    private static final double EFFECTIVE_RADIUS = 120.0;

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand)
    {
        var itemStack = player.getItemInHand(interactionHand);

        if (!level.isClientSide() && level instanceof ServerLevel serverLevel)
        {
            var allays = ((ServerLevel) level).getLevel().getEntities((Entity)null,
                    player.getBoundingBox().inflate(EFFECTIVE_RADIUS), entity -> entity instanceof Allay);

            if (allays.size() < 3)
                level.getChunk(player.getOnPos()).addEntity(
                        EntityType.ALLAY.spawn(serverLevel, itemStack, player, player.getOnPos().above(5), MobSpawnType.NATURAL, true, true));
        }

        return InteractionResultHolder.consume(itemStack);
    }
}
