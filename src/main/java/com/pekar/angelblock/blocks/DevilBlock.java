package com.pekar.angelblock.blocks;

import com.pekar.angelblock.blocks.tile_entities.DevilBlockEntity;
import com.pekar.angelblock.blocks.tile_entities.EntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DevilBlock extends ModBlockWithMultipleHoverText implements EntityBlock
{
    public DevilBlock()
    {
        super(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM)
                .strength(10F, 1200F)
                .lightLevel(blockState -> 10));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState)
    {
        return EntityRegistry.DEVIL_BLOCK_ENTITY.get().create(blockPos, blockState);
    }

    @Override
    public void onBlockExploded(BlockState state, Level world, BlockPos pos, Explosion explosion)
    {
        disposeBlockEntity(world, pos);
        super.onBlockExploded(state, world, pos, explosion);
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
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, @NotNull List<Component> components, @NotNull TooltipFlag tooltipFlag)
    {
        for (int i = 1; i <= 15; i++)
        {
            components.add(getDescription(i, i == 3, false, false, false));
        }
    }
}
