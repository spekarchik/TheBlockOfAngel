package com.pekar.materialext.blocks.tile_entities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class AngelBlockEntity extends BlockEntity implements BlockEntityTicker<AngelBlockEntity>
{
    private int counter;

    public AngelBlockEntity(BlockPos blockPos, BlockState blockState)
    {
        super(EntityRegistry.ANGEL_BLOCK_ENTITY.get(), blockPos, blockState);
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState blockState, AngelBlockEntity entity)
    {
        if (++counter > 40)
        {
            onUpdate(level, pos, blockState, entity);
            counter = 0;
        }
    }

    /* //@Override
    public void update()
    {
        if (++counter > 40)
        {
            onUpdate();
            counter = 0;
        }
    }*/

    private void onUpdate(Level level, BlockPos pos, BlockState blockState, AngelBlockEntity blockEntity)
    {
        if (level.isClientSide()) return;

        BlockPos blockPos = blockEntity.getBlockPos();
        List<Monster> entities = level.getNearbyEntities(Monster.class,
                TargetingConditions.forNonCombat().range(70.0).ignoreLineOfSight().ignoreInvisibilityTesting(),
                null,
                AABB.ofSize(new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ()), 10.0, 10.0, 10.0));

        for (LivingEntity entity : entities)
        {
//            double distance = entity.getDistanceSqToCenter(pos);
//            if (distance > 4900) continue;

            entity.discard();
            //world.removeEntityDangerously(entity);
        }
    }
}
