package com.pekar.angelblock.events;

import com.pekar.angelblock.Main;
import com.pekar.angelblock.potions.PotionRegistry;
import com.pekar.angelblock.potions.PotionUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID, value = Dist.CLIENT)
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
        if (itemStack.getItem() == Items.SPLASH_POTION)
        {
            var potion = PotionUtils.getPotion(itemStack);
            var effects = potion.getEffects();
            if (!effects.isEmpty() &&
                    effects.stream().anyMatch(x -> x.getEffect().getDescriptionId().equals(PotionRegistry.BLOCK_BREAKER_EFFECT.get().getDescriptionId())))
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
        var id = PotionRegistry.BLOCK_BREAKER_EFFECT.get().getDescriptionId() + ".desc" + lineNumber;
        var component = Component.translatable(id).withStyle(ChatFormatting.GRAY);
        if (isSubtitleFormatting)
            component = component.withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.WHITE);
        return component;
    }
}
