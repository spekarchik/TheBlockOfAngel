package com.pekar.angelblock.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

import java.util.Random;

public class GreenDiamondBlock extends Block
{
    public GreenDiamondBlock()
    {
        super(BlockBehaviour.Properties.of(Material.POWDER_SNOW).strength(1F).sound(SoundType.SNOW).requiresCorrectToolForDrops());
    }

    @Override
    public int getExpDrop(BlockState state, LevelReader world, BlockPos pos, int fortune, int silktouch)
    {
        return new Random().nextInt(5,10);
    }
}
