package com.pekar.angelblock.armor;

import com.pekar.angelblock.Main;
import net.minecraft.world.item.equipment.ArmorType;
import net.neoforged.neoforge.registries.DeferredItem;

public class ArmorRegistry
{
    public static final DeferredItem<ModArmor> RENDELITHIC_HELMET = Main.ITEMS.register("rendelithic_helmet", () ->
            new RendelithicArmor(ModArmorMaterial.RENDELITHIC, ArmorType.HELMET));
    public static final DeferredItem<ModArmor> RENDELITHIC_CHESTPLATE = Main.ITEMS.register("rendelithic_chestplate", () ->
            new RendelithicArmor(ModArmorMaterial.RENDELITHIC, ArmorType.CHESTPLATE));
    public static final DeferredItem<ModArmor> RENDELITHIC_CHESTPLATE_WITH_LEVITATION = Main.ITEMS.register("rendelithic_chestplate_with_levitation", () ->
            new RendelithicArmor(ModArmorMaterial.RENDELITHIC2, ArmorType.CHESTPLATE).withSlowFalling());
    public static final DeferredItem<ModArmor> RENDELITHIC_LEGGINGS = Main.ITEMS.register("rendelithic_leggings", () ->
            new RendelithicArmor(ModArmorMaterial.RENDELITHIC, ArmorType.LEGGINGS));
    public static final DeferredItem<ModArmor> RENDELITHIC_LEGGINGS_WITH_HEALTH_REGENERATOR = Main.ITEMS.register("rendelithic_leggings_with_regenerator", () ->
            new RendelithicArmor(ModArmorMaterial.RENDELITHIC2, ArmorType.LEGGINGS).withHealthRegenerator());
    public static final DeferredItem<ModArmor> RENDELITHIC_BOOTS = Main.ITEMS.register("rendelithic_boots", () ->
            new RendelithicArmor(ModArmorMaterial.RENDELITHIC, ArmorType.BOOTS));
    public static final DeferredItem<ModArmor> RENDELITHIC_BOOTS_WITH_STRENGTH_BOOST = Main.ITEMS.register("rendelithic_boots_with_strength_boost", () ->
            new RendelithicArmor(ModArmorMaterial.RENDELITHIC2, ArmorType.BOOTS).withJumpBooster());

    public static final DeferredItem<ModArmor> LAPIS_HELMET = Main.ITEMS.register("lapis_helmet", () ->
            new ModArmor(ModArmorMaterial.LAPIS, ArmorType.HELMET));
    public static final DeferredItem<ModArmor> LAPIS_HELMET_WITH_DETECTOR = Main.ITEMS.register("lapis_helmet_with_detector", () ->
            new ModArmor(ModArmorMaterial.LAPIS2, ArmorType.HELMET).withDetector());
    public static final DeferredItem<ModArmor> LAPIS_CHESTPLATE = Main.ITEMS.register("lapis_chestplate", () ->
            new ModArmor(ModArmorMaterial.LAPIS, ArmorType.CHESTPLATE));
    public static final DeferredItem<ModArmor> LAPIS_CHESTPLATE_WITH_STRENGTH = Main.ITEMS.register("lapis_chestplate_with_strength", () ->
            new ModArmor(ModArmorMaterial.LAPIS2, ArmorType.CHESTPLATE).withStrengthBooster());
    public static final DeferredItem<ModArmor> LAPIS_LEGGINGS = Main.ITEMS.register("lapis_leggings", () ->
            new ModArmor(ModArmorMaterial.LAPIS, ArmorType.LEGGINGS));
    public static final DeferredItem<ModArmor> LAPIS_LEGGINGS_WITH_REGENERATOR = Main.ITEMS.register("lapis_leggings_with_regenerator", () ->
            new ModArmor(ModArmorMaterial.LAPIS2, ArmorType.LEGGINGS).withHealthRegenerator());
    public static final DeferredItem<ModArmor> LAPIS_BOOTS = Main.ITEMS.register("lapis_boots", () ->
            new ModArmor(ModArmorMaterial.LAPIS, ArmorType.BOOTS));
    public static final DeferredItem<ModArmor> LAPIS_BOOTS_WITH_SEA_POWER = Main.ITEMS.register("lapis_boots_with_sea_power", () ->
            new ModArmor(ModArmorMaterial.LAPIS2, ArmorType.BOOTS).withSeaPower());

