package com.pekar.angelblock.armor;

import com.pekar.angelblock.utils.TriFunction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.Unit;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorType;
import net.neoforged.neoforge.registries.DeferredItem;

import static com.pekar.angelblock.Main.ITEMS;

public class ArmorRegistry
{
    public static final DeferredItem<ModHumanoidArmor> RENDELITHIC_HELMET = registerHumanoidArmor("rendelithic_helmet",
            ModArmorMaterial.RENDELITHIC, ArmorType.HELMET, RendelithicArmor::new);
    public static final DeferredItem<ModHumanoidArmor> RENDELITHIC_HELMET_WITH_NIGHT_VISION = registerHumanoidArmor("rendelithic_helmet_with_nv",
            ModArmorMaterial.RENDELITHIC2, ArmorType.HELMET, (m, t, p) -> new RendelithicArmor(m, t, p).withNightVision());
    public static final DeferredItem<ModHumanoidArmor> RENDELITHIC_CHESTPLATE = registerHumanoidArmor("rendelithic_chestplate", ModArmorMaterial.RENDELITHIC, ArmorType.CHESTPLATE, RendelithicArmor::new);
    public static final DeferredItem<ModHumanoidArmor> RENDELITHIC_CHESTPLATE_WITH_LEVITATION = registerHumanoidArmor("rendelithic_chestplate_with_levitation", ModArmorMaterial.RENDELITHIC2, ArmorType.CHESTPLATE,
            (m, t, p) -> new RendelithicArmor(m, t, p).withSlowFalling());
    public static final DeferredItem<ModHumanoidArmor> RENDELITHIC_LEGGINGS = registerHumanoidArmor("rendelithic_leggings", ModArmorMaterial.RENDELITHIC, ArmorType.LEGGINGS, RendelithicArmor::new);
    public static final DeferredItem<ModHumanoidArmor> RENDELITHIC_LEGGINGS_WITH_HEALTH_REGENERATOR = registerHumanoidArmor("rendelithic_leggings_with_regenerator", ModArmorMaterial.RENDELITHIC2, ArmorType.LEGGINGS,
            (m, t, p) -> new RendelithicArmor(m, t, p).withHealthRegenerator());
    public static final DeferredItem<ModHumanoidArmor> RENDELITHIC_BOOTS = registerHumanoidArmor("rendelithic_boots", ModArmorMaterial.RENDELITHIC, ArmorType.BOOTS, RendelithicArmor::new);
    public static final DeferredItem<ModHumanoidArmor> RENDELITHIC_BOOTS_WITH_STRENGTH_BOOST = registerHumanoidArmor("rendelithic_boots_with_strength_boost", ModArmorMaterial.RENDELITHIC2, ArmorType.BOOTS,
            (m, t, p) -> new RendelithicArmor(m, t, p).withJumpBooster());

    public static final DeferredItem<ModHumanoidArmor> LAPIS_HELMET = registerHumanoidArmor("lapis_helmet", ModArmorMaterial.LAPIS, ArmorType.HELMET, ModHumanoidArmor::new);
    public static final DeferredItem<ModHumanoidArmor> LAPIS_HELMET_WITH_NIGHT_VISION = registerHumanoidArmor("lapis_helmet_with_detector", ModArmorMaterial.LAPIS2, ArmorType.HELMET,
            (m, t, p) -> new ModHumanoidArmor(m, t, p).withNightVision());
    public static final DeferredItem<ModHumanoidArmor> LAPIS_CHESTPLATE = registerHumanoidArmor("lapis_chestplate", ModArmorMaterial.LAPIS, ArmorType.CHESTPLATE, ModHumanoidArmor::new);
    public static final DeferredItem<ModHumanoidArmor> LAPIS_CHESTPLATE_WITH_STRENGTH = registerHumanoidArmor("lapis_chestplate_with_strength", ModArmorMaterial.LAPIS2, ArmorType.CHESTPLATE,
            (m, t, p) -> new ModHumanoidArmor(m, t, p).withStrengthBooster());
    public static final DeferredItem<ModHumanoidArmor> LAPIS_LEGGINGS = registerHumanoidArmor("lapis_leggings", ModArmorMaterial.LAPIS, ArmorType.LEGGINGS, ModHumanoidArmor::new);
    public static final DeferredItem<ModHumanoidArmor> LAPIS_LEGGINGS_WITH_REGENERATOR = registerHumanoidArmor("lapis_leggings_with_regenerator", ModArmorMaterial.LAPIS2, ArmorType.LEGGINGS,
            (m, t, p) -> new ModHumanoidArmor(m, t, p).withHealthRegenerator());
    public static final DeferredItem<ModHumanoidArmor> LAPIS_BOOTS = registerHumanoidArmor("lapis_boots", ModArmorMaterial.LAPIS, ArmorType.BOOTS, ModHumanoidArmor::new);
    public static final DeferredItem<ModHumanoidArmor> LAPIS_BOOTS_WITH_SEA_POWER = registerHumanoidArmor("lapis_boots_with_sea_power", ModArmorMaterial.LAPIS2, ArmorType.BOOTS,
            (m, t, p) -> new ModHumanoidArmor(m, t, p).withSeaPower());

