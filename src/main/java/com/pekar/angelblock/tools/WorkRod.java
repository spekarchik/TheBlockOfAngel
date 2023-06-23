package com.pekar.angelblock.tools;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WorkRod extends ModRod
{
    public WorkRod(Tier material, int attackDamage, float attackSpeed, boolean isMagnetic, Properties properties)
    {
        super(material, attackDamage, attackSpeed, isMagnetic, properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag)
    {
        for (int i = 1; i <= 3; i++)
        {
            var component = getDisplayName(i).withStyle(ChatFormatting.GRAY);
            if (i > 2)
                component.withStyle(ChatFormatting.ITALIC);
            components.add(component);
        }
    }
}
