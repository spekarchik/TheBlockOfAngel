package com.pekar.angelblock.tools.properties;

import com.pekar.angelblock.utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;

public class RendelithicMaterialProperties implements IMaterialProperties
{
    @Override
    public boolean isSafeToBreak(LivingEntity entity, BlockPos pos)
    {
        var blockUtils = Utils.instance.blocks;
        return (entity.isInLava() || !blockUtils.conditions.isNearLava(entity.level(), pos))
                && !blockUtils.types.holdsSuspiciousOrLiquid(entity, pos, false, true);
    }
}