    public static final DeferredItem<ModArmor> LIMONITE_HELMET = Main.ITEMS.register("limonite_helmet", () ->
            new LimoniteArmor(ModArmorMaterial.LIMONITE, ArmorType.HELMET));
    public static final DeferredItem<ModArmor> LIMONITE_HELMET_WITH_DETECTOR = Main.ITEMS.register("limonite_helmet_with_detector", () ->
            new LimoniteArmor(ModArmorMaterial.LIMONITE2, ArmorType.HELMET).withDetector());
    public static final DeferredItem<ModArmor> LIMONITE_CHESTPLATE = Main.ITEMS.register("limonite_chestplate", () ->
            new LimoniteArmor(ModArmorMaterial.LIMONITE, ArmorType.CHESTPLATE));
    public static final DeferredItem<ModArmor> LIMONITE_CHESTPLATE_WITH_LUCK = Main.ITEMS.register("limonite_chestplate_with_luck", () ->
            new LimoniteArmor(ModArmorMaterial.LIMONITE2, ArmorType.CHESTPLATE).withLuck());
    public static final DeferredItem<ModArmor> LIMONITE_LEGGINGS = Main.ITEMS.register("limonite_leggings", () ->
            new LimoniteArmor(ModArmorMaterial.LIMONITE, ArmorType.LEGGINGS));
    public static final DeferredItem<ModArmor> LIMONITE_LEGGINGS_WITH_REGENERATOR = Main.ITEMS.register("limonite_leggings_with_regenerator", () ->
            new LimoniteArmor(ModArmorMaterial.LIMONITE2, ArmorType.LEGGINGS).withHealthRegenerator());
    public static final DeferredItem<ModArmor> LIMONITE_BOOTS = Main.ITEMS.register("limonite_boots", () ->
            new LimoniteArmor(ModArmorMaterial.LIMONITE, ArmorType.BOOTS));
    public static final DeferredItem<ModArmor> LIMONITE_BOOTS_WITH_STRENGTH = Main.ITEMS.register("limonite_boots_with_strength", () ->
            new LimoniteArmor(ModArmorMaterial.LIMONITE2, ArmorType.BOOTS).withJumpBooster());

    public static final DeferredItem<ModArmor> DIAMITHIC_HELMET = Main.ITEMS.register("diamithic_helmet", () ->
            new ModArmor(ModArmorMaterial.DIAMITHIC, ArmorType.HELMET));
    public static final DeferredItem<ModArmor> DIAMITHIC_HELMET_WITH_DETECTOR = Main.ITEMS.register("diamithic_helmet_with_detector", () ->
            new ModArmor(ModArmorMaterial.DIAMITHIC2, ArmorType.HELMET).withDetector());
    public static final DeferredItem<ModArmor> DIAMITHIC_CHESTPLATE = Main.ITEMS.register("diamithic_chestplate", () ->
            new ModArmor(ModArmorMaterial.DIAMITHIC, ArmorType.CHESTPLATE));
    public static final DeferredItem<ModArmor> DIAMITHIC_CHESTPLATE_WITH_STRENGTH = Main.ITEMS.register("diamithic_chestplate_with_strength", () ->
            new ModArmor(ModArmorMaterial.DIAMITHIC2, ArmorType.CHESTPLATE).withStrengthBooster());
    public static final DeferredItem<ModArmor> DIAMITHIC_CHESTPLATE_WITH_LEVITATION = Main.ITEMS.register("diamithic_chestplate_with_levitation", () ->
            new ModArmor(ModArmorMaterial.DIAMITHIC2, ArmorType.CHESTPLATE).withSlowFalling());
    public static final DeferredItem<ModArmor> DIAMITHIC_CHESTPLATE_WITH_STRENGTH_AND_LEVITATION = Main.ITEMS.register("diamithic_chestplate_with_strength_and_levitation", () ->
            new ModArmor(ModArmorMaterial.DIAMITHIC2, ArmorType.CHESTPLATE).withStrengthBooster().withSlowFalling());
    public static final DeferredItem<ModArmor> DIAMITHIC_LEGGINGS = Main.ITEMS.register("diamithic_leggings", () ->
            new ModArmor(ModArmorMaterial.DIAMITHIC, ArmorType.LEGGINGS));
    public static final DeferredItem<ModArmor> DIAMITHIC_LEGGINGS_WITH_REGENERATOR = Main.ITEMS.register("diamithic_leggings_with_regenerator", () ->
            new ModArmor(ModArmorMaterial.DIAMITHIC2, ArmorType.LEGGINGS).withHealthRegenerator());
    public static final DeferredItem<ModArmor> DIAMITHIC_BOOTS = Main.ITEMS.register("diamithic_boots", () ->
            new ModArmor(ModArmorMaterial.DIAMITHIC, ArmorType.BOOTS));
    public static final DeferredItem<ModArmor> DIAMITHIC_BOOTS_WITH_STRENGTH = Main.ITEMS.register("diamithic_boots_with_strength", () ->
            new ModArmor(ModArmorMaterial.DIAMITHIC2, ArmorType.BOOTS).withJumpBooster());

