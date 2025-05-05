package com.pekar.angelblock.tools;

import com.pekar.angelblock.tooltip.ITooltip;
import com.pekar.angelblock.tooltip.ITooltipProvider;
import com.pekar.angelblock.tooltip.TextStyle;
import com.pekar.angelblock.utils.Utils;
import com.pekar.angelblock.tools.properties.DefaultMaterialProperties;
import com.pekar.angelblock.tools.properties.IMaterialProperties;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

import java.util.List;

public class ModPickaxe extends ModTool implements IModToolEnhanceable, ITooltipProvider
{
    protected final IMaterialProperties materialProperties;
    protected final Utils utils = new Utils();
    private final ModToolMaterial material;

    public static ModPickaxe createPrimary(ModToolMaterial material, float attackDamage, float attackSpeed, Properties properties)
    {
        return new ModPickaxe(material, attackDamage, attackSpeed, properties, new DefaultMaterialProperties());
    }

    public ModPickaxe(ModToolMaterial material, float attackDamage, float attackSpeed, Properties properties, IMaterialProperties materialProperties)
    {
        super(material, BlockTags.MINEABLE_WITH_PICKAXE, attackDamage, attackSpeed, properties);
        this.materialProperties = materialProperties;
        this.material = material;
    }

    @Override
    public boolean isTool()
    {
        return true;
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
    public float getDestroySpeed(ItemStack stack, BlockState state)
    {
        if (hasCriticalDamage(stack)) return 1F;
        return super.getDestroySpeed(stack, state);
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state)
    {
        return !hasCriticalDamage(stack) && super.isCorrectToolForDrops(stack, state);
    }

    @Override
    public ModToolMaterial getMaterial()
    {
        return material;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility)
    {
        return !hasCriticalDamage(stack) && ItemAbilities.DEFAULT_PICKAXE_ACTIONS.contains(itemAbility);
    }

    @Override
    public final void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {
        ITooltipProvider.appendHoverText(this, stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    public void addTooltip(ItemStack stack, TooltipContext context, ITooltip tooltip, TooltipFlag flag)
    {
        if (!utils.text.showExtendedDescription(tooltip)) return;

        for (int i = 0; i <= 2; i++)
        {
            tooltip.addLine(getDescriptionId(), i).styledAs(TextStyle.DarkGray, i == 1).apply();
        }
    }
}
