package com.pekar.angelblock.armor;

import com.pekar.angelblock.utils.TriFunction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.Equippable;
import net.neoforged.neoforge.registries.DeferredItem;

import static com.pekar.angelblock.Main.ITEMS;

public class ArmorRegistry
{
    public static final DeferredItem<ModArmor> RENDELITHIC_HELMET = registerArmor("rendelithic_helmet",
            ModArmorMaterial.RENDELITHIC, ArmorType.HELMET, RendelithicArmor::new);

    public static final DeferredItem<ModArmor> RENDELITHIC_CHESTPLATE = registerArmor("rendelithic_chestplate", ModArmorMaterial.RENDELITHIC, ArmorType.CHESTPLATE, RendelithicArmor::new);
    public static final DeferredItem<ModArmor> RENDELITHIC_CHESTPLATE_WITH_LEVITATION = registerArmor("rendelithic_chestplate_with_levitation", ModArmorMaterial.RENDELITHIC2, ArmorType.CHESTPLATE,
            (m, t, p) -> new RendelithicArmor(m, t, p).withSlowFalling());
    public static final DeferredItem<ModArmor> RENDELITHIC_LEGGINGS = registerArmor("rendelithic_leggings", ModArmorMaterial.RENDELITHIC, ArmorType.LEGGINGS, RendelithicArmor::new);
    public static final DeferredItem<ModArmor> RENDELITHIC_LEGGINGS_WITH_HEALTH_REGENERATOR = registerArmor("rendelithic_leggings_with_regenerator", ModArmorMaterial.RENDELITHIC2, ArmorType.LEGGINGS,
            (m, t, p) -> new RendelithicArmor(m, t, p).withHealthRegenerator());
    public static final DeferredItem<ModArmor> RENDELITHIC_BOOTS = registerArmor("rendelithic_boots", ModArmorMaterial.RENDELITHIC, ArmorType.BOOTS, RendelithicArmor::new);
    public static final DeferredItem<ModArmor> RENDELITHIC_BOOTS_WITH_STRENGTH_BOOST = registerArmor("rendelithic_boots_with_strength_boost", ModArmorMaterial.RENDELITHIC2, ArmorType.BOOTS,
            (m, t, p) -> new RendelithicArmor(m, t, p).withJumpBooster());

    public static final DeferredItem<ModArmor> LAPIS_HELMET = registerArmor("lapis_helmet", ModArmorMaterial.LAPIS, ArmorType.HELMET, ModArmor::new);
    public static final DeferredItem<ModArmor> LAPIS_HELMET_WITH_NIGHT_VISION = registerArmor("lapis_helmet_with_detector", ModArmorMaterial.LAPIS2, ArmorType.HELMET,
            (m, t, p) -> new ModArmor(m, t, p).withNightVision());
    public static final DeferredItem<ModArmor> LAPIS_CHESTPLATE = registerArmor("lapis_chestplate", ModArmorMaterial.LAPIS, ArmorType.CHESTPLATE, ModArmor::new);
    public static final DeferredItem<ModArmor> LAPIS_CHESTPLATE_WITH_STRENGTH = registerArmor("lapis_chestplate_with_strength", ModArmorMaterial.LAPIS2, ArmorType.CHESTPLATE,
            (m, t, p) -> new ModArmor(m, t, p).withStrengthBooster());
    public static final DeferredItem<ModArmor> LAPIS_LEGGINGS = registerArmor("lapis_leggings", ModArmorMaterial.LAPIS, ArmorType.LEGGINGS, ModArmor::new);
    public static final DeferredItem<ModArmor> LAPIS_LEGGINGS_WITH_REGENERATOR = registerArmor("lapis_leggings_with_regenerator", ModArmorMaterial.LAPIS2, ArmorType.LEGGINGS,
            (m, t, p) -> new ModArmor(m, t, p).withHealthRegenerator());
    public static final DeferredItem<ModArmor> LAPIS_BOOTS = registerArmor("lapis_boots", ModArmorMaterial.LAPIS, ArmorType.BOOTS, ModArmor::new);
    public static final DeferredItem<ModArmor> LAPIS_BOOTS_WITH_SEA_POWER = registerArmor("lapis_boots_with_sea_power", ModArmorMaterial.LAPIS2, ArmorType.BOOTS,
            (m, t, p) -> new ModArmor(m, t, p).withSeaPower());

