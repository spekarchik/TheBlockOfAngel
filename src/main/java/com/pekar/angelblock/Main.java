package com.pekar.angelblock;

import com.pekar.angelblock.armor.ArmorRegistry;
import com.pekar.angelblock.blocks.BlockRegistry;
import com.pekar.angelblock.blocks.tile_entities.EntityRegistry;
import com.pekar.angelblock.events.*;
import com.pekar.angelblock.items.ItemRegistry;
import com.pekar.angelblock.menus.MenuRegistry;
import com.pekar.angelblock.potions.PotionRegistry;
import com.pekar.angelblock.recipe.RecipeRegistry;
import com.pekar.angelblock.tab.MainTab;
import com.pekar.angelblock.tools.ToolRegistry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Main.MODID)
public class Main
{
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "angelblock";
    public static final String MODNAME = "Angel Block Mod";

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MODID);
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, MODID);
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(Registries.POTION, MODID);
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, MODID);

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ANGEL_BLOCK_TAB = new MainTab().createTab();

    public Main(IEventBus modEventBus, ModContainer modContainer)
    {
        initializeRegistry();

        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        BLOCK_ENTITIES.register(modEventBus);
        MOB_EFFECTS.register(modEventBus);
        POTIONS.register(modEventBus);

        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        NeoForge.EVENT_BUS.register(this);

        EventRegistry.registerEvents();
        EventRegistry.registerEventsOnModBus(modEventBus);
        ModDataComponents.register(modEventBus);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register the setup method for modloading
        modEventBus.addListener(this::commonSetup);
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

        RecipeRegistry.initStatic();
        MenuRegistry.initStatic();
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // some preinit code
//        LOGGER.info("*********************HELLO FROM PREINIT**********************");
//        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // do something when the server starts
//        LOGGER.info("HELLO from server starting");
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == ANGEL_BLOCK_TAB.getKey())
        {
            var potionItemStack = new ItemStack(Items.SPLASH_POTION);
            potionItemStack.set(DataComponents.POTION_CONTENTS, new PotionContents(PotionRegistry.BLOCK_BREAKER_POTION));
            event.accept(potionItemStack);
        }
    }
}
