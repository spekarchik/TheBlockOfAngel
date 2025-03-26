package com.pekar.angelblock.blocks;

import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class CrackedEndStoneBlock extends ModDropExperienceBlockWithHoverText
{
    public CrackedEndStoneBlock(BlockBehaviour.Properties properties)
    {
        super(properties);
    }

    @Override
    protected IntProvider getXpRange()
    {
        return UniformInt.of(2, 5);
    }
}
