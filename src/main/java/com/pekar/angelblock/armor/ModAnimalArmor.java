package com.pekar.angelblock.armor;

import com.pekar.angelblock.tooltip.ITooltipProvider;
import com.pekar.angelblock.utils.Utils;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AnimalArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public abstract class ModAnimalArmor extends AnimalArmorItem implements IModArmor, ITooltipProvider
{
    protected final int maxDamage;
    protected final ModArmorMaterial material;
    protected final Utils utils = new Utils();

    public ModAnimalArmor(ModArmorMaterial material, BodyType bodyType, Properties properties)
    {
        super(material.getMaterial(), bodyType, false, properties);
        this.maxDamage = Type.BODY.getDurability(material.getDurabilityMultiplier());
        this.material = material;
    }

    @Override
    public ModArmorMaterial getArmorMaterial()
    {
        return material;
    }

    @Override
    public String getArmorFamilyName()
    {
        return material.getMaterialName() + "_armor";
    }

    @Override
    public boolean isBroken(ItemStack stack)
    {
        return stack.getMaxDamage() - stack.getDamageValue() <= 1;
    }

    @Override
    public int getMaxDamage()
    {
        return components().getOrDefault(DataComponents.MAX_DAMAGE, maxDamage);
    }

    @Override
    public boolean canWalkOnPowderedSnow(ItemStack stack, LivingEntity wearer)
    {
        return false;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> component, TooltipFlag flag)
    {
        ITooltipProvider.appendHoverText(this, stack, context, component, flag);
    }
}
