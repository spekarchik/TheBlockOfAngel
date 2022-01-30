package com.pekar.angelblock.tools;

import com.pekar.angelblock.potions.PotionRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;

public class ModHoe extends HoeItem implements IModTool
{

    public ModHoe(Tier material, int attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage, attackSpeed, properties);
    }

    protected final void processAdditionalBlocks(Player player, Level level, BlockPos pos)
    {
        if (level.isClientSide || !isEnhancedTool() || !isToolEffective(level, pos)) return;

        if (!player.hasEffect(PotionRegistry.TOOL_ADVANCED_MODE_EFFECT.get()))
            return;

        BlockState blockState = level.getBlockState(pos);
        float initialHardness = blockState.getBlock().defaultDestroyTime();

        if (initialHardness == 0.0F)
            return;

        final int posX = pos.getX(), posY = pos.getY(), posZ = pos.getZ();

        int a1, a2, b1, b2;
        switch (player.getDirection())
        {
            case NORTH:
                a1 = 1; a2 = 1; b1 = 3; b2 = 1; break;

            case SOUTH:
                a1 = 1; a2 = 1; b1 = 1; b2 = 3; break;

            case EAST:
                a1 = 1; a2 = 3; b1 = 1; b2 = 1; break;

            case WEST:
                a1 = 3; a2 = 1; b1 = 1; b2 = 1; break;

            default:
                a1 = 1; a2 = 1; b1 = 1; b2 = 1; break;
        }

        for (int x = posX - a1; x <= posX + a2; x++)
            for (int z = posZ - b1; z <= posZ + b2; z++)
            {
                if (x == posX && z == posZ) continue;
                onBlockProcessing(player, level, pos, new BlockPos(x, posY, z));
            }
    }

    protected void onBlockProcessing(Player player, Level level, BlockPos originalPos, BlockPos pos)
    {
        // nothing by default
    }

    protected final boolean isToolEffective(Level level, BlockPos pos)
    {
        BlockState blockState = level.getBlockState(pos);
        return isCorrectToolForDrops(null, blockState);
    }

    protected boolean updateIfWater(Level level, BlockPos pos)
    {
        BlockState waterBlockState = level.getBlockState(pos);
        if (!(waterBlockState.getBlock() instanceof LiquidBlock))
        {
            return false;
        }

        LiquidBlock liquidBlock = (LiquidBlock) waterBlockState.getBlock();
        liquidBlock.neighborChanged(waterBlockState, level, pos, liquidBlock, pos, false /* ignored */);
        return true;
    }

    protected final boolean canUseToolEffect(Player player)
    {
        ItemStack itemstack = player.getItemInHand(InteractionHand.OFF_HAND);
        return itemstack.isEmpty() || itemstack.getItem() == Items.TOTEM_OF_UNDYING;
    }

    @Override
    public boolean isEnhancedTool()
    {
        return false;
    }
}
