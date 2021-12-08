package com.pekar.materialext.blocks;

import com.pekar.materialext.MaterialExtensionMod;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class BlockRegistry
{
    public static final RegistryObject<Block> CRACKED_ENDSTONE = MaterialExtensionMod.BLOCKS.register("cracked_endstone_block", CrackedEndStoneBlock::new);
//    public static final RegistryObject<Block>  = MaterialExtensionMod.BLOCKS.register("", ::new);

    public static void initStatic()
    {
        // just to initialize static members
    }
}
