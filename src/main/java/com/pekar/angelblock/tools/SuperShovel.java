package com.pekar.angelblock.tools;

import com.pekar.angelblock.blocks.BlockRegistry;
import com.pekar.angelblock.tools.properties.SuperMaterialProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class SuperShovel extends ModShovel
{
    public SuperShovel(Tier material, float attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage, attackSpeed, properties, new SuperMaterialProperties());
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        var player = context.getPlayer();
        var level = player.level;
        var pos = context.getClickedPos();

        if (!level.isClientSide && canUseToolEffect(player))
        {
            BlockState blockState = level.getBlockState(pos);
            var block = blockState.getBlock();

            if (block == Blocks.END_STONE)
            {
                setBlock(player, pos, BlockRegistry.CRACKED_ENDSTONE.get());
                return InteractionResult.CONSUME;
            }
        }

        InteractionResult result = super.useOn(context);

        if (result != InteractionResult.FAIL)
        {
            processAdditionalBlocks(player, level, pos, context.getClickedFace());
            return InteractionResult.CONSUME;
        }

        return result;
    }

    @Override
    public boolean mineBlock(ItemStack itemStack, Level level, BlockState blockState, BlockPos pos, LivingEntity livingEntity)
    {
        if (!level.isClientSide)
            mineAdditionalBlocks(level, pos, livingEntity);
        return super.mineBlock(itemStack, level, blockState, pos, livingEntity);
    }

    @Override
    public boolean isEnhancedTool()
    {
        return true;
    }
}
