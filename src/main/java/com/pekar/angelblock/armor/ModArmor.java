package com.pekar.angelblock.armor;

import com.pekar.angelblock.Utils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ModArmor extends ArmorItem
{
    protected final String armorModelName;
    protected final Utils utils = new Utils();

    protected ModArmor(ArmorMaterial material, Type equipmentSlot, String armorModelName)
    {
        super(material, equipmentSlot, new Properties());
        this.armorModelName = armorModelName;
    }

    public String getArmorModelName()
    {
        return armorModelName;
    }

    public boolean isModifiedWithDetector()
    {
        return false;
    }

    public boolean isModifiedWithHealthRegenerator()
    {
        return false;
    }

    public boolean isModifiedWithStrengthBooster()
    {
        return false;
    }

    public boolean isModifiedWithLevitation()
    {
        return false;
    }

    public boolean isModifiedWithSeaPower()
    {
        return false;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag)
    {
        for (int i = 1; i <= 9; i++)
        {
            components.add(getCommonDescription(i, i == 4, false, i == 3, false));
        }

        for (int i = 1; i <= getDescriptionLineCount(); i++)
        {
            components.add(getSpecificDescription(i, i == 1, false, getEquipmentSlot() == EquipmentSlot.FEET && i == 7, false));
        }
    }

    private int getDescriptionLineCount()
    {
        return switch (getEquipmentSlot())
        {
            case HEAD -> 4;
            case CHEST -> 7;
            case LEGS -> 5;
            case FEET -> 7;
            default -> 0;
        };
    }

    private MutableComponent getCommonDescription(int lineNumber, boolean isHeader, boolean isSubHeader, boolean isNotice, boolean isImportantNotice)
    {
        var component = getCommonDisplayName(lineNumber);
        return utils.getFormattedTextComponent(component, isHeader, isSubHeader, isNotice, isImportantNotice);
    }

    private MutableComponent getSpecificDescription(int lineNumber, boolean isHeader, boolean isSubHeader, boolean isNotice, boolean isImportantNotice)
    {
        var component = getSpecificDisplayName(lineNumber);
        return utils.getFormattedTextComponent(component, isHeader, isSubHeader, isNotice, isImportantNotice);
    }

    private MutableComponent getSpecificDisplayName(int lineNumber)
    {
        return Component.translatable(this.getDescriptionId() + ".desc" + lineNumber);
    }

    private MutableComponent getCommonDisplayName(int lineNumber)
    {
        return Component.translatable(material.getName().replace(':', '.').replaceAll("[0-9]", "") + ".desc" + lineNumber);
    }
}
