package com.pekar.angelblock.blocks;

import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;

public class CrackedObsidianBlock extends ModDropExperienceBlock
{
    protected CrackedObsidianBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    protected IntProvider getXpRange()
    {
        return UniformInt.of(2, 5);
    }
}
