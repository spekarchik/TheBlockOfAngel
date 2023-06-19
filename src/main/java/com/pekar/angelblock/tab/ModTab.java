package com.pekar.angelblock.tab;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collection;

import static com.pekar.angelblock.Main.CREATIVE_MODE_TABS;

public abstract class ModTab
{
    protected abstract String getTabName();

    protected abstract RegistryObject<Item> getIconItem();

    protected abstract Collection<RegistryObject<Item>> getTabItems();

    protected abstract ResourceKey<CreativeModeTab>[] getTabsBefore();

    protected String getTitle()
    {
        return "itemGroup." + getTabName();
    }

    public final RegistryObject<CreativeModeTab> createTab()
    {
        return CREATIVE_MODE_TABS.register(getTabName(), () -> CreativeModeTab.builder()
                .withTabsBefore(getTabsBefore())
                .title(Component.translatable(getTitle()))
                .icon(() -> getIconItem().get().getDefaultInstance())
                .displayItems(this::addItems).build());
    }

    private void addItems(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output)
    {
        for (var item : getTabItems())
        {
            output.accept(item.get());
        }
    }
}