    public static final DeferredItem<ModArmor> LIMONITE_HELMET = registerArmor("limonite_helmet", ModArmorMaterial.LIMONITE, ArmorType.HELMET, LimoniteArmor::new);
    public static final DeferredItem<ModArmor> LIMONITE_HELMET_WITH_DETECTOR = registerArmor("limonite_helmet_with_detector", ModArmorMaterial.LIMONITE2, ArmorType.HELMET,
            (m, t, p) -> new LimoniteArmor(m, t, p).withDetector());
    public static final DeferredItem<ModArmor> LIMONITE_CHESTPLATE = registerArmor("limonite_chestplate", ModArmorMaterial.LIMONITE, ArmorType.CHESTPLATE, LimoniteArmor::new);
    public static final DeferredItem<ModArmor> LIMONITE_CHESTPLATE_WITH_LUCK = registerArmor("limonite_chestplate_with_luck", ModArmorMaterial.LIMONITE2, ArmorType.CHESTPLATE,
            (m, t, p) -> new LimoniteArmor(m, t, p).withLuck());
    public static final DeferredItem<ModArmor> LIMONITE_LEGGINGS = registerArmor("limonite_leggings", ModArmorMaterial.LIMONITE, ArmorType.LEGGINGS, LimoniteArmor::new);
    public static final DeferredItem<ModArmor> LIMONITE_LEGGINGS_WITH_REGENERATOR = registerArmor("limonite_leggings_with_regenerator", ModArmorMaterial.LIMONITE2, ArmorType.LEGGINGS,
            (m, t, p) -> new LimoniteArmor(m, t, p).withHealthRegenerator());
    public static final DeferredItem<ModArmor> LIMONITE_BOOTS = registerArmor("limonite_boots", ModArmorMaterial.LIMONITE, ArmorType.BOOTS, LimoniteArmor::new);
    public static final DeferredItem<ModArmor> LIMONITE_BOOTS_WITH_STRENGTH = registerArmor("limonite_boots_with_strength", ModArmorMaterial.LIMONITE2, ArmorType.BOOTS,
            (m, t, p) -> new LimoniteArmor(m, t, p).withJumpBooster());

    public static final DeferredItem<ModArmor> DIAMITHIC_HELMET = registerArmor("diamithic_helmet", ModArmorMaterial.DIAMITHIC, ArmorType.HELMET, ModArmor::new);
    public static final DeferredItem<ModArmor> DIAMITHIC_HELMET_WITH_DETECTOR = registerArmor("diamithic_helmet_with_detector", ModArmorMaterial.DIAMITHIC2, ArmorType.HELMET,
            (m, t, p) -> new ModArmor(m, t, p).withDetector());
    public static final DeferredItem<ModArmor> DIAMITHIC_CHESTPLATE = registerArmor("diamithic_chestplate", ModArmorMaterial.DIAMITHIC, ArmorType.CHESTPLATE, ModArmor::new);
    public static final DeferredItem<ModArmor> DIAMITHIC_CHESTPLATE_WITH_STRENGTH = registerArmor("diamithic_chestplate_with_strength", ModArmorMaterial.DIAMITHIC2, ArmorType.CHESTPLATE,
            (m, t, p) -> new ModArmor(m, t, p).withStrengthBooster());
    public static final DeferredItem<ModArmor> DIAMITHIC_CHESTPLATE_WITH_LEVITATION = registerArmor("diamithic_chestplate_with_levitation", ModArmorMaterial.DIAMITHIC2, ArmorType.CHESTPLATE,
            (m, t, p) -> new ModArmor(m, t, p).withSlowFalling());
    public static final DeferredItem<ModArmor> DIAMITHIC_CHESTPLATE_WITH_STRENGTH_AND_LEVITATION = registerArmor("diamithic_chestplate_with_strength_and_levitation", ModArmorMaterial.DIAMITHIC2, ArmorType.CHESTPLATE,
            (m, t, p) -> new ModArmor(m, t, p).withStrengthBooster().withSlowFalling());
    public static final DeferredItem<ModArmor> DIAMITHIC_LEGGINGS = registerArmor("diamithic_leggings", ModArmorMaterial.DIAMITHIC, ArmorType.LEGGINGS, ModArmor::new);
    public static final DeferredItem<ModArmor> DIAMITHIC_LEGGINGS_WITH_REGENERATOR = registerArmor("diamithic_leggings_with_regenerator", ModArmorMaterial.DIAMITHIC2, ArmorType.LEGGINGS,
            (m, t, p) -> new ModArmor(m, t, p).withHealthRegenerator());
    public static final DeferredItem<ModArmor> DIAMITHIC_BOOTS = registerArmor("diamithic_boots", ModArmorMaterial.DIAMITHIC, ArmorType.BOOTS, ModArmor::new);
    public static final DeferredItem<ModArmor> DIAMITHIC_BOOTS_WITH_STRENGTH = registerArmor("diamithic_boots_with_strength", ModArmorMaterial.DIAMITHIC2, ArmorType.BOOTS,
            (m, t, p) -> new ModArmor(m, t, p).withJumpBooster());

