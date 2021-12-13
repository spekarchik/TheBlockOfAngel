package com.pekar.materialext.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class ModBlock extends Block
{
    protected ModBlock(Material material)
    {
        super(BlockBehaviour.Properties.of(material));
    }

    protected ModBlock(BlockBehaviour.Properties properties)
    {
        super(properties);
    }
}
