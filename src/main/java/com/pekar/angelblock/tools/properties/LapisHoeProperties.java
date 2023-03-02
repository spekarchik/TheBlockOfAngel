package com.pekar.angelblock.tools.properties;

import com.pekar.angelblock.tools.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;

public class LapisHoeProperties implements IMaterialProperties
{
    @Override
    public boolean isSafeToBreak(LivingEntity entity, BlockPos pos)
    {
        return Utils.isFallSafeExact(entity, pos);
    }
}
