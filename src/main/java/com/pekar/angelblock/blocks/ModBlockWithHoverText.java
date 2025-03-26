package com.pekar.angelblock.blocks;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ModBlockWithHoverText extends ModBlock
{
    public ModBlockWithHoverText(Properties properties)
    {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> components, TooltipFlag tooltipFlag)
    {
        components.add(getDisplayName().withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
    }

    protected MutableComponent getDescription(boolean isHeader, boolean isSubHeader, boolean isNotice, boolean isImportantNotice)
    {
        var component = getDisplayName();
        return utils.text.getFormattedTextComponent(component, isHeader, isSubHeader, isNotice, isImportantNotice, false);
    }

    protected MutableComponent getDisplayName()
    {
        return Component.translatable(asItem().getDescriptionId() + ".desc");
    }
}
