package com.pekar.angelblock.tools.properties;

import com.pekar.angelblock.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;

public class LimoniteMaterialProperties implements IMaterialProperties
{
    @Override
    public boolean isSafeToBreak(LivingEntity entity, BlockPos pos)
    {
        return !Utils.isStandingOnBreakingBlock(entity, pos);
    }
}
