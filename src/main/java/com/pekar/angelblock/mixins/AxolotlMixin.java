package com.pekar.angelblock.mixins;

import com.pekar.angelblock.armor.ArmorRegistry;
import com.pekar.angelblock.armor.ModArmor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Axolotl.class)
public abstract class AxolotlMixin extends Animal
{
    private static final int BREEDING_INFLUENCE_RADIUS = 8;

    protected AxolotlMixin(EntityType<? extends Animal> type, Level level)
    {
        super(type, level);
    }

    @Inject(method = "getBreedOffspring", at = @At("HEAD"), cancellable = true)
    private void injectBreedOffspring(ServerLevel level, AgeableMob partner, CallbackInfoReturnable<AgeableMob> cir)
    {
        Axolotl axolotl = (Axolotl) (Object) this;
        Axolotl offspring = EntityType.AXOLOTL.create(level);
        if (offspring != null)
        {
            Axolotl.Variant variant;

            boolean useRare = axolotlUseRareVariant(level, partner);
            if (useRare)
            {
                variant = Axolotl.Variant.getRareSpawnVariant(axolotl.getRandom());
            }
            else
            {
                variant = axolotl.getRandom().nextBoolean() ? axolotl.getVariant() : ((Axolotl) partner).getVariant();
            }

            offspring.setVariant(variant);
            offspring.setPersistenceRequired();
        }

        cir.setReturnValue(offspring);
    }

    private boolean axolotlUseRareVariant(ServerLevel level, AgeableMob partner)
    {
        RandomSource random = this.getRandom();
        var entities = level.getEntitiesOfClass(LivingEntity.class, partner.getBoundingBox().inflate(BREEDING_INFLUENCE_RADIUS));
        for (var entity : entities)
        {
            if (isWearingSpecialArmor(entity))
            {
                return random.nextInt(100) == 0;
            }
        }

        return random.nextInt(1200) == 0;
    }

    private boolean isWearingSpecialArmor(LivingEntity player)
    {
        for (var armor : player.getArmorSlots())
        {
            if (armor.isEmpty()) return false;
            if (!(armor.getItem() instanceof ModArmor modArmor)) return false;
            if (!modArmor.getArmorFamilyName().equals(ArmorRegistry.LAPIS_BOOTS.get().getArmorFamilyName())) return false;
        }

        return true;
    }
}
