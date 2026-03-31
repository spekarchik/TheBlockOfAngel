package com.pekar.angelblock.blocks;

import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;

public class CrackedBlock extends ModDropExperienceBlock
{
    public CrackedBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    protected IntProvider getXpRange()
    {
        return UniformInt.of(1, 2);
    }
}
