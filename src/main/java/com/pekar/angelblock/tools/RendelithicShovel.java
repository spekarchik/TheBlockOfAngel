package com.pekar.angelblock.tools;

import com.pekar.angelblock.blocks.BlockRegistry;
import com.pekar.angelblock.tools.properties.RendelithicMaterialProperties;
import com.pekar.angelblock.tooltip.ITooltip;
import com.pekar.angelblock.tooltip.TextStyle;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public final class RendelithicShovel extends EnhancedShovel
{

    public RendelithicShovel(ModToolMaterial material, float attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage, attackSpeed, properties, new RendelithicMaterialProperties());
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        var result = super.useOn(context);

        if (result == InteractionResult.FAIL) return result;

        var player = context.getPlayer();
        var level = player.level();

        var pos = context.getClickedPos();
        BlockState blockState = level.getBlockState(pos);
        var block = blockState.getBlock();

        if (block == Blocks.END_STONE)
        {
            if (!level.isClientSide)
                setBlock(player, pos, BlockRegistry.CRACKED_ENDSTONE.get());

            return getToolInteractionResult(true, level.isClientSide());
        }

        return result;
    }

    @Override
    public void addTooltip(ItemStack stack, TooltipContext context, ITooltip tooltip, TooltipFlag flag)
    {
        if (!utils.text.showExtendedDescription(tooltip)) return;

        for (int i = 0; i <= 8; i++)
        {
            tooltip.addLine(getDescriptionId(), i).styledAs(TextStyle.Header, i == 1 || i == 5).styledAs(TextStyle.Notice, i == 3).styledAs(TextStyle.DarkGray, i == 7).apply();
        }
    }
}
