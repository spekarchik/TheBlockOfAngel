package com.pekar.angelblock.tools;

import com.pekar.angelblock.tooltip.ITooltipProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public abstract class WorkRod extends ModRod implements ITooltipProvider
{
    public WorkRod(ModToolMaterial material, Properties properties)
    {
        super(material, false, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {
        ITooltipProvider.appendHoverText(this, stack, context, tooltipComponents, tooltipFlag);
    }

    protected void causePlayerExhaustion(Player player)
    {
        utils.player.causePlayerExhaustion(player, 2);
    }
}
