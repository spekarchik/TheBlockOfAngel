package com.pekar.angelblock.tools;

import com.pekar.angelblock.tools.properties.DefaultMaterialProperties;
import com.pekar.angelblock.tools.properties.IMaterialProperties;
import com.pekar.angelblock.tooltip.ITooltip;
import com.pekar.angelblock.tooltip.TextStyle;
import com.pekar.angelblock.utils.SoundType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class ModHoe extends ModMiningTool
{
    private static final int USE_MAGIC_EXHAUSTION_MULTIPLIER = 16;

    public static ModHoe createPrimary(ModToolMaterial material, float attackDamage, float attackSpeed, Properties properties)
    {
        return new ModHoe(material, attackDamage, attackSpeed, properties, new DefaultMaterialProperties());
    }

    public ModHoe(ModToolMaterial material, float attackDamage, float attackSpeed, Properties properties, IMaterialProperties materialProperties)
    {
        super(material, properties.hoe(material.getVanillaMaterial(), attackDamage, attackSpeed), materialProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        var result = super.useOn(context);
        if (result == InteractionResult.FAIL) return result;

        var player = context.getPlayer();
        var level = player.level();

//        if (level.isClientSide) return result;

        var pos = context.getClickedPos();
        BlockPos upPos = pos.above();

        if ((level.isWaterAt(upPos) && !level.getBlockState(upPos).getFluidState().isSource()) || ((level.isEmptyBlock(upPos))
                && ((utils.blocks.types.allowsWaterPlacementBetween(level, upPos.north()) && utils.blocks.types.allowsWaterPlacementBetween(level, upPos.south()))
                || (utils.blocks.types.allowsWaterPlacementBetween(level, upPos.east()) && utils.blocks.types.allowsWaterPlacementBetween(level, upPos.west())))))
        {
            boolean isBrokenOrPlayerExhausted = hasCriticalDamage(context.getItemInHand()) || player.getFoodData().getFoodLevel() <= 0;

            if (!isBrokenOrPlayerExhausted)
            {
                if (!level.isClientSide())
                {
                    level.setBlock(upPos, Blocks.WATER.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
                    damageMainHandItemIfSurvivalIgnoreClient(player, level); // pos, not upPos
                    utils.player.causePlayerExhaustion(player, USE_MAGIC_EXHAUSTION_MULTIPLIER);
                }

                utils.sound.playSoundByBlock(player, pos, SoundType.WATER_PLACED);
            }

            return getToolInteractionResult(!isBrokenOrPlayerExhausted, level.isClientSide());
        }
        else
        {
            if (level.isEmptyBlock(upPos) && context.getClickedFace() == Direction.UP)
                return changePodzolToDirt(player, level, pos) ? getToolInteractionResult(true, level.isClientSide()) : result;
        }

        return result;
    }

    @Override
    public void addTooltip(ItemStack stack, TooltipContext context, ITooltip tooltip, TooltipFlag flag)
    {
        if (!utils.text.showExtendedDescription(tooltip, flag)) return;

        tooltip.ignoreEmptyLines();

        for (int i = 0; i <= 6; i++)
        {
            tooltip.addLine(getDescriptionId(), i)
                    .styledAs(TextStyle.Header, i == 1)
                    .styledAs(TextStyle.DarkGray, i >= 4 && i <= 5)
                    .apply();
        }
    }

    protected boolean changePodzolToDirt(Player player, Level level, BlockPos pos)
    {
        var blockState = level.getBlockState(pos);
        var block = blockState.getBlock();

        if (block == Blocks.PODZOL)
        {
            if (!level.isClientSide())
            {
                setBlockWithClientSound(player, pos, Blocks.DIRT);
                damageMainHandItemIfSurvivalIgnoreClient(player, level);
            }

            utils.sound.playSoundByBlock(player, pos, SoundEvents.HOE_TILL.value());

            return true;
        }

        return false;
    }
}
