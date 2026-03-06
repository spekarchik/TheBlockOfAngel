package com.pekar.angelblock.utils;

import com.pekar.angelblock.Main;
import com.pekar.angelblock.blocks.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;

import static com.pekar.angelblock.utils.Resources.createResourceLocation;

public class BlockTypes
{
    BlockTypes()
    {

    }

    public boolean isInfested(Block block)
    {
        return block instanceof InfestedBlock;
    }

    public boolean isSuspicious(Block block)
    {
        return block instanceof BrushableBlock;
    }

    public boolean holdsSuspiciousOrLiquid(LivingEntity livingEntity, BlockPos pos, boolean checkForWaterAndSuspicious, boolean checkForLava)
    {
        var posToCheck = pos;
        var level = livingEntity.level();

        for (int i = 0; i < 16; posToCheck = posToCheck.above(), i++)
        {
            var blockState = level.getBlockState(posToCheck);
            if (blockState.isAir()) return false;

            if (checkForWaterAndSuspicious && blockState.is(Blocks.WATER) && !livingEntity.isInWater()) return true;
            if (checkForLava && blockState.is(Blocks.LAVA) && !livingEntity.isInLava()) return true;

            var block = blockState.getBlock();
            if (checkForWaterAndSuspicious && isSuspicious(block)) return true;

            var isFallingBlock = block instanceof FallingBlock;
            if (isFallingBlock)
            {
                var blockUtils = Utils.instance.blocks;
                if (checkForWaterAndSuspicious && blockUtils.conditions.isNearWater(level, posToCheck) && !livingEntity.isInWater()) return true;
                if (checkForLava && blockUtils.conditions.isNearLava(level, posToCheck) && !livingEntity.isInLava()) return true;
            }
            else
            {
                if (posToCheck.getY() > pos.getY() && blockState.isSolidRender(level, posToCheck)) return false;
            }
        }

        return false;
    }

    public boolean isOre(BlockState blockState)
    {
        var ores = TagKey.create(Registries.BLOCK, createResourceLocation(Main.MODID, "ores"));
        return blockState.is(ores);
    }

    public boolean isLiquid(Block block)
    {
        return block instanceof LiquidBlock;
    }

    public boolean isCactiPlantableOn(BlockState blockState)
    {
        return blockState.is(BlockTags.SAND);
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
        var blockState = level.getBlockState(pos);
        return blockState.is(Blocks.GRASS_BLOCK) || blockState.is(BlockTags.DIRT) || blockState.is(Blocks.FARMLAND)
                || blockState.is(BlockTags.SAND) || blockState.is(Blocks.GRAVEL) || blockState.is(Blocks.SUSPICIOUS_GRAVEL);
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

    public Block getDestroyingWoolBlock(Block block)
    {
        if (block == Blocks.WHITE_WOOL)
        {
            return BlockRegistry.DESTROYING_WHITE_WOOL_BY_ROD.get();
        }
        else if (block == Blocks.ORANGE_WOOL)
        {
            return BlockRegistry.DESTROYING_ORANGE_WOOL.get();
        }
        else if (block == Blocks.MAGENTA_WOOL)
        {
            return BlockRegistry.DESTROYING_MAGENTA_WOOL.get();
        }
        else if (block == Blocks.LIGHT_BLUE_WOOL)
        {
            return BlockRegistry.DESTROYING_LIGHT_BLUE_WOOL.get();
        }
        else if (block == Blocks.YELLOW_WOOL)
        {
            return BlockRegistry.DESTROYING_YELLOW_WOOL.get();
        }
        else if (block == Blocks.LIME_WOOL)
        {
            return BlockRegistry.DESTROYING_LIME_WOOL.get();
        }
        else if (block == Blocks.PINK_WOOL)
        {
            return BlockRegistry.DESTROYING_PINK_WOOL.get();
        }
        else if (block == Blocks.GRAY_WOOL)
        {
            return BlockRegistry.DESTROYING_GRAY_WOOL.get();
        }
        else if (block == Blocks.LIGHT_GRAY_WOOL)
        {
            return BlockRegistry.DESTROYING_LIGHT_GRAY_WOOL.get();
        }
        else if (block == Blocks.CYAN_WOOL)
        {
            return BlockRegistry.DESTROYING_CYAN_WOOL.get();
        }
        else if (block == Blocks.PURPLE_WOOL)
        {
            return BlockRegistry.DESTROYING_PURPLE_WOOL.get();
        }
        else if (block == Blocks.BLUE_WOOL)
        {
            return BlockRegistry.DESTROYING_BLUE_WOOL.get();
        }
        else if (block == Blocks.BROWN_WOOL)
        {
            return BlockRegistry.DESTROYING_BROWN_WOOL.get();
        }
        else if (block == Blocks.GREEN_WOOL)
        {
            return BlockRegistry.DESTROYING_GREEN_WOOL.get();
        }
        else if (block == Blocks.RED_WOOL)
        {
            return BlockRegistry.DESTROYING_RED_WOOL.get();
        }
        else if (block == Blocks.BLACK_WOOL)
        {
            return BlockRegistry.DESTROYING_BLACK_WOOL.get();
        }

        return null;
    }
}
