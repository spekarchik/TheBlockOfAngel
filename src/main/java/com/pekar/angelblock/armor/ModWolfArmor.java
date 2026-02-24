package com.pekar.angelblock.armor;

import com.pekar.angelblock.tooltip.ITooltip;
import com.pekar.angelblock.tooltip.TextStyle;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.equipment.ArmorType;

public class ModWolfArmor extends ModArmor
{
    public ModWolfArmor(ModArmorMaterial material, ArmorType armorItemType, Properties properties)
    {
        super(material, armorItemType, properties.wolfArmor(material.getMaterial()));
    }

    @Override
    public void addTooltip(ItemStack stack, TooltipContext context, ITooltip tooltip, TooltipFlag flag)
    {
        if (!flag.hasShiftDown())
        {
            tooltip.addLineById("description.press_shift").apply();
            return;
        }

        if (flag.hasShiftDown())
        {
            tooltip.ignoreEmptyLines();

            for (int i = 1; i <= 20; i++)
            {
                tooltip.addLine(getDescriptionId(), i)
                        .styledAs(TextStyle.Header, i == 7)
                        .apply();
            }

            tooltip.addEmptyLine();
            tooltip.addLine(getDescriptionId(), 21).asDarkGrey().apply();
        }
    }
}
