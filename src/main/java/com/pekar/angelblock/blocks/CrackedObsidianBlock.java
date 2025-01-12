package com.pekar.angelblock.blocks;

import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class CrackedObsidianBlock extends ModDropExperienceBlockWithHoverText
{
    protected CrackedObsidianBlock()
    {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.SAND)
                .strength(10f)
                .sound(SoundType.METAL)
                .requiresCorrectToolForDrops());
    }

    @Override
    protected IntProvider getXpRange()
    {
        return UniformInt.of(2, 5);
    }
}
