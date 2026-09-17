package com.pekar.angelblock.tools;

import com.pekar.angelblock.tools.properties.IMaterialProperties;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public abstract class ModMiningTool extends ModTool implements IModToolEnhanceable
{
    protected final IMaterialProperties materialProperties;
    private final ModToolMaterial material;

    protected ModMiningTool(ModToolMaterial material, Properties properties, IMaterialProperties materialProperties)
    {
        super(material, properties);
        this.materialProperties = materialProperties;
        this.material = material;
    }

    @Override
    public final boolean isTool()
    {
        return true;
    }

    @Override
    public final IMaterialProperties getMaterialProperties()
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
    public final ModToolMaterial getMaterial()
    {
        return material;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state)
    {
        return hasCriticalDamage(stack) ? 1F : super.getDestroySpeed(stack, state);
    }
}
