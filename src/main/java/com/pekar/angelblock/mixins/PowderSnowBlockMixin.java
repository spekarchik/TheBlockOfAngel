package com.pekar.angelblock.mixins;

import com.pekar.angelblock.armor.ModArmor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.block.PowderSnowBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PowderSnowBlock.class)
public abstract class PowderSnowBlockMixin
{

    @Inject(
            method = "canEntityWalkOnPowderSnow",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void canWalkOnPowderedSnow(Entity entity, CallbackInfoReturnable<Boolean> cir)
    {
        if (entity instanceof Animal animal)
        {
            var armor = animal.getBodyArmorItem();
            if (armor.getItem() instanceof ModArmor modArmor)
            {
                var returnValue = modArmor.canWalkOnPowderedSnow(armor, animal);
                cir.setReturnValue(returnValue);
            }
        }
    }
}