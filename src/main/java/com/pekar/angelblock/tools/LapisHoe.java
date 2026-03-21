package com.pekar.angelblock.tools;

import com.pekar.angelblock.tools.properties.LapisHoeProperties;
import com.pekar.angelblock.tooltip.ITooltip;
import com.pekar.angelblock.tooltip.TextStyle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class LapisHoe extends EnhancedHoe
{
    public LapisHoe(ModToolMaterial material, int attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage, attackSpeed, properties, new LapisHoeProperties());
    }

    @Override
    protected boolean onBlockProcessing(Player player, Level level, BlockPos originalPos, BlockPos pos, Direction facing)
    {
        if (!level.isEmptyBlock(pos.above())) return false;

        var blockState = level.getBlockState(pos);
        Block block = blockState.getBlock();

        if (utils.blocks.types.canBeFarmland(block))
        {
            if (!level.isClientSide())
            {
                level.setBlock(pos, Blocks.FARMLAND.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
                damageMainHandItemIfSurvivalIgnoreClient(player, level);
            }

            utils.sound.playSoundByBlock(player, pos, SoundEvents.HOE_TILL);

            return true;
        }
        else if (block == Blocks.COARSE_DIRT)
        {
            if (!level.isClientSide())
            {
                setBlockWithClientSound(player, pos, Blocks.DIRT);
                damageMainHandItemIfSurvivalIgnoreClient(player, level);
            }

            utils.sound.playSoundByBlock(player, pos, SoundEvents.HOE_TILL);

            return true;
        }
        else
        {
            return changePodzolToDirt(player, level, pos);
        }
    }

    @Override
    public void addTooltip(ItemStack stack, TooltipContext context, ITooltip tooltip, TooltipFlag flag)
    {
        if (!utils.text.showExtendedDescription(tooltip)) return;

        tooltip.ignoreEmptyLines();

        for (int i = 0; i <= 17; i++)
        {
            tooltip.addLine(getDescriptionId(), i)
                    .styledAs(TextStyle.Header, i == 1 || i == 5 || i == 9)
                    .styledAs(TextStyle.Notice, i == 3)
                    .styledAs(TextStyle.DarkGray, i >= 15 && i <= 16)
                    .apply();
        }
    }
}
