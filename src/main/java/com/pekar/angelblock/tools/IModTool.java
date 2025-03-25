package com.pekar.angelblock.tools;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public interface IModTool extends IModDescriptionItem, IDamageable, IToolService
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

    IModTool getTool();

    boolean isCorrectToolForDrops(ItemStack stack, BlockState state);

    String getDescriptionId();

    ModToolMaterial getMaterial();

    default String getMaterialName()
    {
        if (getTool().getMaterial() instanceof ModToolMaterial toolMaterial)
        {
            return toolMaterial.getName();
        }

        return "";
    }

    default boolean isToolEffective(LivingEntity entityLiving, BlockPos pos)
    {
        BlockState blockState = entityLiving.level().getBlockState(pos);
        return getTool().isCorrectToolForDrops(entityLiving.getMainHandItem(), blockState);
    }

    @Override
    default MutableComponent getDisplayName(int lineNumber)
    {
        return Component.translatable(getTool().getDescriptionId() + ".desc" + lineNumber);
    }
}
