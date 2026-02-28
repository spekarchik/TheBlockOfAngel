package com.pekar.angelblock.armor;

import com.pekar.angelblock.tooltip.ITooltip;
import com.pekar.angelblock.tooltip.TextStyle;
import net.minecraft.core.HolderSet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class ModHorseArmor extends ModAnimalArmor
{
    public ModHorseArmor(ModArmorMaterial material, AnimalArmorType armorType, Properties properties)
    {
        super(material, armorType, material.getMaterial().animalProperties(properties, SoundEvents.HORSE_ARMOR, false, HolderSet.direct(EntityType::builtInRegistryHolder, EntityType.HORSE)));
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
