package com.pekar.angelblock.blocks;

import com.pekar.angelblock.blocks.tile_entities.DevilBlockEntity;
import com.pekar.angelblock.blocks.tile_entities.EntityRegistry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DevilBlock extends ModBlockWithMultipleHoverText implements EntityBlock
{
    public DevilBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult)
    {
        var blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof DevilBlockEntity devilBlockEntity)
        {
            var interactionItemStack = player.getItemInHand(hand);
            if (interactionItemStack.isEmpty()) return InteractionResult.FAIL;

            var interactionItem = interactionItemStack.getItem();

            if (!level.isClientSide())
                devilBlockEntity.spawnMonster(interactionItem, player, interactionItemStack);

            return getInteractionSidedSuccess(level.isClientSide());
        }

        return InteractionResult.FAIL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState)
    {
        return EntityRegistry.DEVIL_BLOCK_ENTITY.get().create(blockPos, blockState);
    }

    @Override
    public void onBlockExploded(BlockState state, ServerLevel level, BlockPos pos, Explosion explosion)
    {
        disposeBlockEntity(level, pos);
        super.onBlockExploded(state, level, pos, explosion);
    }

    private void disposeBlockEntity(Level world, BlockPos pos)
    {
        var blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof DevilBlockEntity)
        {
            var devilBlockEntity = (DevilBlockEntity) blockEntity;
            devilBlockEntity.dispose();
        }
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level world, BlockPos pos, Player player, boolean willHarvest, FluidState fluid)
    {
        disposeBlockEntity(world, pos);
        return super.onDestroyedByPlayer(state, world, pos, player, willHarvest, fluid);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType)
    {
        return level.isClientSide()
                ? null
                : (level0, pos, blockState0, blockEntity) -> ((DevilBlockEntity)blockEntity).tick(level0, pos, blockState0, (DevilBlockEntity)blockEntity);
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston)
    {
        if (!level.isClientSide)
        {
            level.playSound(null, pos, SoundEvents.AMBIENT_CAVE.value(), SoundSource.BLOCKS, 1.0f, 1.0f);
        }
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
            for (int i = 1; i <= 16; i++)
            {
                var component = getDescription(i, i == 3, false, false, false);
                if (!component.getString().isEmpty())
                    tooltipComponents.add(component);
            }

            tooltipComponents.add(Component.empty());
            tooltipComponents.add(Component.translatable("description.common.press_alt"));
        }
        else if (Screen.hasAltDown())
        {
            for (int i = 17; i <= 35; i++)
            {
                var component = getDescription(i, i == 17, false, false, false, i >= 32);
                if (!component.getString().isEmpty())
                    tooltipComponents.add(component);
            }

            tooltipComponents.add(Component.empty());
            tooltipComponents.add(Component.translatable("description.common.press_shift"));
        }
    }
}