    public static final DeferredItem<ModArmor> SUPER_HELMET = Main.ITEMS.register("super_helmet", () ->
            new SuperArmor(ModArmorMaterial.SUPER, ArmorType.HELMET));
    public static final DeferredItem<ModArmor> SUPER_HELMET_WITH_DETECTOR = Main.ITEMS.register("super_helmet_with_detector", () ->
            new SuperArmor(ModArmorMaterial.SUPER2, ArmorType.HELMET).withDetector());
    public static final DeferredItem<ModArmor> SUPER_CHESTPLATE = Main.ITEMS.register("super_chestplate", () ->
            new SuperArmor(ModArmorMaterial.SUPER, ArmorType.CHESTPLATE));
    public static final DeferredItem<ModArmor> SUPER_CHESTPLATE_FLYING = Main.ITEMS.register("super_chestplate_flying", () ->
            new SuperArmorFlying(ModArmorMaterial.SUPER2, ArmorType.CHESTPLATE));
    public static final DeferredItem<ModArmor> SUPER_LEGGINGS = Main.ITEMS.register("super_leggings", () ->
            new SuperArmor(ModArmorMaterial.SUPER, ArmorType.LEGGINGS));
    public static final DeferredItem<ModArmor> SUPER_LEGGINGS_WITH_REGENERATOR2 = Main.ITEMS.register("super_leggings_with_regenerator", () ->
            new SuperArmor(ModArmorMaterial.SUPER2, ArmorType.LEGGINGS).withHealthRegenerator());
    public static final DeferredItem<ModArmor> SUPER_BOOTS = Main.ITEMS.register("super_boots", () ->
            new SuperArmor(ModArmorMaterial.SUPER, ArmorType.BOOTS));
    public static final DeferredItem<ModArmor> SUPER_BOOTS_WITH_STRENGTH = Main.ITEMS.register("super_boots_with_strength", () ->
            new SuperArmor(ModArmorMaterial.SUPER2, ArmorType.BOOTS).withJumpBooster());
    public static final DeferredItem<ModArmor> SUPER_BOOTS_WITH_SEA_POWER = Main.ITEMS.register("super_boots_with_sea_power", () ->
            new SuperArmor(ModArmorMaterial.SUPER2, ArmorType.BOOTS).withSeaPower());
    public static final DeferredItem<ModArmor> SUPER_BOOTS_WITH_STRENGTH_AND_SEA_POWER = Main.ITEMS.register("super_boots_with_strength_and_sea_power", () ->
            new SuperArmor(ModArmorMaterial.SUPER2, ArmorType.BOOTS).withSeaPower().withJumpBooster());

    public static final DeferredItem<ModArmor> FLYING_HELMET = Main.ITEMS.register("flying_helmet", () ->
            new FlyingArmor(ModArmorMaterial.FLYING, ArmorType.HELMET));
    public static final DeferredItem<ModArmor> FLYING_CHESTPLATE = Main.ITEMS.register("flying_chestplate", () ->
            new FlyingArmor(ModArmorMaterial.FLYING, ArmorType.CHESTPLATE));
    public static final DeferredItem<ModArmor> FLYING_LEGGINGS = Main.ITEMS.register("flying_leggings", () ->
            new FlyingArmor(ModArmorMaterial.FLYING, ArmorType.LEGGINGS));
    public static final DeferredItem<ModArmor> FLYING_BOOTS = Main.ITEMS.register("flying_boots", () ->
            new FlyingArmor(ModArmorMaterial.FLYING, ArmorType.BOOTS));

    public static void initStatic()
    {
        // just to initialize static members
    }
}
