package com.pekar.materialext.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

import java.util.Random;

public class CrackedEndStoneBlock extends ModBlock
{
    public CrackedEndStoneBlock()
    {
        super(BlockBehaviour.Properties.of(Material.SNOW)
                .strength(0.5f)
                .sound(SoundType.SNOW)
                .requiresCorrectToolForDrops());
    }

    @Override
    public int getExpDrop(BlockState state, LevelReader world, BlockPos pos, int fortune, int silktouch)
    {
        return new Random().nextInt(2,5);
    }
}
