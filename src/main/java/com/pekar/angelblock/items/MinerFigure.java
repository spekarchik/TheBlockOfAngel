package com.pekar.angelblock.items;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.Zoglin;
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
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity interactionTarget, InteractionHand usedHand)
    {
        if (interactionTarget instanceof Piglin || interactionTarget instanceof Zoglin)
        {
            var level = player.level();
            var isClientSide = level.isClientSide();

            if (!isClientSide)
            {
                eraseMobMemory((Mob)interactionTarget);
            }

            return getToolInteractionResult(level.isClientSide());
        }
        else
        {
            return InteractionResult.PASS;
        }
    }

    private static void eraseMobMemory(Mob mob)
    {
        mob.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
        mob.getBrain().eraseMemory(MemoryModuleType.ANGRY_AT);
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        var level = context.getLevel();
        var pos = context.getClickedPos();

        if (erasePiglinsMemory(level, pos))
        {
            return getToolInteractionResult(level.isClientSide());
        }

        return InteractionResult.PASS;
    }

    private boolean erasePiglinsMemory(Level level, BlockPos pos)
    {
        if (!level.isClientSide())
        {
            var monsters = level.getEntitiesOfClass(Mob.class, new AABB(pos).inflate(EFFECTIVE_RADIUS), entity -> entity instanceof Piglin || entity instanceof Zoglin);

            for (Entity entity : monsters)
            {
                var piglin = (Piglin) entity;
                piglin.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
                piglin.getBrain().eraseMemory(MemoryModuleType.ANGRY_AT);
            }

            return !monsters.isEmpty();
        }
        return false;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand)
    {
        if (interactionHand == InteractionHand.MAIN_HAND)
        {
            var pos = player.getOnPos();

            if (erasePiglinsMemory(level, pos))
            {
                return InteractionResultHolder.sidedSuccess(player.getItemInHand(interactionHand), level.isClientSide());
            }
        }

        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }

    private InteractionResult getToolInteractionResult(boolean isClientSide)
    {
        return isClientSide ? InteractionResult.SUCCESS_NO_ITEM_USED: InteractionResult.CONSUME_PARTIAL;
    }
}