    public static final DeferredItem<ModHumanoidArmor> LIMONITE_HELMET = registerHumanoidArmor("limonite_helmet", ModArmorMaterial.LIMONITE, ArmorType.HELMET, LimoniteArmor::new);
    public static final DeferredItem<ModHumanoidArmor> LIMONITE_HELMET_WITH_DETECTOR = registerHumanoidArmor("limonite_helmet_with_detector", ModArmorMaterial.LIMONITE2, ArmorType.HELMET,
            (m, t, p) -> new LimoniteArmor(m, t, p).withDetector());
    public static final DeferredItem<ModHumanoidArmor> LIMONITE_CHESTPLATE = registerHumanoidArmor("limonite_chestplate", ModArmorMaterial.LIMONITE, ArmorType.CHESTPLATE, LimoniteArmor::new);
    public static final DeferredItem<ModHumanoidArmor> LIMONITE_CHESTPLATE_WITH_LUCK = registerHumanoidArmor("limonite_chestplate_with_luck", ModArmorMaterial.LIMONITE2, ArmorType.CHESTPLATE,
            (m, t, p) -> new LimoniteArmor(m, t, p).withLuck());
    public static final DeferredItem<ModHumanoidArmor> LIMONITE_LEGGINGS = registerHumanoidArmor("limonite_leggings", ModArmorMaterial.LIMONITE, ArmorType.LEGGINGS, LimoniteArmor::new);
    public static final DeferredItem<ModHumanoidArmor> LIMONITE_LEGGINGS_WITH_REGENERATOR = registerHumanoidArmor("limonite_leggings_with_regenerator", ModArmorMaterial.LIMONITE2, ArmorType.LEGGINGS,
            (m, t, p) -> new LimoniteArmor(m, t, p).withHealthRegenerator());
    public static final DeferredItem<ModHumanoidArmor> LIMONITE_BOOTS = registerHumanoidArmor("limonite_boots", ModArmorMaterial.LIMONITE, ArmorType.BOOTS, LimoniteArmor::new);
    public static final DeferredItem<ModHumanoidArmor> LIMONITE_BOOTS_WITH_STRENGTH = registerHumanoidArmor("limonite_boots_with_strength", ModArmorMaterial.LIMONITE2, ArmorType.BOOTS,
            (m, t, p) -> new LimoniteArmor(m, t, p).withJumpBooster());

    public static final DeferredItem<ModHumanoidArmor> DIAMITHIC_HELMET = registerHumanoidArmor("diamithic_helmet", ModArmorMaterial.DIAMITHIC, ArmorType.HELMET, ModHumanoidArmor::new);
    public static final DeferredItem<ModHumanoidArmor> DIAMITHIC_HELMET_WITH_DETECTOR = registerHumanoidArmor("diamithic_helmet_with_detector", ModArmorMaterial.DIAMITHIC2, ArmorType.HELMET,
            (m, t, p) -> new ModHumanoidArmor(m, t, p).withDetector());
    public static final DeferredItem<ModHumanoidArmor> DIAMITHIC_CHESTPLATE = registerHumanoidArmor("diamithic_chestplate", ModArmorMaterial.DIAMITHIC, ArmorType.CHESTPLATE, ModHumanoidArmor::new);
    public static final DeferredItem<ModHumanoidArmor> DIAMITHIC_CHESTPLATE_WITH_STRENGTH = registerHumanoidArmor("diamithic_chestplate_with_strength", ModArmorMaterial.DIAMITHIC2, ArmorType.CHESTPLATE,
            (m, t, p) -> new ModHumanoidArmor(m, t, p).withStrengthBooster());
    public static final DeferredItem<ModHumanoidArmor> DIAMITHIC_CHESTPLATE_WITH_LEVITATION = registerHumanoidArmor("diamithic_chestplate_with_levitation", ModArmorMaterial.DIAMITHIC2, ArmorType.CHESTPLATE,
            (m, t, p) -> new ModHumanoidArmor(m, t, p).withSlowFalling());
    public static final DeferredItem<ModHumanoidArmor> DIAMITHIC_CHESTPLATE_WITH_STRENGTH_AND_LEVITATION = registerHumanoidArmor("diamithic_chestplate_with_strength_and_levitation", ModArmorMaterial.DIAMITHIC2, ArmorType.CHESTPLATE,
            (m, t, p) -> new ModHumanoidArmor(m, t, p).withStrengthBooster().withSlowFalling());
    public static final DeferredItem<ModHumanoidArmor> DIAMITHIC_LEGGINGS = registerHumanoidArmor("diamithic_leggings", ModArmorMaterial.DIAMITHIC, ArmorType.LEGGINGS, ModHumanoidArmor::new);
    public static final DeferredItem<ModHumanoidArmor> DIAMITHIC_LEGGINGS_WITH_REGENERATOR = registerHumanoidArmor("diamithic_leggings_with_regenerator", ModArmorMaterial.DIAMITHIC2, ArmorType.LEGGINGS,
            (m, t, p) -> new ModHumanoidArmor(m, t, p).withHealthRegenerator());
    public static final DeferredItem<ModHumanoidArmor> DIAMITHIC_BOOTS = registerHumanoidArmor("diamithic_boots", ModArmorMaterial.DIAMITHIC, ArmorType.BOOTS, ModHumanoidArmor::new);
    public static final DeferredItem<ModHumanoidArmor> DIAMITHIC_BOOTS_WITH_STRENGTH = registerHumanoidArmor("diamithic_boots_with_strength", ModArmorMaterial.DIAMITHIC2, ArmorType.BOOTS,
            (m, t, p) -> new ModHumanoidArmor(m, t, p).withJumpBooster());

