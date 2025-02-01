package com.pekar.angelblock.armor;

import com.pekar.angelblock.Utils;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ModArmor extends ArmorItem
{
    protected final String materialName;
    protected final ArmorItem.Type armorItemType;
    protected final int maxDamage;
    protected final ModArmorMaterial material;
    protected final Utils utils = new Utils();

    protected ModArmor(ModArmorMaterial material, Type armorItemType)
    {
        super(material.getMaterial(), armorItemType, new Properties().durability(armorItemType.getDurability(material.getDurabilityMultiplier())));
        this.material = material;
        this.armorItemType = armorItemType;
        this.materialName = material.getMaterialName();
        this.maxDamage = armorItemType.getDurability(material.getDurabilityMultiplier());
    }

    public ModArmorMaterial getArmorMaterial()
    {
        return material;
    }

    public String getMaterialName()
    {
        return materialName;
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

    public int getMaxDamage()
    {
        return components().getOrDefault(DataComponents.MAX_DAMAGE, maxDamage);
    }

    public int getDamage()
    {
        return components().getOrDefault(DataComponents.DAMAGE, 0);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag tooltipFlag)
    {
        for (int i = 1; i <= 10; i++)
        {
            components.add(getCommonDescription(i, i == 5, false, i == 4, false));
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
        return Component.translatable(material.getFullArmorModelName().replace(':', '.').replaceAll("[0-9]", "") + ".desc" + lineNumber);
    }
}
