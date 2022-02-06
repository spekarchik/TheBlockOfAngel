package com.pekar.angelblock.tools;

import com.pekar.angelblock.potions.PotionRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.OreBlock;

public class MagneticRod extends ModRod
{
    public MagneticRod(Tier material, int attackDamage, float attackSpeed, boolean isMagnetic, Properties properties)
    {
        super(material, attackDamage, attackSpeed, isMagnetic, properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        var player = context.getPlayer();
        var level = player.level;

        if (level.isClientSide) return InteractionResult.PASS;
        if (!canUseToolEffect(player)) return InteractionResult.PASS;
        if (!isEnhancedRod() || !player.hasEffect(PotionRegistry.ROD_MAGNETIC_MODE_EFFECT.get()))
        {
            return super.useOn(context);
        }

        var pos = context.getClickedPos();
        var block = level.getBlockState(pos).getBlock();
        if (!canBeReplaced(level, block)) return super.useOn(context);

        return shiftOres(level, pos, context.getClickedFace());
    }

    private InteractionResult shiftOres(Level level, BlockPos pos, Direction clickedFace)
    {
        if (clickedFace == Direction.UP)
        {
            int radius = getShiftingRadius();
            int depth = getShiftDepth();

            int X = pos.getX(), Y = pos.getY(), Z = pos.getZ();
            for (int x = X - radius; x <= X + radius; x++)
                for (int z = Z - radius; z <= Z + radius; z++)
                    for (int y = Y - depth; y < Y; y++)
                    {
                        var currentPos = new BlockPos(x, y, z);
                        var currectBlock = level.getBlockState(currentPos).getBlock();
                        var upperBlock = level.getBlockState(currentPos.above()).getBlock();
                        if (!isOre(currectBlock)) continue;
                        if (!canBeReplaced(level, upperBlock)) continue;
                        exchange(level, currentPos);
                    }

            return InteractionResult.CONSUME;
        }

        // TODO: other sides

        return InteractionResult.PASS;
    }

    protected int getShiftingRadius()
    {
        return 1;
    }

    protected int getShiftDepth()
    {
        return 5;
    }

    private void exchange(Level level, BlockPos currentPos)
    {
        var currectBlockState = level.getBlockState(currentPos);
        var upperBlockState = level.getBlockState(currentPos.above());
        level.setBlock(currentPos, upperBlockState, 11);
        level.setBlock(currentPos.above(), currectBlockState, 11);
    }

    protected boolean canBeReplaced(Level level, Block block)
    {
        if (!Utils.isOverworld(level.dimension())) return false;

        return block == Blocks.AIR || block == Blocks.WATER || block == Blocks.STONE || block == Blocks.GRANITE
                || block == Blocks.DIORITE || block == Blocks.ANDESITE
                || block == Blocks.DEEPSLATE || block == Blocks.TUFF || block == Blocks.COBBLESTONE || block == Blocks.COBBLED_DEEPSLATE;
    }

    protected boolean isOre(Block block)
    {
        return block instanceof OreBlock && block != Blocks.REDSTONE_BLOCK && block != Blocks.DEEPSLATE_REDSTONE_ORE
                && block != Blocks.DIAMOND_ORE && block != Blocks.DEEPSLATE_DIAMOND_ORE;
    }
}
