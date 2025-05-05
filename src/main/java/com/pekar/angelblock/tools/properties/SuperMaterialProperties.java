package com.pekar.angelblock.tools.properties;

import com.pekar.angelblock.utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;

public class SuperMaterialProperties implements IMaterialProperties
{
    @Override
    public boolean isSafeToBreak(LivingEntity entity, BlockPos pos)
    {
        return !Utils.instance.player.conditions.isNearLavaOrWaterOrUnsafe(entity, pos)
                && !Utils.instance.blocks.types.isInfested(entity.level().getBlockState(pos).getBlock())
                && !Utils.instance.blocks.types.isSuspicious(entity.level().getBlockState(pos).getBlock());
    }
}
