package com.pekar.angelblock.tools;

import com.pekar.angelblock.tooltip.ITooltip;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.neoforged.neoforge.common.ItemAbility;

public abstract class ModRod extends ModTool implements IModTool
{
    private final boolean isMagnetic;
    private final ModToolMaterial material;

    public ModRod(ModToolMaterial material, boolean isMagnetic, Properties properties)
    {
        super(material, material.getIncorrectBlocksForDrops(), 0F, -3.0F, properties);
        this.isMagnetic = isMagnetic;
        this.material = material;
    }

    @Override
    public boolean mineBlock(ItemStack itemStack, Level level, BlockState blockState, BlockPos pos, LivingEntity entity)
    {
        if (!hasCriticalDamage(itemStack))
            additionalActionOnMineBlock(itemStack, level, blockState, pos, entity);

        return true;
    }

    protected void additionalActionOnMineBlock(ItemStack itemStack, Level level, BlockState blockState, BlockPos pos, LivingEntity entity)
    {
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility)
    {
        return false;
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment)
    {
        return false;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book)
    {
        return false;
    }

    @Override
    public boolean isEnhanced()
    {
        return isMagnetic;
    }

    @Override
    public boolean isRod()
    {
        return true;
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state)
    {
        return false;
    }

    @Override
    public ModToolMaterial getMaterial()
    {
        return material;
    }

    @Override
    public void damageOffHandItem(int amount, LivingEntity livingEntity)
    {
        var itemStack = livingEntity.getItemInHand(InteractionHand.OFF_HAND);
        var durability = itemStack.getMaxDamage() - itemStack.getDamageValue();
        if (durability <= amount) return;

        super.damageOffHandItem(amount, livingEntity);
    }

    protected boolean showExtendedDescription(ITooltip tooltip)
    {
        if (!Screen.hasShiftDown() && !Screen.hasAltDown() && !Screen.hasControlDown())
        {
            tooltip.addLineById("description.rods.press_shift_alt_or_ctrl").apply();
            return false;
        }

        return true;
    }

    protected InteractionResult plant(Player player, Level level, BlockPos pos, InteractionHand hand, Direction facing, Block plantBlock)
    {
        var canSustainPlant = utils.blocks.conditions.canSustainPlant(level, pos, plantBlock.defaultBlockState());
        if (!canSustainPlant) return InteractionResult.FAIL;

        var itemStack = player.getItemInHand(hand);
        var abovePos = pos.above();

        if (facing == Direction.UP && level.getBlockState(abovePos).isAir() && level.getBlockState(abovePos.above()).isAir())
        {
            boolean isClientSide = level.isClientSide();
            if (!isClientSide)
            {
                level.setBlock(abovePos, plantBlock.defaultBlockState(), 11);

                if (plantBlock instanceof DoublePlantBlock doublePlant)
                {
                    level.setBlock(abovePos.above(), doublePlant.defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER), 11);
                }

                if (player instanceof ServerPlayer serverPlayer)
                {
                    level.playSound(null, pos.above(), SoundEvents.GRASS_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
                    CriteriaTriggers.PLACED_BLOCK.trigger(serverPlayer, pos.above(), itemStack);
                }
            }

            return getToolInteractionResult(true, isClientSide);
        }
        else
        {
            return InteractionResult.FAIL;
        }
    }

    @Override
    public int getCriticalDurability()
    {
        return 1;
    }
}
