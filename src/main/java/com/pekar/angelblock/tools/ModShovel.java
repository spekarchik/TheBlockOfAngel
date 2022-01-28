package com.pekar.angelblock.tools;

import com.pekar.angelblock.potions.PotionRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class ModShovel extends ShovelItem implements IModTool
{
    public ModShovel(Tier material, float attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage, attackSpeed, properties);
    }

    protected final void dropAdditionalBlocks(Level level, BlockPos pos, LivingEntity entityLiving)
    {
        BlockState blockState = level.getBlockState(pos);
        float initialHardness = blockState.getBlock().defaultDestroyTime();

        if (level.isClientSide() || initialHardness == 0.0F)
            return;


        if (!entityLiving.hasEffect(PotionRegistry.TOOL_ADVANCED_MODE_EFFECT.get()))
            return;

        var facing = Utils.getDirectionForShovel(entityLiving, pos);
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

        for (int x = posX - a; x <= posX + a; x++)
            for (int y = posY - b; y <= posY + b; y++)
                for (int z = posZ - c; z <= posZ + c; z++)
                {
                    if (x == posX && y == posY && z == posZ) continue;
                    onBlockDropping(level, blockState, initialHardness, new BlockPos(x, y, z), entityLiving);
                }
    }

    protected final void transformAdditionalBlocks(Player player, Level level, BlockPos pos, Direction facing)
    {
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
                onBlockTransforming(player, level, pos, new BlockPos(x, posY, z), facing);
            }
    }

    protected void onBlockTransforming(Player player, Level level, BlockPos originalPos, BlockPos pos, Direction facing)
    {
        // nothing by default
    }

    protected final boolean isToolEffective(Level level, BlockPos pos)
    {
        BlockState blockState = level.getBlockState(pos);
        return isCorrectToolForDrops(null, blockState);
    }

    protected void onBlockDropping(Level level, BlockState initialBlockState, float initialHardness, BlockPos pos, LivingEntity entityLiving)
    {
        // nothing by default
    }

    protected final boolean canUseToolEffect(Player player)
    {
        ItemStack itemstack = player.getItemInHand(InteractionHand.OFF_HAND);
        return itemstack.isEmpty() || itemstack.getItem() == Items.TOTEM_OF_UNDYING;
    }

    protected final void damageItem(LivingEntity livingEntity, int amount)
    {
        var itemStack = livingEntity.getItemInHand(InteractionHand.MAIN_HAND);
        itemStack.hurtAndBreak(amount, livingEntity, player -> player.broadcastBreakEvent(InteractionHand.MAIN_HAND));
    }

    @Override
    public boolean isEnhancedTool()
    {
        return false;
    }
}
