package com.pekar.angelblock.tools.properties;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;

public class DefaultMaterialProperties implements IMaterialProperties
{
    @Override
    public boolean isSafeToBreak(LivingEntity entity, BlockPos pos)
    {
        return true;
    }
}
