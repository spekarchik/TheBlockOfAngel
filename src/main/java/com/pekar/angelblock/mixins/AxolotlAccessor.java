package com.pekar.angelblock.mixins;

import net.minecraft.world.entity.animal.axolotl.Axolotl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Axolotl.class)
public interface AxolotlAccessor
{
    @Invoker("setVariant")
    void invokeSetVariant(Axolotl.Variant variant);
}
