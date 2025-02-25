package com.pekar.angelblock.mixins;

import com.pekar.angelblock.Main;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(Player.class)
public class PlayerMixin
{
    @Inject(method = "hurt", at = @At("HEAD"))
    private void logPlayerHurtStart(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        Main.LOGGER.error("------ PLAYER HURT START - Initial Damage: " + amount + " ----------------");
        var player = (Player)(Object) this;
        var armorAttribute = player.getAttribute(Attributes.ARMOR);
        Main.LOGGER.error("      Armor::: " + armorAttribute.getValue());
    }

//    @Inject(method = "hurt", at = @At(value = "INVOKE",
//            target = "Lnet/minecraft/world/entity/LivingEntity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z", shift = At.Shift.BEFORE))
//    private void logPlayerHurtBeforeSuper(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
//        Main.LOGGER.error("PLAYER HURT (BEFORE CALLING SUPER) - Damage: " + amount, new Exception("Stack Trace"));
//    }

//    @Inject(method = "hurt", at = @At("TAIL"))
//    private void logPlayerHurtAfter(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
//        Main.LOGGER.error("PLAYER HURT (AFTER SUPER) - Damage: " + amount);
//    }
//
//    @Inject(method = "hurt", at = @At(value = "INVOKE",
//            target = "Lnet/minecraft/world/entity/LivingEntity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
//    private void logBeforeLivingHurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
//        Main.LOGGER.error("PLAYER HURT (BEFORE CALLING SUPER) - Damage: " + amount);
//    }
//
//    @Inject(method = "hurt", at = @At(value = "INVOKE",
//            target = "Lnet/minecraft/world/entity/LivingEntity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
//    private void logFinalDamageBeforeReturn(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
//        Main.LOGGER.error("FINAL DAMAGE BEFORE RETURN: " + amount);
//    }


//    @Inject(method = "hurt", at = @At("RETURN"))
//    private void logPlayerHurtFinal(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
//        Main.LOGGER.error("PLAYER HURT (FINAL) - Damage: " + amount);
//    }

//    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
//    private void overridePlayerHurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
//        if (amount == 2.5F) {
//            new Exception("Stack Trace");
//            float correctedDamage = 0.625F; // Используем правильное значение
//            Main.LOGGER.error("OVERRIDING PLAYER HURT: 2.5 → " + correctedDamage);
//
//            // Вызываем оригинальный метод, но с исправленным значением
//            ((LivingEntity) (Object) this).hurt(source, correctedDamage);
//            cir.setReturnValue(true);
//        }
//    }
//
//    @Inject(method = "hurt", at = @At("RETURN"), cancellable = true)
//    private void fixPlayerHurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
//        if (amount == 2.5F) {
//            new Exception("Stack Trace");
//            float correctedDamage = 0.625F;
//            Main.LOGGER.error("FIXING PLAYER HURT: Overriding 2.5 → " + correctedDamage);
//            cir.setReturnValue(((LivingEntity) (Object) this).hurt(source, correctedDamage));
//        }
//    }

//    @Inject(method = "hurt", at = @At("HEAD"))
//    private void trackPlayerHurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
//        Main.LOGGER.error("PLAYER HURT START - Damage: " + amount, new Exception("Stack Trace"));
//    }

//    @Inject(method = "hurt", at = @At("RETURN"))
//    private void trackPlayerHurtAfter(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
//        Main.LOGGER.error("PLAYER HURT END - Damage: " + amount);
//    }


//    @Inject(method = "actuallyHurt", at = @At("HEAD"))
//    private void onActuallyHurt(DamageSource damageSource, float damageAmount, CallbackInfo ci) {
//        Main.LOGGER.error("ACTUALLY HURT - Final Damage: " + damageAmount);
//    }

//    @Inject(method = "actuallyHurt", at = @At("HEAD"))
//    private void logDamageCalculation(DamageSource damageSource, float damageAmount, CallbackInfo ci) {
//        float armorReduced = ((LivingEntityAccessor)(Object)this).callGetDamageAfterArmorAbsorb(damageSource, damageAmount);
//        float magicReduced = ((LivingEntityAccessor)(Object)this).callGetDamageAfterMagicAbsorb(damageSource, damageAmount);
//
//        Main.LOGGER.error("ACTUALLY HURT - Initial Damage: " + damageAmount);
//        Main.LOGGER.error("DAMAGE AFTER ARMOR ABSORB: " + armorReduced);
//        Main.LOGGER.error("DAMAGE AFTER MAGIC ABSORB: " + magicReduced);
//    }
}
