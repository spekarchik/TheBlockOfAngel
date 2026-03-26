package com.pekar.angelblock.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class InactiveAngelBlock extends ModBlock
{
    public InactiveAngelBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult)
    {
        if (stack.isEmpty()) return InteractionResult.FAIL;

        var isClientSide = level.isClientSide();

        var interactionItem = stack.getItem();
        if (interactionItem == Items.ECHO_SHARD)
        {
            if (!isClientSide)
            {
                var angelBlock = BlockRegistry.ANGEL_BLOCK.get();
                level.setBlock(pos, angelBlock.defaultBlockState().setValue(AngelBlock.IS_WORMING_UP, true), InactiveAngelBlock.UPDATE_ALL_IMMEDIATE);
            }

            return getInteractionSidedSuccess(isClientSide);
        }

        return InteractionResult.FAIL;
    }
}
