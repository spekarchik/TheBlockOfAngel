package com.pekar.angelblock.tools;

import com.pekar.angelblock.blocks.BlockRegistry;
import com.pekar.angelblock.tools.properties.RendelithicMaterialProperties;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class RendelithicShovel extends EnhancedShovel
{

    public RendelithicShovel(Tier material, float attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage, attackSpeed, properties, new RendelithicMaterialProperties());
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
            Block block = blockState.getBlock();

            if (block == Blocks.END_STONE)
            {
                setBlock(player, pos, BlockRegistry.CRACKED_ENDSTONE.get());
                return InteractionResult.CONSUME;
            }
        }

        return super.useOn(context);
    }
}
