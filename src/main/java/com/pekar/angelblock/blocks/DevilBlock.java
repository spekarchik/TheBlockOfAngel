package com.pekar.angelblock.blocks;

import com.pekar.angelblock.blocks.tile_entities.DevilBlockEntity;
import com.pekar.angelblock.blocks.tile_entities.EntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.Nullable;

public class DevilBlock extends ModBlockWithHoverText implements EntityBlock
{
    public DevilBlock()
    {
        super(BlockBehaviour.Properties.of(Material.HEAVY_METAL)
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
}
