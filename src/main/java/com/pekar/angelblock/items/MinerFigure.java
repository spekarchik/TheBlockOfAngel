package com.pekar.angelblock.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class MinerFigure extends ModItemWithDoubleHoverText
{
    private static final double EFFECTIVE_RADIUS = 16.0;

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        var level = context.getLevel();
        var pos = context.getClickedPos();

        if (!level.isClientSide())
        {
            var monsters = level.getEntities((Entity) null,
                    new AABB(pos).inflate(EFFECTIVE_RADIUS),
                    entity -> entity instanceof Piglin);

            for (Entity entity : monsters)
            {
                var piglin = (Piglin) entity;
                piglin.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
                piglin.getBrain().eraseMemory(MemoryModuleType.ANGRY_AT);
            }

            if (!monsters.isEmpty()) return getToolInteractionResult(level.isClientSide());
        }

        return super.useOn(context);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand)
    {
        if (interactionHand == InteractionHand.MAIN_HAND)
        {
            var pos = player.getOnPos();

            if (!level.isClientSide())
            {
                var monsters = level.getEntities((Entity) null,
                        new AABB(pos).inflate(EFFECTIVE_RADIUS),
                        entity -> entity instanceof Piglin);

                for (Entity entity : monsters)
                {
                    var piglin = (Piglin) entity;
                    piglin.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
                    piglin.getBrain().eraseMemory(MemoryModuleType.ANGRY_AT);
                }

                if (!monsters.isEmpty())
                    return InteractionResultHolder.sidedSuccess(player.getItemInHand(interactionHand), level.isClientSide());
            }
        }

        return super.use(level, player, interactionHand);
    }

    private InteractionResult getToolInteractionResult(boolean isClientSide)
    {
        return isClientSide ? InteractionResult.SUCCESS_NO_ITEM_USED: InteractionResult.CONSUME_PARTIAL;
    }
}
