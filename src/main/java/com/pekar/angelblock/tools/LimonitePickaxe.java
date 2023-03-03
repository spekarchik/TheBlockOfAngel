package com.pekar.angelblock.tools;

import com.pekar.angelblock.tools.properties.LimoniteMaterialProperties;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.InfestedBlock;
import net.minecraft.world.level.block.state.BlockState;

public class LimonitePickaxe extends EnhancedPickaxe
{
    public LimonitePickaxe(Tier material, int attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage, attackSpeed, properties, new LimoniteMaterialProperties());
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
