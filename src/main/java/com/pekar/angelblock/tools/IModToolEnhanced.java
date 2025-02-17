package com.pekar.angelblock.tools;

import com.pekar.angelblock.tools.properties.IMaterialProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;

public interface IModToolEnhanced extends IModTool
{

    IMaterialProperties getMaterialProperties();

    default boolean preventBlockBreak(Player player, LevelAccessor level, BlockPos pos)
    {
        if (!canPreventBlockDestroying(player, pos)) return false;

        var cancelBreaking = !getMaterialProperties().isSafeToBreak(player, pos);
        return cancelBreaking;
    }

    default boolean canPreventBlockDestroying(LivingEntity entity, BlockPos pos)
    {
        return isTool() && isEnhanced() && isToolEffective(entity, pos) && !entity.isShiftKeyDown();
    }
}
