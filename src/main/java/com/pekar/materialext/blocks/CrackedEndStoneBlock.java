package com.pekar.materialext.blocks;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class CrackedEndStoneBlock extends ModBlock
{
    public CrackedEndStoneBlock()
    {
        super(BlockBehaviour.Properties.of(Material.SNOW)
                        .strength(0.5f)
                        .sound(SoundType.SNOW));
//        setHardness(0.5f);
//        setHarvestLevel("shovel", 2);
    }

//    @Override
//    public Item getItemDropped(IBlockState state, Random rand, int fortune)
//    {
//        if (fortune >= 20) return ItemRegistry.ENDSTONE_POWDER;
//
//        if (fortune > 3)
//        {
//            fortune = 3;
//        }
//
//        return rand.nextInt(12 - fortune * 3) == 0 ? ItemRegistry.ENDSTONE_POWDER : Items.AIR;
//    }
//
//    @Override
//    public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune)
//    {
//        Random rand = world instanceof World ? ((World)world).rand : new Random();
//        return MathHelper.getInt(rand, 2, 5);
//    }
//
//    @Override
//    public int quantityDropped(Random random)
//    {
//        return random.nextInt(4) + 1;
//    }
}
