package com.pekar.angelblock.potions;

import com.pekar.angelblock.blocks.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;

public class BlockBreakerMobEffect extends MobEffect
{
    protected BlockBreakerMobEffect(MobEffectCategory category, int color)
    {
        super(category, color);
    }

    @Override
    public boolean isInstantenous()
    {
        return true;
    }

    @Override
    public void applyInstantenousEffect(@Nullable Entity source, @Nullable Entity indirectSource, LivingEntity entity, int amplifier, double health)
    {
        if (source == null) return;

        BlockPos position = source.blockPosition();
        int X = position.getX(), Y = position.getY(), Z = position.getZ();

        for (int x = X - 1; x <= X + 1; x++)
            for (int y = Y - 1; y <= Y + 1; y++)
                for (int z = Z - 1; z <= Z + 1; z++)
                {
                    BlockPos currentPos = new BlockPos(x, y, z);
                    var level = entity.level;
                    Block block = level.getBlockState(currentPos).getBlock();
                    if (block != Blocks.DIAMOND_BLOCK) continue;

                    level.setBlock(currentPos, BlockRegistry.DIAMOND_POWDER_BLOCK.get().defaultBlockState(), 0);
                    level.destroyBlock(currentPos, true, source, 1);
                }
    }
}
