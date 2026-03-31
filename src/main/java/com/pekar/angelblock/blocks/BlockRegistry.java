package com.pekar.angelblock.blocks;

import com.pekar.angelblock.Main;
import com.pekar.angelblock.blocks.block_items.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.pekar.angelblock.utils.Resources.createResourceLocation;

public class BlockRegistry
{
    public static final TagKey<Block> REPLACEABLE_BY_PLANTER = TagKey.create(Registries.BLOCK, createResourceLocation(Main.MODID, "planter_replaceables"));
    public static final TagKey<Block> TRACK_LAYER_COMPATIBLE = TagKey.create(Registries.BLOCK, createResourceLocation(Main.MODID, "track_layer_compatible"));

    public static final DeferredBlock<Block> CRACKED_ENDSTONE = register("cracked_endstone_block", CrackedBlock::new, ModBlockItemWithHoverText::new,
            BlockBehaviour.Properties.of().strength(0.5f).sound(SoundType.NETHER_BRICKS));
    public static final DeferredBlock<Block> CRACKED_OBSIDIAN = register("cracked_obsidian_block", CrackedBlock::new, ModBlockItemWithHoverText::new,
            BlockBehaviour.Properties.of().strength(10f).sound(SoundType.BONE_BLOCK).requiresCorrectToolForDrops());
    public static final DeferredBlock<Block> DIAMOND_POWDER_BLOCK = register("diamond_powder_block", ModBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.SAND).strength(0.7f, 9f).sound(SoundType.SNOW));
    public static final DeferredBlock<Block> OBSIDIAN_POWDER_BLOCK = register("obsidian_powder_block", ModBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.SAND).strength(0.7f, 9f));
    public static final DeferredBlock<Block> ENDSTONE_POWDER_BLOCK = register("endstone_powder_block", ModBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.SAND).strength(0.7f, 9f).sound(SoundType.SNOW));
    public static final DeferredBlock<Block> SALTPETER_BLOCK = register("saltpeter_block", ModBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.SAND).strength(0.7f, 9f).sound(SoundType.SNOW));
    public static final DeferredBlock<Block> DIAMITHIC_MATERIAL_BLOCK = register("diamithic_material_block", ModBlock::new,
            BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(0.7f, 9f), true);
    public static final DeferredBlock<Block> RENDELITHIC_MATERIAL_BLOCK = register("rendelithic_material_block", ModBlock::new,
            BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(0.7f, 9f), true);
    public static final DeferredBlock<Block> LIMONITE_MATERIAL_BLOCK = register("limonite_material_block", ModBlock::new,
            BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(0.7f, 9f));
    public static final DeferredBlock<Block> LAPIS_MATERIAL_BLOCK = register("lapis_material_block", ModBlock::new,
            BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(0.7f, 9f));
    public static final DeferredBlock<Block> SUPER_MATERIAL_BLOCK = register("super_material_block", ModBlock::new,
            BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(0.7f, 9f), true);
    public static final DeferredBlock<Block> FLYING_MATERIAL_BLOCK = register("flying_material_block", ModBlock::new,
            BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(0.7f, 9f));

    public static final DeferredBlock<Block> INACTIVE_ANGEL_BLOCK = register("inactive_angel_block", InactiveAngelBlock::new, InactiveAngelBlockItem::new,
            BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(1.5F, 1200F));
    public static final DeferredBlock<Block> ANGEL_BLOCK = register("angel_block", AngelBlock::new, AngelBlockItem::new,
            BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(1.5F, 1200F)
                    .lightLevel(state -> 15));
    public static final DeferredBlock<Block> DEVIL_BLOCK = register("devil_block", DevilBlock::new, DevilBlockItem::new,
            BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(10F, 1200F).lightLevel(blockState -> 10));
    public static final DeferredBlock<Block> ANGEL_ROD_BLOCK = registerSkipTab("angel_rod_block", AngelRodBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.LIGHTNING_ROD).strength(0.1F, 1200F).sound(SoundType.COPPER)
                    .lightLevel(state -> 15));

    public static final DeferredBlock<Block> GREEN_DIAMOND_ORE = register("green_diamond_ore", GreenDiamondBlock::new, GreenDiamondOreBlockItem::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE).strength(1F).lightLevel(state -> 6)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops());
    public static final DeferredBlock<Block> GUNPOWDER_BLOCK = register("gunpowder_block", GunpowderBlock::new, GunpowderBlockItem::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.SAND).sound(SoundType.SNOW).strength(0.2F));
    public static final DeferredBlock<Block> NETHER_BARS = register("nether_bars_block", NetherBarsBlock::new, NetherBarsBlockItem::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).strength(10F, 1200F).requiresCorrectToolForDrops());

    // Internal blocks (not added to Creative Tab)
    //public static final DeferredBlock<Block> DESTROYING_DIAMOND_BLOCK = registerSkipTab("destroying_diamond_block", AirBlock::new);
    public static final DeferredBlock<Block> DESTROYING_DIAMOND_POWDER_BLOCK = registerSkipTab("destroying_diamond_powder_block", AirBlock::new);
    public static final DeferredBlock<Block> DESTROYING_PRISMARINE_SHARD_BLOCK = registerSkipTab("destroying_prismarine_shard_block", AirBlock::new);
    public static final DeferredBlock<Block> DESTROYING_PRISMARINE_CRYSTALS = registerSkipTab("destroying_prismarine_crystals", AirBlock::new);
    public static final DeferredBlock<Block> DESTROYING_WHITE_WOOL_BY_POTION = registerSkipTab("destroying_white_wool_by_potion", AirBlock::new);
    public static final DeferredBlock<Block> DESTROYING_BLAZE_POWDER = registerSkipTab("destroying_blaze_powder", AirBlock::new);
    public static final DeferredBlock<Block> DESTROYING_WHITE_WOOL_BY_ROD = registerSkipTab("destroying_white_wool_by_rod", AirBlock::new);
    public static final DeferredBlock<Block> DESTROYING_ORANGE_WOOL = registerSkipTab("destroying_orange_wool", AirBlock::new);
    public static final DeferredBlock<Block> DESTROYING_MAGENTA_WOOL = registerSkipTab("destroying_magenta_wool", AirBlock::new);
    public static final DeferredBlock<Block> DESTROYING_LIGHT_BLUE_WOOL = registerSkipTab("destroying_light_blue_wool", AirBlock::new);
    public static final DeferredBlock<Block> DESTROYING_YELLOW_WOOL = registerSkipTab("destroying_yellow_wool", AirBlock::new);
    public static final DeferredBlock<Block> DESTROYING_LIME_WOOL = registerSkipTab("destroying_lime_wool", AirBlock::new);
    public static final DeferredBlock<Block> DESTROYING_PINK_WOOL = registerSkipTab("destroying_pink_wool", AirBlock::new);
    public static final DeferredBlock<Block> DESTROYING_GRAY_WOOL = registerSkipTab("destroying_gray_wool", AirBlock::new);
    public static final DeferredBlock<Block> DESTROYING_LIGHT_GRAY_WOOL = registerSkipTab("destroying_light_gray_wool", AirBlock::new);
    public static final DeferredBlock<Block> DESTROYING_CYAN_WOOL = registerSkipTab("destroying_cyan_wool", AirBlock::new);
    public static final DeferredBlock<Block> DESTROYING_PURPLE_WOOL = registerSkipTab("destroying_purple_wool", AirBlock::new);
    public static final DeferredBlock<Block> DESTROYING_BLUE_WOOL = registerSkipTab("destroying_blue_wool", AirBlock::new);
    public static final DeferredBlock<Block> DESTROYING_BROWN_WOOL = registerSkipTab("destroying_brown_wool", AirBlock::new);
    public static final DeferredBlock<Block> DESTROYING_GREEN_WOOL = registerSkipTab("destroying_green_wool", AirBlock::new);
    public static final DeferredBlock<Block> DESTROYING_RED_WOOL = registerSkipTab("destroying_red_wool", AirBlock::new);
    public static final DeferredBlock<Block> DESTROYING_BLACK_WOOL = registerSkipTab("destroying_black_wool", AirBlock::new);
    //public static final DeferredBlock<Block> DESTROYING_RAW_IRON = registerSkipTab("destroying_raw_iron", AirBlock::new);
    public static final DeferredBlock<Block> DESTROYING_SALTPETER = registerSkipTab("destroying_saltpeter", AirBlock::new);

    public static void initStatic()
    {
        // just to initialize static members
    }

    private static <T extends Block> DeferredBlock<T> register(String name, Supplier<T> blockSupplier)
    {
        var blockObject = Main.BLOCKS.register(name, blockSupplier);
        Main.ITEMS.registerItem(name, p -> new ModBlockItem(blockObject.get(), p));
        return blockObject;
    }

    private static <T extends Block> DeferredBlock<T> register(String name, Function<BlockBehaviour.Properties, T> blockSupplier)
    {
        var blockObject = Main.BLOCKS.registerBlock(name, blockSupplier);
        Main.ITEMS.registerItem(name, p -> new ModBlockItem(blockObject.get(), p));
        return blockObject;
    }

    private static <T extends Block> DeferredBlock<T> register(String name, Function<BlockBehaviour.Properties, T> blockSupplier, BlockBehaviour.Properties properties)
    {
        var blockObject = Main.BLOCKS.registerBlock(name, blockSupplier, properties);
        Main.ITEMS.registerItem(name, p -> new ModBlockItem(blockObject.get(), p));
        return blockObject;
    }

    private static <T extends Block> DeferredBlock<T> register(String name, Function<BlockBehaviour.Properties, T> blockSupplier, BlockBehaviour.Properties properties, boolean isFireResistant)
    {
        var blockObject = Main.BLOCKS.registerBlock(name, blockSupplier, properties);
        Main.ITEMS.registerItem(name, p -> new ModBlockItem(blockObject.get(), isFireResistant ? p.fireResistant() : p));
        return blockObject;
    }

    private static <T extends Block> DeferredBlock<T> register(String name, Function<BlockBehaviour.Properties, T> blockSupplier, BiFunction<Block, Item.Properties, ? extends ModBlockItem> blockItemSupplier, BlockBehaviour.Properties blockProperties)
    {
        var blockObject = Main.BLOCKS.registerBlock(name, blockSupplier, blockProperties);
        Main.ITEMS.registerItem(name, p -> blockItemSupplier.apply(blockObject.get(), p));
        return blockObject;
    }

    private static <T extends Block> DeferredBlock<T> registerSkipTab(String name, Supplier<T> supplier)
    {
        return Main.BLOCKS.register(name, supplier);
    }

    private static <T extends Block> DeferredBlock<T> registerSkipTab(String name, Function<BlockBehaviour.Properties, T> supplier)
    {
        return Main.BLOCKS.registerBlock(name, supplier);
    }

    private static <T extends Block> DeferredBlock<T> registerSkipTab(String name, Function<BlockBehaviour.Properties, T> supplier, BlockBehaviour.Properties properties)
    {
        return Main.BLOCKS.registerBlock(name, supplier, properties);
    }
}
