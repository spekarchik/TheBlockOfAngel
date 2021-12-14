package com.pekar.materialext.blocks;

import com.pekar.materialext.MaterialExt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.RegistryObject;

public class BlockRegistry
{
    public static final RegistryObject<Block> CRACKED_ENDSTONE = MaterialExt.BLOCKS.register("cracked_endstone_block", CrackedEndStoneBlock::new);
    public static final RegistryObject<Block> CRACKED_OBSIDIAN = MaterialExt.BLOCKS.register("cracked_obsidian_block", CrackedObsidianBlock::new);
    public static final RegistryObject<Block> DIAMOND_POWDER_BLOCK = MaterialExt.BLOCKS.register("diamond_powder_block", () ->
            new Block(BlockBehaviour.Properties.of(Material.SAND).strength(0.7f, 9f).sound(SoundType.SNOW)));
    public static final RegistryObject<Block> OBSIDIAN_POWDER_BLOCK = MaterialExt.BLOCKS.register("obsidian_powder_block", () ->
            new Block(BlockBehaviour.Properties.of(Material.SAND).strength(0.7f, 9f)));
    public static final RegistryObject<Block> ENDSTONE_POWDER_BLOCK = MaterialExt.BLOCKS.register("endstone_powder_block", () ->
            new Block(BlockBehaviour.Properties.of(Material.SAND).strength(0.7f, 9f).sound(SoundType.SNOW)));
    public static final RegistryObject<Block> DIAMITHIC_MATERIAL_BLOCK = MaterialExt.BLOCKS.register("diamithic_material_block", () ->
            new Block(BlockBehaviour.Properties.of(Material.STONE).strength(0.7f, 9f)));
    public static final RegistryObject<Block> RENDELITHIC_MATERIAL_BLOCK = MaterialExt.BLOCKS.register("rendelithic_material_block", () ->
            new Block(BlockBehaviour.Properties.of(Material.STONE).strength(0.7f, 9f)));
    public static final RegistryObject<Block> LIMONITE_MATERIAL_BLOCK = MaterialExt.BLOCKS.register("limonite_material_block", () ->
            new Block(BlockBehaviour.Properties.of(Material.STONE).strength(0.7f, 9f)));
    public static final RegistryObject<Block> LAPIS_MATERIAL_BLOCK = MaterialExt.BLOCKS.register("lapis_material_block", () ->
            new Block(BlockBehaviour.Properties.of(Material.STONE).strength(0.7f, 9f)));
    public static final RegistryObject<Block> SUPER_MATERIAL_BLOCK = MaterialExt.BLOCKS.register("super_material_block", () ->
            new Block(BlockBehaviour.Properties.of(Material.STONE).strength(0.7f, 9f)));

    public static final RegistryObject<Block> ANGEL_BLOCK = MaterialExt.BLOCKS.register("angel_block", AngelBlock::new);

//    public static final RegistryObject<Block>  = MaterialExt.BLOCKS.register("", () ->
//          new ModBlock(BlockBehaviour.Properties.of(Material.).strength(f)));
//    public static final RegistryObject<Block>  = MaterialExt.BLOCKS.register("", () ->
//          new ModBlock(BlockBehaviour.Properties.of(Material.).strength(f)));
//    public static final RegistryObject<Block>  = MaterialExt.BLOCKS.register("", () ->
//          new ModBlock(BlockBehaviour.Properties.of(Material.).strength(f)));
//    public static final RegistryObject<Block>  = MaterialExt.BLOCKS.register("", () ->
//          new ModBlock(BlockBehaviour.Properties.of(Material.).strength(f)));
//    public static final RegistryObject<Block>  = MaterialExt.BLOCKS.register("", () ->
//          new ModBlock(BlockBehaviour.Properties.of(Material.).strength(f)));
//    public static final RegistryObject<Block>  = MaterialExt.BLOCKS.register("", () ->
//          new ModBlock(BlockBehaviour.Properties.of(Material.).strength(f)));
//    public static final RegistryObject<Block>  = MaterialExt.BLOCKS.register("", () ->
//          new ModBlock(BlockBehaviour.Properties.of(Material.).strength(f)));

    public static void initStatic()
    {
        // just to initialize static members
    }
}
