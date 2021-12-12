package com.pekar.materialext.blocks;

import com.pekar.materialext.MaterialExt;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class BlockRegistry
{
    public static final RegistryObject<Block> CRACKED_ENDSTONE = MaterialExt.BLOCKS.register("cracked_endstone_block", CrackedEndStoneBlock::new);
    public static final RegistryObject<Block> CRACKED_OBSIDIAN = MaterialExt.BLOCKS.register("cracked_obsidian_block", CrackedObsidianBlock::new);
//    public static final RegistryObject<Block>  = MaterialExt.BLOCKS.register("", ::new);

    public static void initStatic()
    {
        // just to initialize static members
    }
}
