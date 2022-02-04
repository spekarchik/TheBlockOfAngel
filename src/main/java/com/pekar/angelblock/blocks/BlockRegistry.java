package com.pekar.angelblock.blocks;

import com.pekar.angelblock.Main;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.RegistryObject;

public class BlockRegistry
{
    public static final RegistryObject<Block> CRACKED_ENDSTONE = Main.BLOCKS.register("cracked_endstone_block", CrackedEndStoneBlock::new);
    public static final RegistryObject<Block> CRACKED_OBSIDIAN = Main.BLOCKS.register("cracked_obsidian_block", CrackedObsidianBlock::new);
    public static final RegistryObject<Block> DIAMOND_POWDER_BLOCK = Main.BLOCKS.register("diamond_powder_block", () ->
            new Block(BlockBehaviour.Properties.of(Material.SAND).strength(0.7f, 9f).sound(SoundType.SNOW)));
    public static final RegistryObject<Block> OBSIDIAN_POWDER_BLOCK = Main.BLOCKS.register("obsidian_powder_block", () ->
            new Block(BlockBehaviour.Properties.of(Material.SAND).strength(0.7f, 9f)));
    public static final RegistryObject<Block> ENDSTONE_POWDER_BLOCK = Main.BLOCKS.register("endstone_powder_block", () ->
            new Block(BlockBehaviour.Properties.of(Material.SAND).strength(0.7f, 9f).sound(SoundType.SNOW)));
    public static final RegistryObject<Block> DIAMITHIC_MATERIAL_BLOCK = Main.BLOCKS.register("diamithic_material_block", () ->
            new Block(BlockBehaviour.Properties.of(Material.STONE).strength(0.7f, 9f)));
    public static final RegistryObject<Block> RENDELITHIC_MATERIAL_BLOCK = Main.BLOCKS.register("rendelithic_material_block", () ->
            new Block(BlockBehaviour.Properties.of(Material.STONE).strength(0.7f, 9f)));
    public static final RegistryObject<Block> LIMONITE_MATERIAL_BLOCK = Main.BLOCKS.register("limonite_material_block", () ->
            new Block(BlockBehaviour.Properties.of(Material.STONE).strength(0.7f, 9f)));
    public static final RegistryObject<Block> LAPIS_MATERIAL_BLOCK = Main.BLOCKS.register("lapis_material_block", () ->
            new Block(BlockBehaviour.Properties.of(Material.STONE).strength(0.7f, 9f)));
    public static final RegistryObject<Block> SUPER_MATERIAL_BLOCK = Main.BLOCKS.register("super_material_block", () ->
            new Block(BlockBehaviour.Properties.of(Material.STONE).strength(0.7f, 9f)));

    public static final RegistryObject<Block> GUNPOWDER_BLOCK = Main.BLOCKS.register("gunpowder_block", GunpowderBlock::new);

    public static final RegistryObject<Block> DESTROYING_DIAMOND_BLOCK = Main.BLOCKS.register("destroying_diamond_block", () ->
            new Block(BlockBehaviour.Properties.of(Material.AIR)));

    public static final RegistryObject<Block> ANGEL_BLOCK = Main.BLOCKS.register("angel_block", AngelBlock::new);
    public static final RegistryObject<Block> DEVIL_BLOCK = Main.BLOCKS.register("devil_block", DevilBlock::new);

    public static final RegistryObject<Block> GREEN_DIAMOND_ORE = Main.BLOCKS.register("green_diamond_ore", GreenDiamondBlock::new);

    public static void initStatic()
    {
        // just to initialize static members
    }
}
