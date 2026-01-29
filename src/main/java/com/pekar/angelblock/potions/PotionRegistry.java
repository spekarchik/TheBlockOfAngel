package com.pekar.angelblock.potions;

import com.pekar.angelblock.Main;
import com.pekar.angelblock.utils.Utils;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.Supplier;

public class PotionRegistry
{
    // Mob Effects
    public static final Holder<MobEffect> SWORD_FIRE_MODE_EFFECT = registerMobEffect("sword_fire_mode_effect", SwordFireModeMobEffect::new);

    public static final Holder<MobEffect> SWORD_EXPLOSION_MODE_EFFECT = registerMobEffect("sword_explosion_mode_effect", SwordExplosionModeMobEffect::new);

    public static final Holder<MobEffect> SWORD_WEB_MODE_EFFECT = registerMobEffect("sword_web_mode_effect", SwordWebModeMobEffect::new);

    public static final Holder<MobEffect> TOOL_ADVANCED_MODE_EFFECT = registerMobEffect("tool_advanced_mode_effect", ToolAdvancedModeMobEffect::new);

    public static final Holder<MobEffect> ROD_MAGNETIC_MODE_EFFECT = registerMobEffect("rod_magnetic_mode_effect", RodMagneticModeEffect::new);

    public static final Holder<MobEffect> ARMOR_SUPER_JUMP_MODE_EFFECT = registerMobEffect("armor_super_jump_mode_effect", SuperJumpModeEffect::new);

    public static final Holder<MobEffect> ARMOR_HEAVY_JUMP_EFFECT = registerMobEffect("armor_heavy_jump_effect", HeavyJumpEffect::new);

    public static final Holder<MobEffect> BIOS_DIAMOND_COOLDOWN_EFFECT = registerMobEffect("bios_diamond_cooldown_effect", CooldownEffect::new);

    public static final Holder<MobEffect> END_SAPPHIRE_COOLDOWN_EFFECT = registerMobEffect("end_sapphire_cooldown_effect", CooldownEffect::new);

    public static final Holder<MobEffect> ELDER_GUARDIAN_EYE_EFFECT = registerMobEffect("elder_guardian_eye_effect", ElderGuardianEyeEffect::new);

    public static final Holder<MobEffect> ENERGY_CRYSTAL_EFFECT = registerMobEffect("energy_crystal_effect", EnergyCrystalEffect::new);

    public static final DeferredHolder<EntityType<?>, EntityType<BlockBreakerPotion>> BLOCK_BREAKER_POTION =
            registerThrownPotion("block_breaker_potion", BlockBreakerPotion::new);

//    public static final DeferredRegister<EntityType<Snowball>> BLOCK_BREAKER_POTION = Main.ENTITY_TYPES.register("block_breaker_potion",
//            EntityType.Builder.of(Snowball::new, MobCategory.MISC).noLootTable().sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10));


    public static void initStatic()
    {
        // just to initialize static members
    }

    private static Holder<Potion> registerPotion(String name, Supplier<Potion> potion)
    {
        return Main.POTIONS.register(name, potion);
    }

    private static Holder<MobEffect> registerMobEffect(String name, Supplier<MobEffect> mobEffect)
    {
        return Main.MOB_EFFECTS.register(name, mobEffect);
    }

    private static DeferredHolder<EntityType<?>, EntityType<BlockBreakerPotion>> registerThrownPotion(String name, EntityType.EntityFactory<BlockBreakerPotion> potionFactory)
    {
        var location = Utils.instance.resources.createResourceLocation(Main.MODID, name);
        var resourceKey = ResourceKey.create(Registries.ENTITY_TYPE, location);

        return Main.ENTITY_TYPES.register(name, () -> EntityType.Builder.of(potionFactory, MobCategory.MISC)
                .noLootTable()
                .sized(0.25F, 0.25F) // Entity size
                .clientTrackingRange(4).updateInterval(10)
                .build(resourceKey));
    }
}
