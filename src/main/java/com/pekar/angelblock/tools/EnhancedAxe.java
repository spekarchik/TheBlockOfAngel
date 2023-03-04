package com.pekar.angelblock.tools;

import com.pekar.angelblock.potions.PotionRegistry;
import com.pekar.angelblock.tools.properties.IMaterialProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class EnhancedAxe extends ModAxe
{
    public EnhancedAxe(Tier material, float attackDamage, float attackSpeed, Properties properties, IMaterialProperties materialProperties)
    {
        super(material, attackDamage, attackSpeed, properties, materialProperties);
    }

    @Override
    public boolean isEnhancedTool()
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

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, Player player)
    {
        if (canPreventBlockDestroying(player, pos) && !materialProperties.isSafeToBreak(player, pos)) return true;
        return super.onBlockStartBreak(itemstack, pos, player);
    }

    protected void mineAdditionalBlocks(Level level, BlockPos pos, LivingEntity entityLiving)
    {
        if (level.isClientSide || !isEnhancedTool() || !isToolEffective(entityLiving, pos)) return;

        if (!entityLiving.hasEffect(PotionRegistry.TOOL_ADVANCED_MODE_EFFECT.get()))
            return;

        BlockState blockState = level.getBlockState(pos);
        if (blockState.hasBlockEntity() || blockState != blockState.getBlock().defaultBlockState()) return;

        float originHardness = blockState.getBlock().defaultDestroyTime();
        if (originHardness == 0.0F) return;

        final int posX = pos.getX(), posY = pos.getY(), posZ = pos.getZ();

        for (int x = posX - 1; x <= posX + 1; x++)
            for (int y = posY - 1; y <= posY + 1; y++)
                for (int z = posZ - 1; z <= posZ + 1; z++)
                {
                    if (x == posX && y == posY && z == posZ) continue;
                    onBlockMining(level, blockState, originHardness, new BlockPos(x, y, z), entityLiving);
                }
    }

    protected final boolean isToolEffective(LivingEntity entityLiving, BlockPos pos)
    {
        BlockState blockState = entityLiving.level.getBlockState(pos);
        return isCorrectToolForDrops(entityLiving.getMainHandItem(), blockState);
    }

    protected void onBlockMining(Level level, BlockState originBlockState, float originHardness, BlockPos pos, LivingEntity entityLiving)
    {
        var blockState = level.getBlockState(pos);

        if (blockState.hasBlockEntity()) return;

        var block = blockState.getBlock();
        float hardness = block.defaultDestroyTime();

        if (hardness <= originHardness && isToolEffective(entityLiving, pos)
                && (materialProperties.isSafeToBreak(entityLiving, pos) ||  entityLiving.isShiftKeyDown()))
        {
            if (utils.destroyBlockByMainHandTool(level, pos, entityLiving, blockState, block))
                damageItem(1, entityLiving);
        }
    }

    protected final boolean canUseToolEffect(Player player)
    {
        ItemStack itemstack = player.getItemInHand(InteractionHand.OFF_HAND);
        return itemstack.isEmpty() || !(itemstack.getItem() instanceof BlockItem);
    }

    protected boolean canPreventBlockDestroying(LivingEntity entity, BlockPos pos)
    {
        return isToolEffective(entity, pos) && !entity.isShiftKeyDown();
    }
}
