package com.pekar.angelblock.items;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
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
        var level = player.level();
        var isClientSide = level.isClientSide();

        if (interactionTarget instanceof Piglin piglin)
        {
            if (!isClientSide)
            {
                eraseMobMemory(piglin, player);
            }

            utils.sound.playSoundByLivingEntity(player, player, SoundEvents.LEVER_CLICK, SoundSource.PLAYERS, 1F, 2F);

            return getToolInteractionResult(level.isClientSide());
        }
//        else if (interactionTarget instanceof ZombifiedPiglin zombifiedPiglin)
//        {
//            if (!isClientSide)
//            {
//                eraseMobMemory(zombifiedPiglin, player);
//            }
//
//            return getToolInteractionResult(level.isClientSide());
//        }

        return InteractionResult.PASS;
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        var level = context.getLevel();
        var pos = context.getClickedPos();

        if (erasePiglinsMemory(level, pos, context.getPlayer()))
        {
            return getToolInteractionResult(level.isClientSide());
        }

        return InteractionResult.PASS;
    }

    private boolean erasePiglinsMemory(Level level, BlockPos pos, Player player)
    {
        if (!level.isClientSide())
        {
            var monsters = level.getEntitiesOfClass(Mob.class, new AABB(pos).inflate(EFFECTIVE_RADIUS), entity -> entity instanceof Piglin || entity instanceof ZombifiedPiglin);

            for (Entity entity : monsters)
            {
                if (entity instanceof Piglin piglin) eraseMobMemory(piglin, player);
                //else if (entity instanceof ZombifiedPiglin zombifiedPiglin) eraseMobMemory(zombifiedPiglin, player);
            }

            if (!monsters.isEmpty())
                level.playSound(player, player, SoundEvents.LEVER_CLICK, SoundSource.PLAYERS, 1F, 2F);

            return !monsters.isEmpty();
        }
        return false;
    }

    private void eraseMobMemory(Mob mob, Player player)
    {
        mob.setLastHurtByMob(null);
        mob.setTarget(null);

//        if (mob instanceof ZombifiedPiglin piglin)
//        {
//            var level = mob.level();
//            piglin.setPersistentAngerTarget(null);
//            piglin.stopBeingAngry();
//            piglin.setRemainingPersistentAngerTime(0);
////            piglin.goalSelector.getAvailableGoals().clear();
////            piglin.targetSelector.getAvailableGoals().clear();
//
////            piglin.targetSelector.removeAllGoals(x -> x instanceof HurtByTargetGoal);
////            piglin.targetSelector.addGoal(1, new HurtByTargetGoal(piglin, ZombifiedPiglin.class).setAlertOthers());
//            piglin.getBrain().eraseMemory(MemoryModuleType.HURT_BY);
//
//            if (piglin.targetSelector.getAvailableGoals().stream().noneMatch(g -> g.getGoal() instanceof HurtByTargetGoal))
//            {
//                piglin.targetSelector.addGoal(1, new HurtByTargetGoal(piglin, ZombifiedPiglin.class).setAlertOthers());
//            }
//
//            piglin.getBrain().setActiveActivityIfPossible(Activity.IDLE);
//            piglin.getBrain().updateActivityFromSchedule(level.getDayTime(), level.getGameTime());
//        }

        mob.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
        mob.getBrain().eraseMemory(MemoryModuleType.ANGRY_AT);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand)
    {
        if (interactionHand == InteractionHand.MAIN_HAND)
        {
            var pos = player.getOnPos();

            if (erasePiglinsMemory(level, pos, player))
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
