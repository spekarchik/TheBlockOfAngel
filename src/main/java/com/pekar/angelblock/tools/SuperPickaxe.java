package com.pekar.angelblock.tools;

import com.pekar.angelblock.blocks.BlockRegistry;
import com.pekar.angelblock.tools.properties.SuperMaterialProperties;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.InfestedBlock;
import net.minecraft.world.level.block.state.BlockState;

public class SuperPickaxe extends EnhancedPickaxe
{
    public SuperPickaxe(Tier material, int attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage, attackSpeed, properties, new SuperMaterialProperties());
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        var player = context.getPlayer();
        var level = player.level;

        if (level.isClientSide) return InteractionResult.PASS;
        if (!canUseToolEffect(player)) return InteractionResult.PASS;

        var pos = context.getClickedPos();

        BlockState blockState = level.getBlockState(pos);
        Block block = blockState.getBlock();

        if (block == Blocks.OBSIDIAN)
        {
            level.setBlock(pos, BlockRegistry.CRACKED_OBSIDIAN.get().defaultBlockState(), 11);
            return InteractionResult.CONSUME;
        }

        if (block instanceof InfestedBlock infestedBlock)
        {
            setBlock(player, pos, infestedBlock.getHostBlock());
            return InteractionResult.CONSUME;
        }

        if (Utils.mossyTransforming(player, pos, block))
        {
            return InteractionResult.CONSUME;
        }

        return super.useOn(context);
    }
}
