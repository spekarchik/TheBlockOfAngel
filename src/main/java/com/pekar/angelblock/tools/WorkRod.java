package com.pekar.angelblock.tools;

import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;

public abstract class WorkRod extends ModRod
{
    public WorkRod(ModToolMaterial material, TagKey<Block> mineableBlocks, Properties properties)
    {
        super(material, false, mineableBlocks, properties);
    }

    protected void causePlayerExhaustion(Player player)
    {
        utils.player.causePlayerExhaustion(player, 2);
    }
}
