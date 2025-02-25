package com.pekar.angelblock.mixins;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LivingEntity.class)
public interface LivingEntityAccessor
{
    @Invoker("getDamageAfterArmorAbsorb")
    float callGetDamageAfterArmorAbsorb(DamageSource source, float amount);

    @Invoker("getDamageAfterMagicAbsorb")
    float callGetDamageAfterMagicAbsorb(DamageSource source, float amount);
}
