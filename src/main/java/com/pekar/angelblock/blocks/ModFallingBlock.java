package com.pekar.angelblock.blocks;

import com.pekar.angelblock.Main;
import net.minecraft.world.level.block.FallingBlock;

public abstract class ModFallingBlock extends FallingBlock
{
    public ModFallingBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public String getDescriptionId()
    {
        return super.getDescriptionId().replace("block." + Main.MODID, "item." + Main.MODID);
    }
}
