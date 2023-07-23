package com.pekar.angelblock.tools;

import com.pekar.angelblock.network.packets.PlaySoundPacket;
import com.pekar.angelblock.network.packets.SoundType;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.ToolAction;

public class ModRod extends ModTool implements IModTool
{
    private final boolean isMagnetic;

    public ModRod(Tier material, int attackDamage, float attackSpeed, boolean isMagnetic, Properties properties)
    {
        super(material, attackDamage, attackSpeed, properties);
        this.isMagnetic = isMagnetic;
    }

    @Override
    public boolean mineBlock(ItemStack itemStack, Level level, BlockState blockState, BlockPos pos, LivingEntity entity)
    {
        return true;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction)
    {
        return false;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment)
    {
        return false;
    }

    @Override
    public boolean isEnchantable(ItemStack stack)
    {
        return false;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book)
    {
        return false;
    }

    @Override
    public boolean isEnhancedRod()
    {
        return isMagnetic;
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state)
    {
        return false;
    }

    protected InteractionResult plant(Player player, Level level, BlockPos pos, InteractionHand hand, Direction facing, Block plantBlock)
    {
        if (!(plantBlock instanceof IPlantable plantable)) return InteractionResult.FAIL;

        var blockState = level.getBlockState(pos);
        boolean canSustainPlant = blockState.getBlock().canSustainPlant(blockState, level, pos, facing, plantable);
        if (!canSustainPlant) return InteractionResult.FAIL;

        var itemStack = player.getItemInHand(hand);

        if (facing == Direction.UP && level.isEmptyBlock(pos.above()))
        {
            boolean isClientSide = level.isClientSide();
            if (!isClientSide)
            {
                level.setBlock(pos.above(), plantBlock.defaultBlockState(), 11);

                if (player instanceof ServerPlayer serverPlayer)
                {
                    new PlaySoundPacket(SoundType.PLANT).sendToPlayer(serverPlayer);
                    CriteriaTriggers.PLACED_BLOCK.trigger(serverPlayer, pos.above(), itemStack);
                }
            }

            return InteractionResult.sidedSuccess(isClientSide);
        }
        else
        {
            return InteractionResult.FAIL;
        }
    }

    protected boolean isBroken(ItemStack itemStack)
    {
        return itemStack.getMaxDamage() - itemStack.getDamageValue() <= 1;
    }
}
