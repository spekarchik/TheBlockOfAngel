package com.pekar.angelblock.blocks;

import com.pekar.angelblock.Main;
import net.minecraft.world.level.block.Block;

public class ModBlock extends Block
{
    public ModBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public String getDescriptionId()
    {
        return super.getDescriptionId().replace("block." + Main.MODID, "item." + Main.MODID);
    }
}
