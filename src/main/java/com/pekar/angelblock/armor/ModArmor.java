package com.pekar.angelblock.armor;

import com.pekar.angelblock.tooltip.ITooltipProvider;
import com.pekar.angelblock.utils.Utils;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public abstract class ModArmor extends ArmorItem implements ITooltipProvider
{
    protected final ArmorItem.Type armorItemType;
    protected final int maxDamage;
    protected final ModArmorMaterial material;
    protected final Utils utils = new Utils();

    public ModArmor(ModArmorMaterial material, ArmorItem.Type armorItemType, Properties properties)
    {
        super(material.getMaterial(), armorItemType, material.isFireResistant() ? properties.fireResistant() : properties);
        this.armorItemType = armorItemType;
        this.maxDamage = armorItemType.getDurability(material.getDurabilityMultiplier());
        this.material = material;
    }

    public ModArmorMaterial getArmorMaterial()
    {
        return material;
    }

    public String getArmorFamilyName()
    {
        return material.getMaterialName() + "_armor";
    }

    public boolean isBroken(ItemStack stack)
    {
        return stack.getMaxDamage() - stack.getDamageValue() <= 1;
    }

    public int getMaxDamage()
    {
        return components().getOrDefault(DataComponents.MAX_DAMAGE, maxDamage);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> component, TooltipFlag flag)
    {
        ITooltipProvider.appendHoverText(this, stack, context, component, flag);
    }
}
