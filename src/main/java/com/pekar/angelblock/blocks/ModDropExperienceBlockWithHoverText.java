package com.pekar.angelblock.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public abstract class ModDropExperienceBlockWithHoverText extends ModBlockWithHoverText
{
    private final IntProvider xpRange;

    public ModDropExperienceBlockWithHoverText(Properties properties)
    {
        super(properties);
        this.xpRange = getXpRange();
    }

    @Override
    public int getExpDrop(BlockState state, LevelAccessor level, BlockPos pos, @Nullable BlockEntity blockEntity, @Nullable Entity breaker, ItemStack tool)
    {
        return this.xpRange.sample(level.getRandom());
    }

    protected abstract IntProvider getXpRange();
}
