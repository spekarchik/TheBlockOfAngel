package com.pekar.angelblock.items;

import com.pekar.angelblock.TextStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ModItemWithHoverText extends ModItem
{
    private final TextStyle descriptionStyle;

    public ModItemWithHoverText()
    {
        this(TextStyle.Regular);
    }

    public ModItemWithHoverText(TextStyle descriptionStyle)
    {
        this.descriptionStyle = descriptionStyle;
    }


    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag tooltipFlag)
    {
        var component = utils.text.getDescription(getDisplayName(), descriptionStyle);
        components.add(component);
    }

    protected MutableComponent getDisplayName()
    {
        return Component.translatable(this.getDescriptionId() + ".desc");
    }
}
