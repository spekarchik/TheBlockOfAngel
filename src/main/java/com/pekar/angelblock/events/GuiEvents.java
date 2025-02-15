package com.pekar.angelblock.events;

import com.pekar.angelblock.Main;
import com.pekar.angelblock.potions.PotionRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

@EventBusSubscriber(modid = Main.MODID, value = Dist.CLIENT)
public class GuiEvents
{
    public static void initStatic()
    {
        // do nothing
    }

    @SubscribeEvent
    public static void onItemTooltipEvent(ItemTooltipEvent event)
    {
        var itemStack = event.getItemStack();
        var item = itemStack.getItem();
        if (item == Items.SPLASH_POTION)
        {
            var potionContents = itemStack.get(DataComponents.POTION_CONTENTS); // see PotionItem.appendHoverText()

            if (potionContents != null && potionContents.is(PotionRegistry.BLOCK_BREAKER_POTION))
            {
                var tooltip = event.getToolTip();
                for (int i = 1; i <= 17; i++)
                {
                    tooltip.add(createTextComponent(i, i == 1 || i == 11));
                }
            }
        }
    }

    // See: ItemStack.getTooltipLines() method
    private static Component createTextComponent(int lineNumber, boolean isSubtitleFormatting)
    {
        var id = "effect." + Main.MODID + "." + PotionRegistry.BLOCK_BREAKER_EFFECT.getKey().location().getPath() + ".desc" + lineNumber;
        var component = Component.translatable(id).withStyle(ChatFormatting.GRAY);
        if (isSubtitleFormatting)
            component = component.withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.WHITE);
        return component;
    }
}
