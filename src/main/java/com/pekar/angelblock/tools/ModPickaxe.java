package com.pekar.angelblock.tools;

import com.pekar.angelblock.potions.PotionRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class ModPickaxe extends PickaxeItem implements IModTool
{

    public ModPickaxe(Tier material, int attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage, attackSpeed, properties);
    }

    protected final void processAdditionalBlocks(Level level, BlockState state, BlockPos pos, LivingEntity entityLiving, ItemStack itemStack)
    {
        if (level.isClientSide || (double)state.getDestroySpeed(level, pos) == 0.0D)
            return;

        if (!entityLiving.hasEffect(PotionRegistry.TOOL_ADVANCED_MODE_EFFECT.get()))
            return;

        Direction facing = Utils.getDirection(entityLiving, pos);
        final int posX = pos.getX(), posY = pos.getY(), posZ = pos.getZ();

        int a, b, c;
        switch (facing)
        {
            case NORTH:
            case SOUTH:
                a = 1; b = 1; c = 0; break;

            case EAST:
            case WEST:
                a = 0; b = 1; c = 1; break;

            default:
                a = 1; b = 0; c = 1; break;
        }

        BlockState blockState = level.getBlockState(pos);
        float initialHardness = blockState.getBlock().defaultDestroyTime();

        for (int x = posX - a; x <= posX + a; x++)
            for (int y = posY - b; y <= posY + b; y++)
                for (int z = posZ - c; z <= posZ + c; z++)
                {
                    if (x == posX && y == posY && z == posZ) continue;
                    onBlockProcessing(level, blockState, initialHardness, new BlockPos(x, y, z), entityLiving, itemStack);
                }
    }

    protected final boolean isToolEffective(Level level, BlockPos pos)
    {
        BlockState blockState = level.getBlockState(pos);
        return isCorrectToolForDrops(null, blockState);
//        BlockState blockState = level.getBlockState(pos);
//        Block block = blockState.getBlock();
//        return block.isToolEffective("pickaxe", blockState);
    }

    protected void onBlockProcessing(Level level, BlockState initialBlockState, float initialHardness, BlockPos pos, LivingEntity entityLiving, ItemStack itemStack)
    {
        // nothing by default
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
