package com.pekar.angelblock.tools;

import com.pekar.angelblock.text.ITooltipProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public interface IModTool extends IDamageable, IToolService, ITooltipProvider
{
    default boolean isTool()
    {
        return false;
    }

    default boolean isWeapon()
    {
        return false;
    }

    default boolean isRod()
    {
        return false;
    }

    default boolean isEnhanced()
    {
        return false;
    }

    boolean isCorrectToolForDrops(ItemStack stack, BlockState state);
    ModToolMaterial getMaterial();
    String getMaterialName();
}
