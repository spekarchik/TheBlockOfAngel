package com.pekar.angelblock.tools;

import com.pekar.angelblock.potions.PotionRegistry;
import com.pekar.angelblock.tools.properties.IMaterialProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class EnhancedHoe extends ModHoe
{
    public EnhancedHoe(Tier material, int attackDamage, float attackSpeed, Properties properties, IMaterialProperties materialProperties)
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
    public InteractionResult useOn(UseOnContext context)
    {
        var player = context.getPlayer();
        var level = player.level;

        var result = super.useOn(context);
        if (result != InteractionResult.PASS && result != InteractionResult.CONSUME_PARTIAL) return result;

        if (level.isClientSide) return result;
        if (!canUseToolEffect(player)) return result;

        var pos = context.getClickedPos();
        BlockState blockState = level.getBlockState(pos);
        BlockPos upPos = pos.above();

        processAdditionalBlocks(player, level, pos, context.getClickedFace());
        return InteractionResult.CONSUME;
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
        float originHardness = blockState.getBlock().defaultDestroyTime();

        if (originHardness == 0.0F)
            return;

        final int posX = pos.getX(), posY = pos.getY(), posZ = pos.getZ();

        for (int x = posX - 1; x <= posX + 1; x++)
            for (int y = posY - 1; y <= posY + 1; y++)
                for (int z = posZ - 1; z <= posZ + 1; z++)
                {
                    if (x == posX && y == posY && z == posZ) continue;
                    onBlockMining(level, blockState, originHardness, new BlockPos(x, y, z), entityLiving);
                }
    }

    protected final void processAdditionalBlocks(Player player, Level level, BlockPos pos, Direction facing)
    {
        if (level.isClientSide || !isEnhancedTool() || facing != Direction.UP) return;

        if (!player.hasEffect(PotionRegistry.TOOL_ADVANCED_MODE_EFFECT.get()))
            return;

        BlockState blockState = level.getBlockState(pos);
        if (blockState.hasBlockEntity() || blockState != blockState.getBlock().defaultBlockState()) return;

        float initialHardness = blockState.getBlock().defaultDestroyTime();
        if (initialHardness == 0.0F) return;

        final int posX = pos.getX(), posY = pos.getY(), posZ = pos.getZ();

        int a1, a2, b1, b2;
        switch (player.getDirection())
        {
            case NORTH:
                a1 = 1; a2 = 1; b1 = 3; b2 = 1; break;

            case SOUTH:
                a1 = 1; a2 = 1; b1 = 1; b2 = 3; break;

            case EAST:
                a1 = 1; a2 = 3; b1 = 1; b2 = 1; break;

            case WEST:
                a1 = 3; a2 = 1; b1 = 1; b2 = 1; break;

            default:
                a1 = 1; a2 = 1; b1 = 1; b2 = 1; break;
        }

        for (int x = posX - a1; x <= posX + a2; x++)
            for (int z = posZ - b1; z <= posZ + b2; z++)
            {
                if (x == posX && z == posZ) continue;
                onBlockProcessing(player, level, pos, new BlockPos(x, posY, z), facing);
            }
    }

    protected void onBlockProcessing(Player player, Level level, BlockPos originalPos, BlockPos pos, Direction facing)
    {
        // nothing by default
    }

    protected final boolean isToolEffective(LivingEntity entityLiving, BlockPos pos)
    {
        BlockState blockState = entityLiving.level.getBlockState(pos);
        return isCorrectToolForDrops(entityLiving.getMainHandItem(), blockState);
    }

    protected void onBlockMining(Level level, BlockState originBlockState, float originHardness, BlockPos pos, LivingEntity entityLiving)
    {
        var blockState = level.getBlockState(pos);

        var block = blockState.getBlock();
        if (blockState.hasBlockEntity() || blockState != block.defaultBlockState()) return;

        float hardness = block.defaultDestroyTime();

        if (hardness <= originHardness && isToolEffective(entityLiving, pos)
                && (materialProperties.isSafeToBreak(entityLiving, pos) ||  entityLiving.isShiftKeyDown()))
        {
            if (utils.destroyBlockByMainHandTool(level, pos, entityLiving, blockState, block))
                damageItem(1, entityLiving);
        }
    }

    protected boolean canPreventBlockDestroying(LivingEntity entity, BlockPos pos)
    {
        return isToolEffective(entity, pos) && !entity.isShiftKeyDown();
    }
}
