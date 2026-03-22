package com.pekar.angelblock.armor;

import com.pekar.angelblock.tooltip.ITooltip;
import com.pekar.angelblock.tooltip.TextStyle;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.equipment.ArmorType;

public class ModHorseArmor extends ModAnimalArmor
{
    public ModHorseArmor(ModArmorMaterial material, ArmorType armorSlotType, AnimalArmorType armorType, Properties properties)
    {
        super(material, armorSlotType, armorType, properties.horseArmor(material.getMaterial()));
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

            for (int i = 0; i <= 20; i++)
            {
                tooltip.addLine(getDescriptionId(), i)
                        .styledAs(TextStyle.Notice, i == 0)
                        .styledAs(TextStyle.Header, i == 7)
                        .apply();
            }
        }
    }
}
