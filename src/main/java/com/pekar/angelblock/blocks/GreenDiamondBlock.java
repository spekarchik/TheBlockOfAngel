package com.pekar.angelblock.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class GreenDiamondBlock extends ModBlockWithHoverText
{
    public GreenDiamondBlock()
    {
        super(BlockBehaviour.Properties.of(Material.POWDER_SNOW).strength(1F)
                .lightLevel(state -> 12).sound(SoundType.SNOW).requiresCorrectToolForDrops());
    }

    @Override
    public int getExpDrop(BlockState state, LevelReader level, RandomSource randomSource, BlockPos pos, int fortuneLevel, int silkTouchLevel)
    {
        return randomSource.nextInt(5, 10);
    }
}
