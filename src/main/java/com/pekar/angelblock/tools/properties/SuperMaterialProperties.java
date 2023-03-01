package com.pekar.angelblock.tools.properties;

import com.pekar.angelblock.tools.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;

public class SuperMaterialProperties implements IMaterialProperties
{
    @Override
    public boolean isSafeToBreak(LivingEntity entity, BlockPos pos)
    {
        return !Utils.isNearLavaOrWaterOrUnsafe(entity, pos);
    }
}
