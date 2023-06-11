package com.pekar.angelblock.blocks;

import com.pekar.angelblock.Main;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;
import java.util.function.Supplier;

public class BlockRegistry
{
    public static final RegistryObject<Block> CRACKED_ENDSTONE = register("cracked_endstone_block", CrackedEndStoneBlock::new);
    public static final RegistryObject<Block> CRACKED_OBSIDIAN = register("cracked_obsidian_block", CrackedObsidianBlock::new);
    public static final RegistryObject<Block> DIAMOND_POWDER_BLOCK = register("diamond_powder_block", () ->
            new Block(BlockBehaviour.Properties.copy(Blocks.SAND).strength(0.7f, 9f).sound(SoundType.SNOW)));
    public static final RegistryObject<Block> OBSIDIAN_POWDER_BLOCK = register("obsidian_powder_block", () ->
            new Block(BlockBehaviour.Properties.copy(Blocks.SAND).strength(0.7f, 9f)));
    public static final RegistryObject<Block> ENDSTONE_POWDER_BLOCK = register("endstone_powder_block", () ->
            new Block(BlockBehaviour.Properties.copy(Blocks.SAND).strength(0.7f, 9f).sound(SoundType.SNOW)));
    public static final RegistryObject<Block> DIAMITHIC_MATERIAL_BLOCK = register("diamithic_material_block", () ->
            new Block(BlockBehaviour.Properties.copy(Blocks.STONE).strength(0.7f, 9f)));
    public static final RegistryObject<Block> RENDELITHIC_MATERIAL_BLOCK = register("rendelithic_material_block", () ->
            new Block(BlockBehaviour.Properties.copy(Blocks.STONE).strength(0.7f, 9f)));
    public static final RegistryObject<Block> LIMONITE_MATERIAL_BLOCK = register("limonite_material_block", () ->
            new Block(BlockBehaviour.Properties.copy(Blocks.STONE).strength(0.7f, 9f)));
    public static final RegistryObject<Block> LAPIS_MATERIAL_BLOCK = register("lapis_material_block", () ->
            new Block(BlockBehaviour.Properties.copy(Blocks.STONE).strength(0.7f, 9f)));
    public static final RegistryObject<Block> SUPER_MATERIAL_BLOCK = register("super_material_block", () ->
            new Block(BlockBehaviour.Properties.copy(Blocks.STONE).strength(0.7f, 9f)));
    public static final RegistryObject<Block> FLYING_MATERIAL_BLOCK = register("flying_material_block", () ->
            new Block(BlockBehaviour.Properties.copy(Blocks.STONE).strength(0.7f, 9f)));

    public static final RegistryObject<Block> ANGEL_BLOCK = register("angel_block", AngelBlock::new);
    public static final RegistryObject<Block> DEVIL_BLOCK = register("devil_block", DevilBlock::new);

    public static final RegistryObject<Block> GREEN_DIAMOND_ORE = register("green_diamond_ore", GreenDiamondBlock::new);
    public static final RegistryObject<Block> GUNPOWDER_BLOCK = register("gunpowder_block", GunpowderBlock::new);

