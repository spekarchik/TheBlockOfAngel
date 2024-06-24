package com.pekar.angelblock.armor;

import com.pekar.angelblock.Main;
import com.pekar.angelblock.Utils;
import com.pekar.angelblock.items.ItemRegistry;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class ModArmorMaterial
{
    private final Holder<ArmorMaterial> material;
    private final String armorModelName;
    private final int durabilityMultiplier;

    private static final Holder<ArmorMaterial> Rendelithic = register(
            "rendelithic_armor",
            createArmorTypeMap(3, 6, 8, 3, 7),
            17,
            SoundEvents.ARMOR_EQUIP_DIAMOND,
            0F,
            0F,
            () -> Ingredient.of(ItemRegistry.RENDELITHIC_INGOT.get())); // durabilityMultiplier: 21

    private static final Holder<ArmorMaterial> Rendelithic2 = register(
            "rendelithic_armor2",
            createArmorTypeMap(3, 6, 8, 3, 7),
            17,
            SoundEvents.ARMOR_EQUIP_DIAMOND,
            0F,
            0F,
            () -> Ingredient.of(ItemRegistry.RENDELITHIC_INGOT.get())); // durabilityMultiplier: 21

    private static final Holder<ArmorMaterial> Limonite = register(
            "limonite_armor",
            createArmorTypeMap(3, 5, 8, 3, 3),
            30,
            SoundEvents.ARMOR_EQUIP_LEATHER,
            1F,
            0F,
            () -> Ingredient.of(ItemRegistry.LIMONITE_INGOT.get())); // durabilityMultiplier: 28

    private static final Holder<ArmorMaterial> Limonite2 = register(
            "limonite_armor2",
            createArmorTypeMap(3, 5, 8, 3, 3),
            30,
            SoundEvents.ARMOR_EQUIP_LEATHER,
            1F,
            0F,
            () -> Ingredient.of(ItemRegistry.LIMONITE_INGOT.get())); // durabilityMultiplier: 28

    private static final Holder<ArmorMaterial> Diamithic = register(
            "diamithic_armor",
            createArmorTypeMap(3, 6, 9, 3, 11),
            14,
            SoundEvents.ARMOR_EQUIP_NETHERITE,
            3F,
            0.1F,
            () -> Ingredient.of(ItemRegistry.DIAMITHIC_INGOT.get())); // durabilityMultiplier: 41

    private static final Holder<ArmorMaterial> Diamithic2 = register(
            "diamithic_armor2",
            createArmorTypeMap(3, 6, 9, 3, 11),
            14,
            SoundEvents.ARMOR_EQUIP_NETHERITE,
            3F,
            0.1F,
            () -> Ingredient.of(ItemRegistry.DIAMITHIC_INGOT.get())); // durabilityMultiplier: 41

    private static final Holder<ArmorMaterial> Lapis = register(
            "lapis_armor",
            createArmorTypeMap(3, 6, 8, 3, 11),
            12,
            SoundEvents.ARMOR_EQUIP_DIAMOND,
            2F,
            0F,
            () -> Ingredient.of(ItemRegistry.LAPIS_INGOT.get())); // durabilityMultiplier: 37

    private static final Holder<ArmorMaterial> Lapis2 = register(
            "lapis_armor2",
            createArmorTypeMap(3, 6, 8, 3, 11),
            12,
            SoundEvents.ARMOR_EQUIP_DIAMOND,
            2F,
            0F,
            () -> Ingredient.of(ItemRegistry.LAPIS_INGOT.get())); // durabilityMultiplier: 37

    private static final Holder<ArmorMaterial> Super = register(
            "super_armor",
            createArmorTypeMap(3, 6, 9, 3, 15),
            25,
            SoundEvents.ARMOR_EQUIP_DIAMOND,
            4F,
            0.2F,
            () -> Ingredient.of(ItemRegistry.SUPER_INGOT.get())); // durabilityMultiplier: 43

    private static final Holder<ArmorMaterial> Super2 = register(
            "super_armor2",
            createArmorTypeMap(3, 6, 9, 3, 15),
            25,
            SoundEvents.ARMOR_EQUIP_NETHERITE,
            4F,
            0.2F,
            () -> Ingredient.of(ItemRegistry.SUPER_INGOT.get())); // durabilityMultiplier: 43

    private static final Holder<ArmorMaterial> Flying = register(
            "flying_armor",
            createArmorTypeMap(1, 1, 1, 1, 1),
            30,
            SoundEvents.ARMOR_EQUIP_ELYTRA,
            0F,
            0F,
            () -> Ingredient.of(Items.PHANTOM_MEMBRANE)); // durabilityMultiplier: 3

    protected static final ModArmorMaterial RENDELITHIC = new ModArmorMaterial(Rendelithic, "rendelithic", 21);
    protected static final ModArmorMaterial RENDELITHIC2 = new ModArmorMaterial(Rendelithic2, "rendelithic", 21);
    protected static final ModArmorMaterial LIMONITE = new ModArmorMaterial(Limonite, "limonite", 28);
    protected static final ModArmorMaterial LIMONITE2 = new ModArmorMaterial(Limonite2, "limonite", 28);
    protected static final ModArmorMaterial DIAMITHIC = new ModArmorMaterial(Diamithic, "diamithic", 41);
    protected static final ModArmorMaterial DIAMITHIC2 = new ModArmorMaterial(Diamithic2, "diamithic", 41);
    protected static final ModArmorMaterial LAPIS = new ModArmorMaterial(Lapis, "lapis", 37);
    protected static final ModArmorMaterial LAPIS2 = new ModArmorMaterial(Lapis2, "lapis", 37);
    protected static final ModArmorMaterial SUPER = new ModArmorMaterial(Super, "super", 43);
    protected static final ModArmorMaterial SUPER2 = new ModArmorMaterial(Super2, "super", 43);
    protected static final ModArmorMaterial FLYING = new ModArmorMaterial(Flying, "flying", 3);


    public ModArmorMaterial(Holder<ArmorMaterial> material, String armorModelName, int durabilityMultiplier)
    {
        this.material = material;
        this.armorModelName = armorModelName;
        this.durabilityMultiplier = durabilityMultiplier;
    }

    public Holder<ArmorMaterial> getMaterial()
    {
        return material;
    }

    public String getArmorModelName()
    {
        return armorModelName;
    }

    public int getDurabilityMultiplier()
    {
        return durabilityMultiplier;
    }

    // copied from ArmorMaterials and modified
    private static Holder<ArmorMaterial> register(
            String armorName,
            EnumMap<ArmorItem.Type, Integer> map,
            int enchantmentValue,
            Holder<SoundEvent> equipSound,
            float toughness,
            float knockbackResistance,
            Supplier<Ingredient> ingredient)
    {
        var armorLayers = getNonDyeableArmorLayers(armorName);
        EnumMap<ArmorItem.Type, Integer> enummap = new EnumMap<>(ArmorItem.Type.class);

        for (ArmorItem.Type armoritem$type : ArmorItem.Type.values()) {
            enummap.put(armoritem$type, map.get(armoritem$type));
        }

        return Registry.registerForHolder(
                BuiltInRegistries.f_315942_, //was in 1.20.6: BuiltInRegistries.ARMOR_MATERIAL
                Utils.createResourceLocation(armorName),
                new ArmorMaterial(enummap, enchantmentValue, equipSound, ingredient, armorLayers, toughness, knockbackResistance)
        );
    }

    private static EnumMap<ArmorItem.Type, Integer> createArmorTypeMap(int bootsResistance, int leggingsResistance, int chestplateResistance, int helmetResistance, int bodyResistance)
    {
        return Util.make(new EnumMap<>(ArmorItem.Type.class), armorTypeMap -> {
            armorTypeMap.put(ArmorItem.Type.BOOTS, bootsResistance);
            armorTypeMap.put(ArmorItem.Type.LEGGINGS, leggingsResistance);
            armorTypeMap.put(ArmorItem.Type.CHESTPLATE, chestplateResistance);
            armorTypeMap.put(ArmorItem.Type.HELMET, helmetResistance);
            armorTypeMap.put(ArmorItem.Type.BODY, bodyResistance);
        });
    }

    private static List<ArmorMaterial.Layer> getNonDyeableArmorLayers(String armorName)
    {
        var fullArmorName = Main.MODID + ":" + armorName;
        var resourceLocation = Utils.createResourceLocation(fullArmorName);
        return List.of(new ArmorMaterial.Layer(resourceLocation));
    }
}
