package com.pekar.angelblock.blocks;

import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;

public class GreenDiamondBlock extends ModDropExperienceBlockWithHoverText
{
    public GreenDiamondBlock()
    {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE).strength(1F)
                .lightLevel(state -> 12).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops());
    }

    @Override
    protected IntProvider getXpRange()
    {
        return UniformInt.of(5, 10);
    }
}
