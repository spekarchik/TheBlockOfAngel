package com.pekar.angelblock.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class CrackedEndStoneBlock extends ModBlockWithHoverText
{
    public CrackedEndStoneBlock()
    {
        super(BlockBehaviour.Properties.copy(Blocks.SAND)
                .strength(0.5f)
                .sound(SoundType.SNOW)
                .requiresCorrectToolForDrops());
    }

    @Override
    public int getExpDrop(BlockState state, LevelReader level, RandomSource randomSource, BlockPos pos, int fortuneLevel, int silkTouchLevel)
    {
        return randomSource.nextInt(2, 5);
    }
}
