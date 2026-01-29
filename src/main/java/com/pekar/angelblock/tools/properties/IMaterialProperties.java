package com.pekar.angelblock.tools.properties;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;

public interface IMaterialProperties
{
    boolean isSafeToBreak(LivingEntity entity, BlockPos pos);
}
