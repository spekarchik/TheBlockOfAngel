package com.pekar.angelblock.tools;

import com.pekar.angelblock.blocks.BlockRegistry;
import com.pekar.angelblock.tools.properties.RendelithicMaterialProperties;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public final class RendelithicShovel extends EnhancedShovel
{

    public RendelithicShovel(Tier material, float attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage, attackSpeed, properties, new RendelithicMaterialProperties());
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        var result = super.useOn(context);

        if (result == InteractionResult.FAIL) return result;

        var player = context.getPlayer();
        var level = player.level;

        var pos = context.getClickedPos();
        BlockState blockState = level.getBlockState(pos);
        var block = blockState.getBlock();

        if (block == Blocks.END_STONE)
        {
            if (!level.isClientSide)
                setBlock(player, pos, BlockRegistry.CRACKED_ENDSTONE.get());

            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return result;
    }
}
