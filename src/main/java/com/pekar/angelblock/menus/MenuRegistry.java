package com.pekar.angelblock.menus;

import com.pekar.angelblock.Main;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredHolder;

public class MenuRegistry
{
    public static final DeferredHolder<MenuType<?>, MenuType<CustomSmithingMenu>> CUSTOM_SMITHING_MENU =
            Main.MENUS.register("custom_smithing", () -> new MenuType<CustomSmithingMenu>(CustomSmithingMenu::new, FeatureFlags.DEFAULT_FLAGS));

    public static final DeferredHolder<MenuType<?>, MenuType<CustomCraftingMenu>> CUSTOM_CRAFTING_MENU =
            Main.MENUS.register("custom_crafting", () -> new MenuType<CustomCraftingMenu>(CustomCraftingMenu::new, FeatureFlags.DEFAULT_FLAGS));

    public static void initStatic()
    {
        // just to initialize static members
    }
}
