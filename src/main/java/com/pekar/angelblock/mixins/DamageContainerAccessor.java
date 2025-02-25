package com.pekar.angelblock.mixins;

import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.common.damagesource.IReductionFunction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.EnumMap;
import java.util.List;

//@Mixin(DamageContainer.class)
public interface DamageContainerAccessor
{
    //@Accessor("reductionFunctions")
    EnumMap<DamageContainer.Reduction, List<IReductionFunction>> getReductionFunctions();
}
