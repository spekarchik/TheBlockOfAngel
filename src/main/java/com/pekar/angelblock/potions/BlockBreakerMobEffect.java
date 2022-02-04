package com.pekar.angelblock.potions;

import com.pekar.angelblock.blocks.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;
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
                    applyPotionToBlock(source, currentPos);
                }
    }

    private void applyPotionToBlock(@NotNull Entity source, BlockPos pos)
    {
        var level = source.level;
        Block block = level.getBlockState(pos).getBlock();

        if (block == Blocks.DIAMOND_BLOCK)
        {
            level.setBlock(pos, BlockRegistry.DESTROYING_DIAMOND_POWDER_BLOCK.get().defaultBlockState(), 0);
            level.destroyBlock(pos, true, source, 1);
        }
        else if (block == Blocks.LAVA)
        {
            level.setBlock(pos, Blocks.END_STONE.defaultBlockState(), 11);
        }
        else if (block == Blocks.TUFF)
        {
            level.setBlock(pos, BlockRegistry.GUNPOWDER_BLOCK.get().defaultBlockState(), 0);
            level.destroyBlock(pos, true, source, 1);
        }
        else if (block == Blocks.CRYING_OBSIDIAN)
        {
            level.setBlock(pos, BlockRegistry.DESTROYING_DIAMOND_BLOCK.get().defaultBlockState(), 0);
            level.destroyBlock(pos, true, source, 1);
        }
        else if (block == Blocks.BASALT)
        {
            level.setBlock(pos, BlockRegistry.DESTROYING_PRISMARINE_SHARD_BLOCK.get().defaultBlockState(), 0);
            level.destroyBlock(pos, true, source, 1);
        }
        else if (block == Blocks.GRANITE)
        {
            level.setBlock(pos, Blocks.RED_SAND.defaultBlockState(), 11);
        }
        else if (block == Blocks.WHITE_WOOL)
        {
            level.setBlock(pos, BlockRegistry.DESTROYING_WHITE_WOOL.get().defaultBlockState(), 0);
            level.destroyBlock(pos, true, source, 1);
        }
        else if (block == Blocks.GLOWSTONE)
        {
            level.setBlock(pos, BlockRegistry.DESTROYING_BLAZE_POWDER.get().defaultBlockState(), 0);
            level.destroyBlock(pos, true, source, 1);
        }
    }
}
