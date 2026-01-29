package com.pekar.angelblock.mixins;

import com.pekar.angelblock.Main;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin
{
    @Inject(method = "getDamageAfterArmorAbsorb", at = @At("HEAD"), cancellable = true)
    private void logArmorAbsorb(DamageSource source, float amount, CallbackInfoReturnable<Float> cir) {
        double armorValue = ((LivingEntity)(Object)this).getAttributeValue(Attributes.ARMOR);
        double toughness = ((LivingEntity)(Object)this).getAttributeValue(Attributes.ARMOR_TOUGHNESS);
        Main.LOGGER.error("ARMOR ABSORB - Incoming: " + amount + " | Armor: " + armorValue + " | Toughness: " + toughness);
    }

    @Inject(method = "getDamageAfterMagicAbsorb", at = @At("RETURN"))
    private void logMagicReductionAfter(DamageSource source, float amount, CallbackInfoReturnable<Float> cir) {
        Main.LOGGER.error("MAGIC ABSORB (AFTER) - Final: " + cir.getReturnValue());
    }

//    @Inject(method = "getDamageAfterMagicAbsorb", at = @At("HEAD"), cancellable = true)
//    private void logMagicAbsorb(DamageSource source, float amount, CallbackInfoReturnable<Float> cir) {
//        float magicProtection = EnchantmentHelper.getDamageProtection((ServerLevel) ((LivingEntity)(Object)this).level(), (LivingEntity)(Object)this, source);
//        Main.LOGGER.error("MAGIC ABSORB - Incoming: " + amount + " | Magic Protection: " + magicProtection);
//    }

//    @Inject(method = "hurt", at = @At("HEAD"))
//    private void logHurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
//        Main.LOGGER.error("LIVING HURT - Incoming Damage: " + amount, new Exception("STACK TRACE"));
////        Main.LOGGER.error("HURT CALLED - Incoming Damage: " + amount);
//    }

    @Inject(method = "hurt", at = @At("HEAD"))
    private void logLivingHurtBefore(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        Main.LOGGER.error("LIVING HURT (BEFORE SUPER) - Incoming Damage: " + amount);
    }

    @Inject(method = "hurt", at = @At("TAIL"))
    private void logLivingHurtAfter(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        Main.LOGGER.error("LIVING HURT (AFTER SUPER) - Damage: " + amount);
    }

//    @Inject(method = "hurt", at = @At("HEAD"))
//    private void trackLivingHurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
//        Main.LOGGER.error("LIVING HURT START - Damage: " + amount, new Exception("Stack Trace"));
//    }

    @Inject(method = "hurt", at = @At("RETURN"))
    private void trackLivingHurtAfter(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        Main.LOGGER.error("LIVING HURT END - Damage: " + amount);
    }
}
