package com.pekar.angelblock.potions;

import com.pekar.angelblock.Main;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.registries.RegistryObject;

public class PotionRegistry
{
    public static final RegistryObject<MobEffect> BLOCK_BREAKER_EFFECT = Main.MOB_EFFECTS.register("block_breaker_effect",
            () -> new BlockBreakerMobEffect(MobEffectCategory.NEUTRAL, 0xFF22FF));
    public static final RegistryObject<Potion> BLOCK_BREAKER_POTION = Main.POTIONS.register("block_breaker_potion",
            () -> new Potion(new MobEffectInstance(BLOCK_BREAKER_EFFECT.get())));
//    public static final Potion FIRE_MODE_POTION = new FireModePotion();
//    public static final Potion EXPLOSION_MODE_POTION = new ExplosionModePotion();
//    public static final Potion SINGLE_MODE_POTION = new SingleModePotion();

    public static void initStatic()
    {
        // just to initialize static members
    }
}
