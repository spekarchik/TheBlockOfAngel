package com.pekar.angelblock;

import com.pekar.angelblock.armor.ArmorRegistry;
import com.pekar.angelblock.blocks.BlockRegistry;
import com.pekar.angelblock.blocks.tile_entities.EntityRegistry;
import com.pekar.angelblock.events.*;
import com.pekar.angelblock.items.ItemRegistry;
import com.pekar.angelblock.network.PacketRegistry;
import com.pekar.angelblock.potions.PotionRegistry;
import com.pekar.angelblock.recipe.RecipeRegistry;
import com.pekar.angelblock.tab.MainTab;
import com.pekar.angelblock.tools.ToolRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Main.MODID)
public class Main
{
    // Directly reference a log4j logger.
//    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "angelblock";
    public static final String MODNAME = "Angel Block Mod";

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MODID);
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, MODID);

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // Creates a creative tab with the id "examplemod:example_tab" for the example item, that is placed after the combat tab
    public static final RegistryObject<CreativeModeTab> ANGEL_BLOCK_TAB = new MainTab().createTab();

    public Main()
    {
        initializeRegistry();

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        EventRegistry.registerEvents();

        var modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        BLOCK_ENTITIES.register(modEventBus);
        MOB_EFFECTS.register(modEventBus);
        POTIONS.register(modEventBus);

        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register the setup method for modloading
        modEventBus.addListener(this::commonSetup);

        //modEventBus.addListener(this::clientSetup);
    }

    private void initializeRegistry()
    {
        BlockRegistry.initStatic();
        ItemRegistry.initStatic();
        EntityRegistry.initStatic();
        ArmorRegistry.initStatic();
        ToolRegistry.initStatic();
        PotionRegistry.initStatic();

        ClientSetupEvents.initStatic();
        KeyboardMouseEvents.initStatic();
        ClientTickEvents.initStatic();
        GuiEvents.initStatic();

        PacketRegistry.init();
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        RecipeRegistry.init();
//        LOGGER.info("*********************HELLO FROM PREINIT**********************");
//        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
        //InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
        //LOGGER.info("Got IMC {}", event.getIMCStream().
        //        map(m->m.messageSupplier().get()).
        //        collect(Collectors.toList()));
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    //@SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // do something when the server starts
//        LOGGER.info("HELLO from server starting");
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == ANGEL_BLOCK_TAB.getKey())
            event.accept(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), PotionRegistry.BLOCK_BREAKER_POTION.get()));
    }
}
