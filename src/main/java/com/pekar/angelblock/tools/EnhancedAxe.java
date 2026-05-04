package com.pekar.angelblock.tools;

import com.pekar.angelblock.potions.PotionRegistry;
import com.pekar.angelblock.tools.properties.IMaterialProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.level.block.state.BlockState;

public class EnhancedAxe extends ModAxe
{
    public EnhancedAxe(ModToolMaterial material, float attackDamage, float attackSpeed, Properties properties, IMaterialProperties materialProperties)
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
        mineAdditionalBlocks(itemStack, level, pos, livingEntity);
        return super.mineBlock(itemStack, level, blockState, pos, livingEntity);
    }

    protected void mineAdditionalBlocks(ItemStack itemStack, Level level, BlockPos pos, LivingEntity entityLiving)
    {
        if (!entityLiving.hasEffect(PotionRegistry.TOOL_ADVANCED_MODE_EFFECT))
            return;

        BlockState blockState = level.getBlockState(pos);
        var block = blockState.getBlock();
        if (!isToolEffective(entityLiving, pos)) return;

        if (canNotBeMinedInGroup(blockState)) return;

        final int posX = pos.getX(), posY = pos.getY(), posZ = pos.getZ();

        for (int x = posX - 1; x <= posX + 1; x++)
            for (int y = posY - 1; y <= posY + 1; y++)
                for (int z = posZ - 1; z <= posZ + 1; z++)
                {
                    if (x == posX && y == posY && z == posZ) continue;
                    onBlockMining(itemStack, level, new BlockPos(x, y, z), block, entityLiving);
                }
    }

    protected final boolean isCompatiblePlant(ItemStack itemStack, BlockState blockState)
    {
        return isCorrectToolForDrops(itemStack, blockState) && blockState.getBlock() instanceof VegetationBlock;
    }

    protected void onBlockMining(ItemStack itemStack, Level level, BlockPos pos, Block originBlock, LivingEntity entityLiving)
    {
        var blockState = level.getBlockState(pos);
        var block = blockState.getBlock();

        if (block != originBlock) return;
        if (canNotBeMinedInGroup(blockState)) return;

        float hardness = block.defaultDestroyTime();
        float originHardness = originBlock.defaultDestroyTime();

        if (hardness <= originHardness && isToolEffective(entityLiving, pos)
                && (materialProperties.isSafeToBreak(entityLiving, pos) || entityLiving.isShiftKeyDown()))
        {
            if (utils.player.destroyBlockByMainHandTool(level, pos, entityLiving, blockState))
                damageMainHandItem(1, entityLiving);
        }
    }

    private boolean canNotBeMinedInGroup(BlockState blockState)
    {
        return blockState.hasBlockEntity();
    }
}
