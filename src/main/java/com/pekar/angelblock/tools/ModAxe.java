package com.pekar.angelblock.tools;

import com.pekar.angelblock.utils.Utils;
import com.pekar.angelblock.tools.properties.DefaultMaterialProperties;
import com.pekar.angelblock.tools.properties.IMaterialProperties;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbility;

import java.util.List;

public class ModAxe extends AxeItem implements IModToolEnhanceable
{
    protected final IMaterialProperties materialProperties;
    protected final Utils utils = new Utils();

    public static ModAxe createPrimary(Tier material, float attackDamage, float attackSpeed, Properties properties)
    {
        return new ModAxe(material, attackDamage, attackSpeed, properties, new DefaultMaterialProperties());
    }

    public ModAxe(Tier material, float attackDamage, float attackSpeed, Properties properties, IMaterialProperties materialProperties)
    {
        super(material, properties.attributes(AxeItem.createAttributes(material, attackDamage, attackSpeed)));
        this.materialProperties = materialProperties;
    }

    @Override
    public boolean isTool()
    {
        return true;
    }

    @Override
    public TieredItem getTool()
    {
        return this;
    }

    @Override
    public IMaterialProperties getMaterialProperties()
    {
        return materialProperties;
    }

    @Override
    public void setDamage(ItemStack stack, int damage)
    {
        var modifiedDamage = Mth.clamp(damage, 0, stack.getMaxDamage() - getCriticalDurability());
        stack.set(DataComponents.DAMAGE, modifiedDamage);
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state)
    {
        return !hasCriticalDamage(stack) && super.isCorrectToolForDrops(stack, state);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state)
    {
        if (hasCriticalDamage(stack)) return 1F;
        return super.getDestroySpeed(stack, state);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility)
    {
        return !hasCriticalDamage(stack) && super.canPerformAction(stack, itemAbility);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {
        for (int i = 0; i <= 2; i++)
        {
            tooltipComponents.add(getDescription(i, false, false, i == 1));
        }
    }
}
