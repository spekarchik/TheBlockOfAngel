package com.pekar.angelblock.tools.properties;

import com.pekar.angelblock.utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;

public class SuperMaterialProperties implements IMaterialProperties
{
    @Override
    public boolean isSafeToBreak(LivingEntity entity, BlockPos pos)
    {
        var blockUtils = Utils.instance.blocks;
        return !Utils.instance.player.conditions.isNearLavaOrWaterOrUnsafe(entity, pos)
                && !blockUtils.types.isInfested(entity.level().getBlockState(pos).getBlock())
                && !blockUtils.types.isSuspicious(entity.level().getBlockState(pos).getBlock())
                && !blockUtils.types.holdsSuspiciousOrLiquid(entity.level(), pos, true, true);
    }
}
