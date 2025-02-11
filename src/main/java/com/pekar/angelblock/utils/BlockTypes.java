package com.pekar.angelblock.utils;

import com.pekar.angelblock.blocks.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;

public class BlockTypes
{
    BlockTypes()
    {

    }

    public boolean isOre(Block block)
    {
        return block instanceof DropExperienceBlock || block instanceof RedStoneOreBlock || block == Blocks.ANCIENT_DEBRIS
                || block == BlockRegistry.GREEN_DIAMOND_ORE.get();
    }

    public boolean isLiquid(Block block)
    {
        return block instanceof LiquidBlock;
    }

    public boolean isGlassBlock(Block block)
    {
        return block == Blocks.GLASS || block == Blocks.TINTED_GLASS || block instanceof StainedGlassBlock;
    }

    public boolean isSandBlock(Block block)
    {
        return block == Blocks.SAND || block == Blocks.RED_SAND;
    }

    public boolean isFarmTypeBlock(Level level, BlockPos pos)
    {
        var block = level.getBlockState(pos).getBlock();
        return block == Blocks.FARMLAND || canBeFarmland(block) || isSandBlock(block)
                || block == Blocks.GRAVEL || level.isWaterAt(pos);
    }

    public boolean isRail(Block block)
    {
        return block instanceof BaseRailBlock;
    }

    public boolean isDiamondOre(Block block)
    {
        return block == Blocks.DIAMOND_ORE || block == Blocks.DEEPSLATE_DIAMOND_ORE || block == BlockRegistry.GREEN_DIAMOND_ORE.get();
    }

    public boolean isSculk(Block block)
    {
        return block == Blocks.SCULK || block == Blocks.SCULK_VEIN || block == Blocks.SCULK_CATALYST
                || block == Blocks.SCULK_SENSOR || block == Blocks.SCULK_SHRIEKER;
    }

    public boolean canBeFarmland(Block block)
    {
        return block == Blocks.GRASS_BLOCK || block == Blocks.DIRT_PATH || block == Blocks.DIRT;
    }
}
