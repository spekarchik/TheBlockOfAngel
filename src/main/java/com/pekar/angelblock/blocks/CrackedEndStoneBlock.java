package com.pekar.angelblock.blocks;

import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class CrackedEndStoneBlock extends ModDropExperienceBlockWithHoverText
{
    public CrackedEndStoneBlock()
    {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.SAND)
                .strength(0.5f)
                .sound(SoundType.SNOW)
                .requiresCorrectToolForDrops());
    }

    @Override
    protected IntProvider getXpRange()
    {
        return UniformInt.of(2, 5);
    }
}
