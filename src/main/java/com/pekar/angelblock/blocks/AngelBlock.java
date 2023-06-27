package com.pekar.angelblock.blocks;

import com.pekar.angelblock.blocks.tile_entities.AngelBlockEntity;
import com.pekar.angelblock.blocks.tile_entities.EntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AngelBlock extends ModBlockWithMultipleHoverText implements EntityBlock
{
    public AngelBlock()
    {
        super(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                .strength(1.5F, 1200F)
                .lightLevel(state -> 15));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState)
    {
        return EntityRegistry.ANGEL_BLOCK_ENTITY.get().create(blockPos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> entityType)
    {
        return level.isClientSide()
                ? null
                : (level0, pos, blockState0, blockEntity) -> ((AngelBlockEntity)blockEntity).tick(level0, pos, blockState0, (AngelBlockEntity) blockEntity);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable BlockGetter blockGetter, List<Component> components, TooltipFlag tooltipFlag)
    {
        for (int i = 1; i <= 16; i++)
        {
            components.add(getDescription(i, false, i == 3, false, i == 16));
        }
    }
}
