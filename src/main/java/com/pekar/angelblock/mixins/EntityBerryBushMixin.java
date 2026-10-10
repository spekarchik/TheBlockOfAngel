package com.pekar.angelblock.mixins;

import com.pekar.angelblock.armor.ArmorRegistry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityBerryBushMixin
{
    @Unique
    private static final double lymonitehorsearmor$slowdownStrength = 0.5;

    @Unique
    private Vec3 lymonitehorsearmor$berryBushMultiplier;

    @Inject(method = "makeStuckInBlock", at = @At("HEAD"), cancellable = true)
    private void preserveArmoredHorseMomentum(BlockState state, Vec3 multiplier, CallbackInfo ci)
    {
        if (state.is(Blocks.SWEET_BERRY_BUSH) && (Object) this instanceof Horse horse
                && horse.getBodyArmorItem().is(ArmorRegistry.HORSE_LYMONITE_ARMOR))
        {
            // Vanilla's stuck-block path also clears velocity in Entity.move.
            // Store a separate multiplier so other stuck blocks retain their normal behavior.
            lymonitehorsearmor$berryBushMultiplier = multiplier;
            horse.resetFallDistance();
            ci.cancel();
        }
    }

    @ModifyVariable(method = "move", at = @At("HEAD"), argsOnly = true)
    private Vec3 applyReducedBerryBushSlowdown(Vec3 movement, MoverType moverType, Vec3 originalMovement)
    {
        if (moverType != MoverType.SELF || lymonitehorsearmor$berryBushMultiplier == null)
        {
            return movement;
        }

        var multiplier = lymonitehorsearmor$berryBushMultiplier;
        lymonitehorsearmor$berryBushMultiplier = null;
        if ((Object) this instanceof Horse horse && horse.getBodyArmorItem().is(ArmorRegistry.HORSE_LYMONITE_ARMOR))
        {
            // Vanilla clears horizontal velocity each tick. On the ground its steady speed
            // is the acceleration times the bush multiplier, whereas normal travel also
            // retains momentum through ground friction and air drag. Include that lost
            // momentum when halving the speed reduction, rather than only halving 20%.
            double vanillaHorizontal = multiplier.x;
            if (horse.onGround())
            {
                var belowPos = horse.getBlockPosBelowThatAffectsMyMovement();
                float blockFriction = horse.level().getBlockState(belowPos).getFriction(horse.level(), belowPos, horse);
                // 1.21.1 applies fixed air drag after the block's ground friction.
                double retention = horse.shouldDiscardFriction() ? 1.0 : blockFriction * 0.91F;
                vanillaHorizontal *= 1.0 - retention;
            }
            double horizontal = 1.0 - (1.0 - vanillaHorizontal) * lymonitehorsearmor$slowdownStrength;
            double vertical = 1.0 - (1.0 - multiplier.y) * lymonitehorsearmor$slowdownStrength;
            return movement.multiply(horizontal, vertical, horizontal);
        }
        return movement;
    }
}
