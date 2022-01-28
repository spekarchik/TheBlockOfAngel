package com.pekar.angelblock.tools;

import com.pekar.angelblock.potions.PotionRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class ModAxe extends AxeItem implements IModTool
{
    public ModAxe(Tier material, float attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage, attackSpeed, properties);
    }

    protected void dropAdditionalBlocks(Level level, BlockState state, BlockPos pos, LivingEntity entityLiving, ItemStack itemStack)
    {
        if (level.isClientSide() || (double)state.getDestroySpeed(null, pos) == 0.0D || !isToolEffective(level, pos))
            return;

        if (!entityLiving.hasEffect(PotionRegistry.TOOL_ADVANCED_MODE_EFFECT.get()))
            return;

        final int posX = pos.getX(), posY = pos.getY(), posZ = pos.getZ();

        BlockState blockState = level.getBlockState(pos);
        float initialHardness = blockState.getBlock().defaultDestroyTime();

        for (int x = posX - 1; x <= posX + 1; x++)
            for (int y = posY - 1; y <= posY + 1; y++)
                for (int z = posZ - 1; z <= posZ + 1; z++)
                {
                    if (x == posX && y == posY && z == posZ) continue;
                    onBlockDropping(level, blockState, initialHardness, new BlockPos(x, y, z), entityLiving, itemStack);
                }
    }

    protected final boolean isToolEffective(Level level, BlockPos pos)
    {
        BlockState blockState = level.getBlockState(pos);
//        Block block = blockState.getBlock();
//        return block.isToolEffective("axe", blockState);
        return isCorrectToolForDrops(null, blockState);
    }

    protected void onBlockDropping(Level level, BlockState initialBlockState, float initialHardness, BlockPos pos, LivingEntity entityLiving, ItemStack itemStack)
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
