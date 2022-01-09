package com.pekar.materialext;

import com.pekar.materialext.armor.ArmorRegistry;
import com.pekar.materialext.blocks.BlockRegistry;
import com.pekar.materialext.blocks.tile_entities.EntityRegistry;
import com.pekar.materialext.items.ItemRegistry;
import com.pekar.materialext.tab.MaterialExtTab;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MaterialExt.MODID)
public class MaterialExt
{
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "materialext";

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MODID);

    public MaterialExt()
    {
        // Register the setup method for modloading
        //FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);

        //FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);

        initialyzeRegistry();

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        var bus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(bus);
        ITEMS.register(bus);
        BLOCK_ENTITIES.register(bus);
    }

    private void initialyzeRegistry()
    {
        BlockRegistry.initStatic();
        ItemRegistry.initStatic();
        EntityRegistry.initStatic();
        ArmorRegistry.initStatic();
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        //LOGGER.info("HELLO FROM PREINIT");
        //LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
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
    public void onServerStarting(ServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents
    {
        //@SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent)
        {
            // register a new block here
            LOGGER.info("HELLO from Register Block");
        }

        //@SubscribeEvent
        public static void registerBlockEntities(RegistryEvent.Register<BlockEntityType<?>> event)
        {
        }

        @SubscribeEvent
        public static void onRegisterItems(final RegistryEvent.Register<Item> event)
        {
            var registry = event.getRegistry();
            BLOCKS.getEntries().stream()
                    .map(RegistryObject::get)
                    .forEach(block ->
                    {
                        final var prop = new Item.Properties().tab(MaterialExtTab.MATERIAL_EXT_TAB);
                        var blockItem = new BlockItem(block, prop);
                        blockItem.setRegistryName(block.getRegistryName());
                        registry.register(blockItem);
                    });
        }
    }
}
