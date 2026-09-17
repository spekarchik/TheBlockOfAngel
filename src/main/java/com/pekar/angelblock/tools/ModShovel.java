package com.pekar.angelblock.tools;

import com.pekar.angelblock.tools.properties.DefaultMaterialProperties;
import com.pekar.angelblock.tools.properties.IMaterialProperties;
import com.pekar.angelblock.tooltip.ITooltip;
import com.pekar.angelblock.tooltip.TextStyle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

public class ModShovel extends ModMiningTool
{
    public static ModShovel createPrimary(ModToolMaterial material, float attackDamage, float attackSpeed, Properties properties)
    {
        return new ModShovel(material, attackDamage, attackSpeed, properties, new DefaultMaterialProperties());
    }

    public ModShovel(ModToolMaterial material, float attackDamage, float attackSpeed, Properties properties, IMaterialProperties materialProperties)
    {
        super(material, properties.shovel(material.getVanillaMaterial(), attackDamage, attackSpeed), materialProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        var result = super.useOn(context);
        if (result != InteractionResult.PASS) return result;

        var player = context.getPlayer();
        var level = player.level();
        var pos = context.getClickedPos();
        return onBlockProcessing(player, level, pos, pos, context.getHorizontalDirection())
                ? getToolInteractionResult(true, level.isClientSide())
                : result;
    }

    @Override
    public boolean canPerformAction(ItemInstance itemInstance, ItemAbility itemAbility)
    {
        return !hasCriticalDamage(itemInstance) && itemAbility == ItemAbilities.SHOVEL_DOUSE;
    }

    @Override
    public void addTooltip(ItemStack stack, TooltipContext context, ITooltip tooltip, TooltipFlag flag)
    {
        if (!utils.text.showExtendedDescription(tooltip, flag)) return;

        tooltip.ignoreEmptyLines();

        for (int i = 0; i <= 4; i++)
        {
            tooltip.addLine(getDescriptionId(), i).styledAs(TextStyle.DarkGray, i >= 2 && i <= 3).apply();
        }
    }

    protected boolean onBlockProcessing(Player player, Level level, BlockPos originalPos, BlockPos pos, Direction facing)
    {
        if (!level.isEmptyBlock(pos.above())) return false;

        var blockState = level.getBlockState(pos);
        Block block = blockState.getBlock();

        if (blockState.is(BlockTags.TURNS_INTO_DIRT_PATH) || block == Blocks.FARMLAND)
        {
            if (!level.isClientSide())
            {
                BlockState newBlockState = Blocks.DIRT_PATH.defaultBlockState();
                level.setBlock(pos, newBlockState, Block.UPDATE_ALL_IMMEDIATE);

                damageMainHandItemIfSurvivalIgnoreClient(player, level);
            }

            utils.sound.playSoundByBlock(player, pos, SoundEvents.SHOVEL_FLATTEN.value());

            return true;
        }

        return false;
    }
}
