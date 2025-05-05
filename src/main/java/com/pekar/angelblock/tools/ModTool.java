package com.pekar.angelblock.tools;

import com.pekar.angelblock.tooltip.ITooltipProvider;
import com.pekar.angelblock.utils.Utils;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.Block;

import java.util.function.Consumer;

public abstract class ModTool extends Item implements IModTool
{
    protected final Utils utils = new Utils();

    public ModTool(ModToolMaterial material, TagKey<Block> mineableBlocks, float attackDamage, float attackSpeed, Properties properties)
    {
        super(material.getVanillaMaterial().applyToolProperties(properties, mineableBlocks, attackDamage, attackSpeed, /*disableBlockingForSeconds*/ 0));
        // ToolMaterial#applyToolProperties now takes in a boolean of whether the weapon can disable a blocker (e.g., shield)
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