    public static final DeferredItem<ModHumanoidArmor> SUPER_HELMET = registerHumanoidArmor("super_helmet", ModArmorMaterial.SUPER, ArmorType.HELMET, SuperArmor::new);
    public static final DeferredItem<ModHumanoidArmor> SUPER_HELMET_WITH_DETECTOR = registerHumanoidArmor("super_helmet_with_detector", ModArmorMaterial.SUPER2, ArmorType.HELMET,
            (m, t, p) -> new SuperArmor(m, t, p).withDetector());
    public static final DeferredItem<ModHumanoidArmor> SUPER_CHESTPLATE = registerHumanoidArmor("super_chestplate", ModArmorMaterial.SUPER, ArmorType.CHESTPLATE, SuperArmor::new);
    public static final DeferredItem<ModHumanoidArmor> SUPER_CHESTPLATE_FLYING = registerHumanoidArmor("super_chestplate_flying", ModArmorMaterial.SUPER2, ArmorType.CHESTPLATE,
            (m, t, p) -> new SuperArmorFlying(m, t,  p.component(DataComponents.GLIDER, Unit.INSTANCE)).withElytra().canFly());
    public static final DeferredItem<ModHumanoidArmor> SUPER_LEGGINGS = registerHumanoidArmor("super_leggings", ModArmorMaterial.SUPER, ArmorType.LEGGINGS, SuperArmor::new);
    public static final DeferredItem<ModHumanoidArmor> SUPER_LEGGINGS_WITH_REGENERATOR2 = registerHumanoidArmor("super_leggings_with_regenerator", ModArmorMaterial.SUPER2, ArmorType.LEGGINGS,
            (m, t, p) -> new SuperArmor(m, t, p).withHealthRegenerator());
    public static final DeferredItem<ModHumanoidArmor> SUPER_BOOTS = registerHumanoidArmor("super_boots", ModArmorMaterial.SUPER, ArmorType.BOOTS, SuperArmor::new);
    public static final DeferredItem<ModHumanoidArmor> SUPER_BOOTS_WITH_STRENGTH = registerHumanoidArmor("super_boots_with_strength", ModArmorMaterial.SUPER2, ArmorType.BOOTS,
            (m, t, p) -> new SuperArmor(m, t, p).withJumpBooster());
    public static final DeferredItem<ModHumanoidArmor> SUPER_BOOTS_WITH_SEA_POWER = registerHumanoidArmor("super_boots_with_sea_power", ModArmorMaterial.SUPER2, ArmorType.BOOTS,
            (m, t, p) -> new SuperArmor(m, t, p).withSeaPower());
    public static final DeferredItem<ModHumanoidArmor> SUPER_BOOTS_WITH_STRENGTH_AND_SEA_POWER = registerHumanoidArmor("super_boots_with_strength_and_sea_power", ModArmorMaterial.SUPER2, ArmorType.BOOTS,
            (m, t, p) -> new SuperArmor(m, t, p).withSeaPower().withJumpBooster());

    public static final DeferredItem<ModHumanoidArmor> FLYING_HELMET = registerHumanoidArmor("flying_helmet", ModArmorMaterial.FLYING, ArmorType.HELMET, FlyingArmor::new);
    public static final DeferredItem<ModHumanoidArmor> FLYING_CHESTPLATE = registerHumanoidArmor("flying_chestplate", ModArmorMaterial.FLYING, ArmorType.CHESTPLATE,
            (m, t, p) -> new FlyingArmor(m, t, p.component(DataComponents.GLIDER, Unit.INSTANCE)).canFly());
    public static final DeferredItem<ModHumanoidArmor> FLYING_LEGGINGS = registerHumanoidArmor("flying_leggings", ModArmorMaterial.FLYING, ArmorType.LEGGINGS, FlyingArmor::new);
    public static final DeferredItem<ModHumanoidArmor> FLYING_BOOTS = registerHumanoidArmor("flying_boots", ModArmorMaterial.FLYING, ArmorType.BOOTS, FlyingArmor::new);

    public static void initStatic()
    {
        // just to initialize static members
    }

    private static DeferredItem<ModHumanoidArmor> registerHumanoidArmor(String name, ModArmorMaterial armorMaterial, ArmorType armorType,
                                                                        TriFunction<ModArmorMaterial, ArmorType, Item.Properties, ModHumanoidArmor> armorConstructor)
    {
        return ITEMS.registerItem(name, p -> armorConstructor.apply(armorMaterial, armorType, p));
    }
}
