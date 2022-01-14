package com.pekar.angelblock.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class DevilBlock extends Block
{
    public DevilBlock()
    {
        super(BlockBehaviour.Properties.of(Material.HEAVY_METAL)
                .strength(15F)
                .lightLevel(blockState -> 10));
//        super(Material.DRAGON_EGG);
//        setHardness(15F);
//        setLightLevel(0.9F);
//        setLightOpacity(170);
    }

//    @Override
//    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
//    {
//        DevilTileEntity tileEntity = getTileEntity(worldIn, pos);
//        if (tileEntity != null)
//        {
//            tileEntity.activate();
//        }
//
//        super.onBlockAdded(worldIn, pos, state);
//    }
//
//    @Override
//    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
//    {
//        TileEntity tileEntity = worldIn.getTileEntity(pos);
//        if (tileEntity instanceof DevilTileEntity)
//        {
//            DevilTileEntity devilTileEntity = (DevilTileEntity) tileEntity;
//            devilTileEntity.dispose();
//        }
//
//        super.breakBlock(worldIn, pos, state);
//    }
//
//    @Override
//    public Class<DevilTileEntity> getTileEntityClass()
//    {
//        return DevilTileEntity.class;
//    }
//
//    @Nullable
//    @Override
//    public TileEntity createTileEntity(World world, IBlockState state)
//    {
//        return new DevilTileEntity();
//    }
}
