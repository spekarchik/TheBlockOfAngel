package com.pekar.angelblock.blocks;

import com.pekar.angelblock.Main;
import net.minecraft.world.level.block.Block;
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
    public static final RegistryObject<Block> FLYING_MATERIAL_BLOCK = Main.BLOCKS.register("flying_material_block", () ->
            new Block(BlockBehaviour.Properties.of(Material.STONE).strength(0.7f, 9f)));

    public static final RegistryObject<Block> ANGEL_BLOCK = Main.BLOCKS.register("angel_block", AngelBlock::new);
    public static final RegistryObject<Block> DEVIL_BLOCK = Main.BLOCKS.register("devil_block", DevilBlock::new);

    public static final RegistryObject<Block> GREEN_DIAMOND_ORE = Main.BLOCKS.register("green_diamond_ore", GreenDiamondBlock::new);
    public static final RegistryObject<Block> GUNPOWDER_BLOCK = Main.BLOCKS.register("gunpowder_block", GunpowderBlock::new);

    // Internal blocks. Material should be AIR not to add it to creative tab
    public static final RegistryObject<Block> DESTROYING_DIAMOND_BLOCK = Main.BLOCKS.register("destroying_diamond_block", () ->
            new Block(BlockBehaviour.Properties.of(Material.AIR)));

    public static final RegistryObject<Block> DESTROYING_DIAMOND_POWDER_BLOCK = Main.BLOCKS.register("destroying_diamond_powder_block", () ->
            new Block(BlockBehaviour.Properties.of(Material.AIR)));

    public static final RegistryObject<Block> DESTROYING_PRISMARINE_SHARD_BLOCK = Main.BLOCKS.register("destroying_prismarine_shard_block", () ->
            new Block(BlockBehaviour.Properties.of(Material.AIR)));

    public static final RegistryObject<Block> DESTROYING_PRISMARINE_CRYSTALS = Main.BLOCKS.register("destroying_prismarine_crystals", () ->
            new Block(BlockBehaviour.Properties.of(Material.AIR)));

    public static final RegistryObject<Block> DESTROYING_WHITE_WOOL_BY_POTION = Main.BLOCKS.register("destroying_white_wool_by_potion", () ->
            new Block(BlockBehaviour.Properties.of(Material.AIR)));

    public static final RegistryObject<Block> DESTROYING_BLAZE_POWDER = Main.BLOCKS.register("destroying_blaze_powder", () ->
            new Block(BlockBehaviour.Properties.of(Material.AIR)));

    public static final RegistryObject<Block> DESTROYING_WHITE_WOOL_BY_ROD = Main.BLOCKS.register("destroying_white_wool_by_rod", () ->
            new Block(BlockBehaviour.Properties.of(Material.AIR)));

    public static final RegistryObject<Block> DESTROYING_ORANGE_WOOL = Main.BLOCKS.register("destroying_orange_wool", () ->
            new Block(BlockBehaviour.Properties.of(Material.AIR)));

    public static final RegistryObject<Block> DESTROYING_MAGENTA_WOOL = Main.BLOCKS.register("destroying_magenta_wool", () ->
            new Block(BlockBehaviour.Properties.of(Material.AIR)));

    public static final RegistryObject<Block> DESTROYING_LIGHT_BLUE_WOOL = Main.BLOCKS.register("destroying_light_blue_wool", () ->
            new Block(BlockBehaviour.Properties.of(Material.AIR)));

    public static final RegistryObject<Block> DESTROYING_YELLOW_WOOL = Main.BLOCKS.register("destroying_yellow_wool", () ->
            new Block(BlockBehaviour.Properties.of(Material.AIR)));

    public static final RegistryObject<Block> DESTROYING_LIME_WOOL = Main.BLOCKS.register("destroying_lime_wool", () ->
            new Block(BlockBehaviour.Properties.of(Material.AIR)));

    public static final RegistryObject<Block> DESTROYING_PINK_WOOL = Main.BLOCKS.register("destroying_pink_wool", () ->
            new Block(BlockBehaviour.Properties.of(Material.AIR)));

    public static final RegistryObject<Block> DESTROYING_GRAY_WOOL = Main.BLOCKS.register("destroying_gray_wool", () ->
            new Block(BlockBehaviour.Properties.of(Material.AIR)));

    public static final RegistryObject<Block> DESTROYING_LIGHT_GRAY_WOOL = Main.BLOCKS.register("destroying_light_gray_wool", () ->
            new Block(BlockBehaviour.Properties.of(Material.AIR)));

    public static final RegistryObject<Block> DESTROYING_CYAN_WOOL = Main.BLOCKS.register("destroying_cyan_wool", () ->
            new Block(BlockBehaviour.Properties.of(Material.AIR)));

    public static final RegistryObject<Block> DESTROYING_PURPLE_WOOL = Main.BLOCKS.register("destroying_purple_wool", () ->
            new Block(BlockBehaviour.Properties.of(Material.AIR)));

    public static final RegistryObject<Block> DESTROYING_BLUE_WOOL = Main.BLOCKS.register("destroying_blue_wool", () ->
            new Block(BlockBehaviour.Properties.of(Material.AIR)));

    public static final RegistryObject<Block> DESTROYING_BROWN_WOOL = Main.BLOCKS.register("destroying_brown_wool", () ->
            new Block(BlockBehaviour.Properties.of(Material.AIR)));

    public static final RegistryObject<Block> DESTROYING_GREEN_WOOL = Main.BLOCKS.register("destroying_green_wool", () ->
            new Block(BlockBehaviour.Properties.of(Material.AIR)));

    public static final RegistryObject<Block> DESTROYING_RED_WOOL = Main.BLOCKS.register("destroying_red_wool", () ->
            new Block(BlockBehaviour.Properties.of(Material.AIR)));

    public static final RegistryObject<Block> DESTROYING_BLACK_WOOL = Main.BLOCKS.register("destroying_black_wool", () ->
            new Block(BlockBehaviour.Properties.of(Material.AIR)));

    public static final RegistryObject<Block> DESTROYING_RAW_IRON = Main.BLOCKS.register("destroying_raw_iron", () ->
            new Block(BlockBehaviour.Properties.of(Material.AIR)));

    public static final RegistryObject<Block> DESTROYING_GUNPOWDER = Main.BLOCKS.register("destroying_gunpowder", () ->
            new Block(BlockBehaviour.Properties.of(Material.AIR)));

    public static void initStatic()
    {
        // just to initialize static members
    }
}