    // Internal blocks (not added to Creative Tab)
    public static final RegistryObject<Block> DESTROYING_DIAMOND_BLOCK = registerSkipTab("destroying_diamond_block", AirBlock::new);
    public static final RegistryObject<Block> DESTROYING_DIAMOND_POWDER_BLOCK = registerSkipTab("destroying_diamond_powder_block", AirBlock::new);
    public static final RegistryObject<Block> DESTROYING_PRISMARINE_SHARD_BLOCK = registerSkipTab("destroying_prismarine_shard_block", AirBlock::new);
    public static final RegistryObject<Block> DESTROYING_PRISMARINE_CRYSTALS = registerSkipTab("destroying_prismarine_crystals", AirBlock::new);
    public static final RegistryObject<Block> DESTROYING_WHITE_WOOL_BY_POTION = registerSkipTab("destroying_white_wool_by_potion", AirBlock::new);
    public static final RegistryObject<Block> DESTROYING_BLAZE_POWDER = registerSkipTab("destroying_blaze_powder", AirBlock::new);
    public static final RegistryObject<Block> DESTROYING_WHITE_WOOL_BY_ROD = registerSkipTab("destroying_white_wool_by_rod", AirBlock::new);
    public static final RegistryObject<Block> DESTROYING_ORANGE_WOOL = registerSkipTab("destroying_orange_wool", AirBlock::new);
    public static final RegistryObject<Block> DESTROYING_MAGENTA_WOOL = registerSkipTab("destroying_magenta_wool", AirBlock::new);
    public static final RegistryObject<Block> DESTROYING_LIGHT_BLUE_WOOL = registerSkipTab("destroying_light_blue_wool", AirBlock::new);
    public static final RegistryObject<Block> DESTROYING_YELLOW_WOOL = registerSkipTab("destroying_yellow_wool", AirBlock::new);
    public static final RegistryObject<Block> DESTROYING_LIME_WOOL = registerSkipTab("destroying_lime_wool", AirBlock::new);
    public static final RegistryObject<Block> DESTROYING_PINK_WOOL = registerSkipTab("destroying_pink_wool", AirBlock::new);
    public static final RegistryObject<Block> DESTROYING_GRAY_WOOL = registerSkipTab("destroying_gray_wool", AirBlock::new);
    public static final RegistryObject<Block> DESTROYING_LIGHT_GRAY_WOOL = registerSkipTab("destroying_light_gray_wool", AirBlock::new);
    public static final RegistryObject<Block> DESTROYING_CYAN_WOOL = registerSkipTab("destroying_cyan_wool", AirBlock::new);
    public static final RegistryObject<Block> DESTROYING_PURPLE_WOOL = registerSkipTab("destroying_purple_wool", AirBlock::new);
    public static final RegistryObject<Block> DESTROYING_BLUE_WOOL = registerSkipTab("destroying_blue_wool", AirBlock::new);
    public static final RegistryObject<Block> DESTROYING_BROWN_WOOL = registerSkipTab("destroying_brown_wool", AirBlock::new);
    public static final RegistryObject<Block> DESTROYING_GREEN_WOOL = registerSkipTab("destroying_green_wool", AirBlock::new);
    public static final RegistryObject<Block> DESTROYING_RED_WOOL = registerSkipTab("destroying_red_wool", AirBlock::new);
    public static final RegistryObject<Block> DESTROYING_BLACK_WOOL = registerSkipTab("destroying_black_wool", AirBlock::new);
    public static final RegistryObject<Block> DESTROYING_RAW_IRON = registerSkipTab("destroying_raw_iron", AirBlock::new);
    public static final RegistryObject<Block> DESTROYING_GUNPOWDER = registerSkipTab("destroying_gunpowder", AirBlock::new);

    public static void initStatic()
    {
        // just to initialize static members
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> supplier)
    {
        var blockObject = Main.BLOCKS.register(name, supplier);
        Main.ITEMS.register(name, () -> new ModBlockItem(blockObject.get() /*, true */));
        return blockObject;
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, Function<Block, ? extends ModBlockItem> blockItemSupplier)
    {
        var blockObject = Main.BLOCKS.register(name, blockSupplier);
        Main.ITEMS.register(name, () -> blockItemSupplier.apply(blockObject.get()));
        return blockObject;
    }

    private static <T extends Block> RegistryObject<T> registerSkipTab(String name, Supplier<T> supplier)
    {
        var blockObject = Main.BLOCKS.register(name, supplier);
        //Main.ITEMS.register(name, () -> new ModBlockItem(blockObject.get(), false));  TODO: check if we can not to register item to avoid adding it to creative tab
        return blockObject;
    }
}
