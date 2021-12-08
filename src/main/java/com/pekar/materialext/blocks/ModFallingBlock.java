package com.pekar.materialext.blocks;

import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public abstract class ModFallingBlock extends FallingBlock
{
    protected ModFallingBlock(Material material)
    {
        super(BlockBehaviour.Properties.of(material));
    }

    protected ModFallingBlock(BlockBehaviour.Properties properties)
    {
        super(properties);
    }
}
