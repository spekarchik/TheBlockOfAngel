package com.pekar.angelblock.tools;

import com.pekar.angelblock.tooltip.ITooltipProvider;
import com.pekar.angelblock.utils.Utils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

import java.util.List;

public abstract class ModTool extends Item implements IModTool, ITooltipProvider
{
    protected final Utils utils = new Utils();

    public ModTool(ModToolMaterial material, TagKey<Block> mineableBlocks, float attackDamage, float attackSpeed, Properties properties)
    {
        super(material.getVanillaMaterial().applyToolProperties(material.isFireResistant() ? properties.fireResistant() : properties, mineableBlocks, attackDamage, attackSpeed));
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

    @Override
    public final void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {
        ITooltipProvider.appendHoverText(this, stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    public MutableComponent getDisplayName(int lineNumber)
    {
        return Component.translatable(getDescriptionId() + ".desc" + lineNumber);
    }
}
