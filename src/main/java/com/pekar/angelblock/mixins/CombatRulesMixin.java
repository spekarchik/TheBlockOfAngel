package com.pekar.angelblock.mixins;

import com.pekar.angelblock.Main;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CombatRules.class)
public class CombatRulesMixin
{
    @Inject(method = "getDamageAfterAbsorb", at = @At("HEAD"))
    private static void logDamageAbsorb(LivingEntity entity, float damage, DamageSource damageSource, float armorValue, float armorToughness, CallbackInfoReturnable<Float> cir) {
        Main.LOGGER.error("COMBAT RULES - Incoming: " + damage + " | Armor: " + armorValue + " | Toughness: " + armorToughness);
    }

    @Inject(method = "getDamageAfterAbsorb", at = @At("RETURN"))
    private static void logDamageAbsorbAfter(LivingEntity entity, float damage, DamageSource damageSource, float armorValue, float armorToughness, CallbackInfoReturnable<Float> cir) {
        Main.LOGGER.error("COMBAT RULES - After Absorb: " + cir.getReturnValue());
    }
}
