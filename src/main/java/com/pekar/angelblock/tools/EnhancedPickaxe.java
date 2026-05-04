package com.pekar.angelblock.tools;

import com.pekar.angelblock.potions.PotionRegistry;
import com.pekar.angelblock.tools.properties.IMaterialProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class EnhancedPickaxe extends ModPickaxe
{
    public EnhancedPickaxe(ModToolMaterial material, int attackDamage, float attackSpeed, Properties properties, IMaterialProperties materialProperties)
    {
        super(material, attackDamage, attackSpeed, properties, materialProperties);
    }

    @Override
    public boolean isEnhanced()
    {
        return true;
    }

    @Override
    public boolean mineBlock(ItemStack itemStack, Level level, BlockState blockState, BlockPos pos, LivingEntity livingEntity)
    {
        if (!level.isClientSide)
            mineAdditionalBlocks(level, pos, livingEntity);
        return super.mineBlock(itemStack, level, blockState, pos, livingEntity);
    }

    protected void mineAdditionalBlocks(Level level, BlockPos pos, LivingEntity entityLiving)
    {
        if (!isToolEffective(entityLiving, pos)) return;

        if (!entityLiving.hasEffect(PotionRegistry.TOOL_ADVANCED_MODE_EFFECT))
            return;

        BlockState blockState = level.getBlockState(pos);
        if (canNotBeMinedInGroup(blockState, level, pos)) return;

        float originHardness = blockState.getBlock().defaultDestroyTime();

        Direction facing = utils.player.getDirection(entityLiving, pos);
        final int posX = pos.getX(), posY = pos.getY(), posZ = pos.getZ();

        int a, b, c;
        switch (facing)
        {
            case NORTH:
            case SOUTH:
                a = 1; b = 1; c = 0; break;

            case EAST:
            case WEST:
                a = 0; b = 1; c = 1; break;

            default:
                a = 1; b = 0; c = 1; break;
        }

        for (int x = posX - a; x <= posX + a; x++)
            for (int y = posY - b; y <= posY + b; y++)
                for (int z = posZ - c; z <= posZ + c; z++)
                {
                    if (x == posX && y == posY && z == posZ) continue;
                    onBlockMining(level, blockState, originHardness, new BlockPos(x, y, z), entityLiving);
                }
    }

    private boolean canNotBeMinedInGroup(BlockState blockState, Level level, BlockPos pos)
    {
        return blockState.hasBlockEntity() || !blockState.isCollisionShapeFullBlock(level, pos);
    }

    protected void onBlockMining(Level level, BlockState originBlockState, float originHardness, BlockPos pos, LivingEntity entityLiving)
    {
        var blockState = level.getBlockState(pos);

        var block = blockState.getBlock();
        if (canNotBeMinedInGroup(blockState, level, pos)) return;

        float hardness = block.defaultDestroyTime();

        if (hardness <= originHardness && isToolEffective(entityLiving, pos)
                && (materialProperties.isSafeToBreak(entityLiving, pos) || entityLiving.isShiftKeyDown()))
        {
            boolean areTheSameOres = isSameOre(originBlockState, blockState);
            if (!utils.blocks.types.isOre(originBlockState) || areTheSameOres)
            {
                if (utils.player.destroyBlockByMainHandTool(level, pos, entityLiving, blockState))
                    damageMainHandItem(1, entityLiving);
            }
        }
    }

    private static boolean isSameOre(BlockState a, BlockState b)
    {
        if (a.getBlock() == b.getBlock()) return true;

        var aTags = a.getTags()
                .filter(tag -> tag.location().getNamespace().equals("minecraft") && tag.location().getPath().endsWith("_ores"))
                .toList();

        return aTags.stream().anyMatch(b::is);
    }
}
