package com.pekar.angelblock.tools;

import com.pekar.angelblock.potions.PotionRegistry;
import com.pekar.angelblock.tools.properties.IMaterialProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RedStoneOreBlock;
import net.minecraft.world.level.block.state.BlockState;

public class EnhancedPickaxe extends ModPickaxe
{
    public EnhancedPickaxe(Tier material, int attackDamage, float attackSpeed, Properties properties, IMaterialProperties materialProperties)
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

    protected final void mineAdditionalBlocks(Level level, BlockPos pos, LivingEntity entityLiving)
    {
        if (level.isClientSide || !isEnhancedTool() || !isToolEffective(entityLiving, pos)) return;

        if (!entityLiving.hasEffect(PotionRegistry.TOOL_ADVANCED_MODE_EFFECT.get()))
            return;

        BlockState blockState = level.getBlockState(pos);
        var block = blockState.getBlock();
        if (blockState.hasBlockEntity() || (blockState != block.defaultBlockState() && !(block instanceof RedStoneOreBlock))) return;

        float originHardness = blockState.getBlock().defaultDestroyTime();
        if (originHardness == 0.0F) return;

        Direction facing = Utils.getDirection(entityLiving, pos);
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

    protected void onBlockMining(Level level, BlockState originBlockState, float originHardness, BlockPos pos, LivingEntity entityLiving)
    {
        var blockState = level.getBlockState(pos);

        var block = blockState.getBlock();
        if (blockState.hasBlockEntity() || (blockState != block.defaultBlockState() && !(block instanceof RedStoneOreBlock))) return;

        float hardness = block.defaultDestroyTime();

        if (hardness <= originHardness && isToolEffective(entityLiving, pos)
                && (materialProperties.isSafeToBreak(entityLiving, pos) ||  entityLiving.isShiftKeyDown()))
        {
            var originBlock = originBlockState.getBlock();
            if (!Utils.isOre(originBlock) || originBlock == block)
            {
                if (utils.destroyBlockByMainHandTool(level, pos, entityLiving, blockState, block))
                    damageItem(1, entityLiving);
            }
        }
    }

    protected boolean canPreventBlockDestroying(LivingEntity entity, BlockPos pos)
    {
        return isToolEffective(entity, pos) && !entity.isShiftKeyDown();
    }

    protected final boolean isToolEffective(LivingEntity entityLiving, BlockPos pos)
    {
        BlockState blockState = entityLiving.level.getBlockState(pos);
        return isCorrectToolForDrops(entityLiving.getMainHandItem(), blockState);
    }

    protected final boolean canUseToolEffect(Player player)
    {
        ItemStack itemstack = player.getItemInHand(InteractionHand.OFF_HAND);
        return itemstack.isEmpty() || !(itemstack.getItem() instanceof BlockItem);
    }
}
