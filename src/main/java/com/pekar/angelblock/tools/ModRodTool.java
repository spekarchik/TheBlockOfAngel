package com.pekar.angelblock.tools;

import com.pekar.angelblock.utils.Utils;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.level.block.Block;

public abstract class ModRodTool extends DiggerItem implements IModTool
{
    protected final Utils utils = new Utils();

    public ModRodTool(ModToolMaterial material, TagKey<Block> mineableBlocks, int attackDamage, float attackSpeed, Properties properties)
    {
        super(material.getVanillaMaterial(), mineableBlocks, attackDamage, attackSpeed, properties);
    }

    @Override
    public IModTool getTool()
    {
        return this;
    }
}
