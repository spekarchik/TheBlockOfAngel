package com.pekar.angelblock.blocks;

import com.pekar.angelblock.blocks.tile_entities.AngelBlockEntity;
import com.pekar.angelblock.blocks.tile_entities.EntityRegistry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AngelBlock extends ModBlockWithMultipleHoverText implements EntityBlock
{
    private static final int MaxMonstersFilterValue = 4;
    public static final IntegerProperty MONSTERS_IN_FILTER = IntegerProperty.create("monsters_in_filter", 0, MaxMonstersFilterValue);

    public AngelBlock()
    {
        super(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM)
                .strength(1.5F, 1200F)
                .lightLevel(state -> 15));

        registerDefaultState(this.stateDefinition.any().setValue(MONSTERS_IN_FILTER, 0));
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult)
    {
        var blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof AngelBlockEntity angelBlockEntity)
        {
            var interactionItemStack = player.getItemInHand(hand);
            if (interactionItemStack.isEmpty()) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;

            var isClientSide = level.isClientSide();

            var interactionItem = interactionItemStack.getItem();
            if (interactionItem == Items.FLINT)
            {
                if (!isClientSide)
                {
                    angelBlockEntity.resetFilter(player);
                    setBlockStateValue(level, pos, 0);
                }

                return ItemInteractionResult.sidedSuccess(isClientSide);
            }
            else
            {
                var result = angelBlockEntity.addMonsterToFilter(interactionItem, player);
                if (result)
                {
                    setBlockStateValue(level, pos, angelBlockEntity.monstersInFilter());
                }
                return result ? ItemInteractionResult.sidedSuccess(isClientSide) : ItemInteractionResult.FAIL;
            }
        }

        return ItemInteractionResult.FAIL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState)
    {
        return EntityRegistry.ANGEL_BLOCK_ENTITY.get().create(blockPos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @NotNull BlockState blockState, @NotNull BlockEntityType<T> entityType)
    {
        return level.isClientSide()
                ? null
                : (level0, pos, blockState0, blockEntity) -> ((AngelBlockEntity)blockEntity).tick(level0, pos, blockState0, (AngelBlockEntity) blockEntity);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(MONSTERS_IN_FILTER);
    }

    public void setBlockStateValue(Level level, BlockPos pos, int value)
    {
        var stateValue = Math.min(value, MaxMonstersFilterValue);
        BlockState state = level.getBlockState(pos);
        level.setBlock(pos, state.setValue(MONSTERS_IN_FILTER, stateValue), 3);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag)
    {
        if (!Screen.hasShiftDown() && !Screen.hasAltDown())
        {
            tooltipComponents.add(Component.translatable("description.common.press_shift_or_alt"));
            return;
        }

        if (Screen.hasShiftDown())
        {
            for (int i = 1; i <= 19; i++)
            {
                var component = getDescription(i, i == 3, false, i == 17, false, i == 18);
                if (!component.getString().isEmpty())
                    tooltipComponents.add(component);
            }

            tooltipComponents.add(Component.empty());
            tooltipComponents.add(Component.translatable("description.common.press_alt"));
        }

        if (Screen.hasAltDown())
        {
            for (int i = 21; i <= 27; i++)
            {
                var component = getDescription(i, i == 21);
                if (!component.getString().isEmpty())
                    tooltipComponents.add(component);
            }

            tooltipComponents.add(Component.empty());
            tooltipComponents.add(Component.translatable("description.common.press_shift"));
        }
    }
}
