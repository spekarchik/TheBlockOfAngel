package com.pekar.materialext.blocks;

import com.pekar.materialext.blocks.tile_entities.AngelBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.Nullable;

public class AngelBlock extends Block implements EntityBlock
{
    public AngelBlock()
    {
        super(BlockBehaviour.Properties.of(Material.HEAVY_METAL)
                .strength(8f)
                .lightLevel(state -> 200));

        //super(Material.DRAGON_EGG);
//        setHardness(8F);
//        setLightLevel(0.9F);
//        setLightOpacity(150);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState)
    {
        return new AngelBlockEntity(blockPos, blockState);
    }
    /*
    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        getTileEntity(worldIn, pos);
    }

    @Override
    public Class<AngelTileEntity> getTileEntityClass()
    {
        return AngelTileEntity.class;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new AngelTileEntity();
    }

     */
}
