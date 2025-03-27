package com.pekar.angelblock.tools.properties;

import com.pekar.angelblock.utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;

public class LapisMaterialProperties implements IMaterialProperties
{
    @Override
    public boolean isSafeToBreak(LivingEntity entity, BlockPos pos)
    {
        return entity.isInWater() || !Utils.instance.blocks.conditions.isNearWater(entity.level(), pos);
    }
}
