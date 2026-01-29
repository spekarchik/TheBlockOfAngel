package com.pekar.angelblock.explosions;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.block.state.BlockState;

public class ExplosionNoHurtEntityDamageCalculator extends ExplosionDamageCalculator
{
    @Override
    public boolean shouldDamageEntity(Explosion explosion, Entity entity)
    {
        return false;
    }

    @Override
    public boolean shouldBlockExplode(Explosion explosion, BlockGetter reader, BlockPos pos, BlockState state, float power)
    {
        return false;
    }
}