    public static final DeferredItem<ModArmor> SUPER_HELMET = registerArmor("super_helmet", ModArmorMaterial.SUPER, ArmorType.HELMET, SuperArmor::new);
    public static final DeferredItem<ModArmor> SUPER_HELMET_WITH_DETECTOR = registerArmor("super_helmet_with_detector", ModArmorMaterial.SUPER2, ArmorType.HELMET,
            (m, t, p) -> new SuperArmor(m, t, p).withDetector());
    public static final DeferredItem<ModArmor> SUPER_CHESTPLATE = registerArmor("super_chestplate", ModArmorMaterial.SUPER, ArmorType.CHESTPLATE, SuperArmor::new);
    public static final DeferredItem<ModArmor> SUPER_CHESTPLATE_FLYING = registerArmor("super_chestplate_flying", ModArmorMaterial.SUPER2, ArmorType.CHESTPLATE,
            (m, t, p) -> new SuperArmorFlying(m, t,  p.component(DataComponents.GLIDER, Unit.INSTANCE)).withElytra().canFly());
    public static final DeferredItem<ModArmor> SUPER_LEGGINGS = registerArmor("super_leggings", ModArmorMaterial.SUPER, ArmorType.LEGGINGS, SuperArmor::new);
    public static final DeferredItem<ModArmor> SUPER_LEGGINGS_WITH_REGENERATOR2 = registerArmor("super_leggings_with_regenerator", ModArmorMaterial.SUPER2, ArmorType.LEGGINGS,
            (m, t, p) -> new SuperArmor(m, t, p).withHealthRegenerator());
    public static final DeferredItem<ModArmor> SUPER_BOOTS = registerArmor("super_boots", ModArmorMaterial.SUPER, ArmorType.BOOTS, SuperArmor::new);
    public static final DeferredItem<ModArmor> SUPER_BOOTS_WITH_STRENGTH = registerArmor("super_boots_with_strength", ModArmorMaterial.SUPER2, ArmorType.BOOTS,
            (m, t, p) -> new SuperArmor(m, t, p).withJumpBooster());
    public static final DeferredItem<ModArmor> SUPER_BOOTS_WITH_SEA_POWER = registerArmor("super_boots_with_sea_power", ModArmorMaterial.SUPER2, ArmorType.BOOTS,
            (m, t, p) -> new SuperArmor(m, t, p).withSeaPower());
    public static final DeferredItem<ModArmor> SUPER_BOOTS_WITH_STRENGTH_AND_SEA_POWER = registerArmor("super_boots_with_strength_and_sea_power", ModArmorMaterial.SUPER2, ArmorType.BOOTS,
            (m, t, p) -> new SuperArmor(m, t, p).withSeaPower().withJumpBooster());

    public static final DeferredItem<ModArmor> FLYING_HELMET = registerArmor("flying_helmet", ModArmorMaterial.FLYING, ArmorType.HELMET, FlyingArmor::new);
    public static final DeferredItem<ModArmor> FLYING_CHESTPLATE = registerArmor("flying_chestplate", ModArmorMaterial.FLYING, ArmorType.CHESTPLATE,
            (m, t, p) -> new FlyingArmor(m, t, p.component(DataComponents.GLIDER, Unit.INSTANCE)).canFly());
    public static final DeferredItem<ModArmor> FLYING_LEGGINGS = registerArmor("flying_leggings", ModArmorMaterial.FLYING, ArmorType.LEGGINGS, FlyingArmor::new);
    public static final DeferredItem<ModArmor> FLYING_BOOTS = registerArmor("flying_boots", ModArmorMaterial.FLYING, ArmorType.BOOTS, FlyingArmor::new);

    public static void initStatic()
    {
        // just to initialize static members
    }

    private static DeferredItem<ModArmor> registerArmor(String name, ModArmorMaterial armorMaterial, ArmorType armorType,
                                                        TriFunction<ModArmorMaterial, ArmorType, Item.Properties, ModArmor> armorProvider)
    {
        var equipmentSlot = switch (armorType)
        {
            case HELMET -> EquipmentSlot.HEAD;
            case CHESTPLATE -> EquipmentSlot.CHEST;
            case LEGGINGS -> EquipmentSlot.LEGS;
            case BOOTS -> EquipmentSlot.FEET;
            case BODY -> EquipmentSlot.BODY;
        };

        return ITEMS.registerItem(name, p -> armorProvider.apply(armorMaterial, armorType, p.component(
                DataComponents.EQUIPPABLE,
                Equippable.builder(equipmentSlot).setAsset(armorMaterial.getMaterial().assetId())
                        .build())));
    }
}
