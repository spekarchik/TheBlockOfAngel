package com.pekar.angelblock.tools;

import com.pekar.angelblock.tooltip.ITooltipProvider;
import com.pekar.angelblock.utils.Utils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public abstract class ModTool extends Item implements IModTool
{
    protected final Utils utils = new Utils();

    public ModTool(ModToolMaterial material, Properties properties)
    {
        super(material.isFireResistant() ? properties.fireResistant() : properties);
    }

    @Override
    public final void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> component, TooltipFlag flag)
    {
        ITooltipProvider.appendHoverText(this, stack, context, display, component, flag);
    }

    @Override
    public String getMaterialName()
    {
        if (getMaterial() instanceof ModToolMaterial toolMaterial)
        {
            return toolMaterial.getName();
        }

        return "";
    }
}
