package com.pekar.angelblock.tools;

import com.pekar.angelblock.utils.Utils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public abstract class ModTool extends Item implements IModTool
{
    protected final Utils utils = new Utils();

    public ModTool(ModToolMaterial material, TagKey<Block> mineableBlocks, float attackDamage, float attackSpeed, Properties properties)
    {
        super(material.getVanillaMaterial().applyToolProperties(properties, mineableBlocks, attackDamage, attackSpeed));
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
    public MutableComponent getDisplayName(int lineNumber)
    {
        return Component.translatable(getDescriptionId() + ".desc" + lineNumber);
    }
}
