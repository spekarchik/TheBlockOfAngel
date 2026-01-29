package com.pekar.angelblock.mixins;

import com.pekar.angelblock.Main;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.common.damagesource.IReductionFunction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Mixin(DamageContainer.class)
public class DamageContainerMixin
{
    @Inject(method = "setReduction", at = @At("HEAD"))
    private void logReduction(DamageContainer.Reduction reduction, float amount, CallbackInfo ci) {
        Main.LOGGER.error("DAMAGE REDUCTION - Type: " + reduction + " | Amount: " + amount);
    }

    @Inject(method = "setNewDamage", at = @At("HEAD"))
    private void logSetNewDamage(float damage, CallbackInfo ci) {
        Main.LOGGER.error("SET NEW DAMAGE: " + damage/*, new Exception("STACK TRACE")*/);
    }

    @Inject(method = "modifyReduction", at = @At("HEAD"))
    private void logModifyReduction(DamageContainer.Reduction type, float reduction, CallbackInfoReturnable<Float> cir) {
        try {
            Field field = DamageContainer.class.getDeclaredField("reductionFunctions");
            field.setAccessible(true);
            Map<DamageContainer.Reduction, List<IReductionFunction>> reductionFuncs =
                    (Map<DamageContainer.Reduction, List<IReductionFunction>>) field.get(this);
            Main.LOGGER.error("REDUCTION FUNCTIONS - Type: " + type + " | Functions: " + reductionFuncs.getOrDefault(type, List.of()));

            Main.LOGGER.error("MODIFY REDUCTION - Type: " + type + " | Before: " + reduction);
            float modified = reduction;
            for (IReductionFunction func : reductionFuncs.getOrDefault(type, List.of())) {
                modified = func.modify((DamageContainer) (Object) this, modified);
                Main.LOGGER.error("REDUCTION FUNC: " + func.getClass().getName() + " | New Value: " + modified);
            }
            Main.LOGGER.error("MODIFY REDUCTION - After: " + modified);
        } catch (Exception e) {
            Main.LOGGER.error("ERROR ACCESSING reductionFunctions", e);
        }
    }

//    @Inject(method = "setNewDamage", at = @At("HEAD"), cancellable = true)
//    private void overrideSetNewDamage(float amount, CallbackInfo ci) {
//        if (amount == 2.5F) {
//            Main.LOGGER.error("BLOCKING setNewDamage(2.5)!");
//            ci.cancel(); // Отменяем фиксацию урона
//        }
//    }


//    @Inject(method = "modifyReduction", at = @At("HEAD"), cancellable = true)
//    private void logModifyReduction(DamageContainer.Reduction type, float reduction, CallbackInfoReturnable<Float> cir) {
//        Main.LOGGER.error("MODIFY REDUCTION - Type: " + type + " | Before: " + reduction);
//        float modified = reduction; // Запоминаем значение
//        for (IReductionFunction func : this.reductionFunctions.getOrDefault(type, List.of())) {
//            modified = func.modify((DamageContainer) (Object) this, modified);
//        }
//        Main.LOGGER.error("MODIFY REDUCTION - After: " + modified);
//        cir.setReturnValue(modified);
//    }
}
