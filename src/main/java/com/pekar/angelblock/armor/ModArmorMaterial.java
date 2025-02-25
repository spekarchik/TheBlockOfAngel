package com.pekar.angelblock.armor;

import com.pekar.angelblock.Main;
import com.pekar.angelblock.utils.Utils;
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

// Durability:
// LEATHER: 5
// GOLDEN: 7
// CHAINMAIL: 15
// IRON: 15
// DIAMOND: 33
// NETHERITE: 37

public class ModArmorMaterial
{
    private final Holder<ArmorMaterial> material;
    private final String materialName;
    private final int durabilityMultiplier;
    private final EnumMap<ArmorItem.Type, Integer> armorResistanceMap;
    private final int enchantmentValue;
    private final float toughness;
    private final float knockbackResistance;
    private final Holder<SoundEvent> equipmentSound;
    private final Supplier<Ingredient> repairIngredient;

    public static final String RENDELITHIC_MATERIAL_NAME = "rendelithic";
    public static final String LIMONITE_MATERIAL_NAME = "limonite";
    public static final String DIAMITHIC_MATERIAL_NAME = "diamithic";
    public static final String LAPIS_MATERIAL_NAME = "lapis";
    public static final String SUPER_MATERIAL_NAME = "super";
    public static final String FLYING_MATERIAL_NAME = "flying";

    protected static final ModArmorMaterial RENDELITHIC = new ModArmorMaterial(RENDELITHIC_MATERIAL_NAME, "rendelithic_armor",
            createArmorTypeMap(3, 7, 9, 3, 7),
            25, 0F, 0F, 11, SoundEvents.ARMOR_EQUIP_GOLD, () -> Ingredient.of(ItemRegistry.RENDELITHIC_INGOT.get()));
    protected static final ModArmorMaterial LIMONITE = new ModArmorMaterial(LIMONITE_MATERIAL_NAME, "limonite_armor",
            createArmorTypeMap(2, 5, 7, 3, 3),
            30, 1F, 0F, 23, SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.of(ItemRegistry.LIMONITE_INGOT.get()));
    protected static final ModArmorMaterial LAPIS = new ModArmorMaterial(LAPIS_MATERIAL_NAME, "lapis_armor",
            createArmorTypeMap(3, 6, 8, 3, 11),
            10, 0F, 0F,40, SoundEvents.ARMOR_EQUIP_DIAMOND, () -> Ingredient.of(ItemRegistry.LAPIS_INGOT.get()));
    protected static final ModArmorMaterial DIAMITHIC = new ModArmorMaterial(DIAMITHIC_MATERIAL_NAME, "diamithic_armor",
            createArmorTypeMap(3, 7, 9, 3, 11),
            14, 3F, 0.2F, 45, SoundEvents.ARMOR_EQUIP_NETHERITE, () -> Ingredient.of(ItemRegistry.DIAMITHIC_INGOT.get()));
    protected static final ModArmorMaterial SUPER = new ModArmorMaterial(SUPER_MATERIAL_NAME, "super_armor",
            createArmorTypeMap(5, 9, 11, 5, 15),
            1, 4F, 0.2F, 43, SoundEvents.ARMOR_EQUIP_NETHERITE, () -> Ingredient.of(ItemRegistry.SUPER_INGOT.get()));
    protected static final ModArmorMaterial FLYING = new ModArmorMaterial(FLYING_MATERIAL_NAME, "flying_armor",
            createArmorTypeMap(1, 1, 1, 1, 1),
            0, 0F, 0F, 3, SoundEvents.ARMOR_EQUIP_ELYTRA, () -> Ingredient.of(Items.PHANTOM_MEMBRANE));

    // other armor models (other textures)
    protected static final ModArmorMaterial RENDELITHIC2 = copyOf(RENDELITHIC, "rendelithic_armor2");
    protected static final ModArmorMaterial LIMONITE2 = copyOf(LIMONITE, "limonite_armor2");
    protected static final ModArmorMaterial DIAMITHIC2 = copyOf(DIAMITHIC, "diamithic_armor2");
    protected static final ModArmorMaterial LAPIS2 = copyOf(LAPIS, "lapis_armor2");
    protected static final ModArmorMaterial SUPER2 = copyOf(SUPER, "super_armor2");


    public ModArmorMaterial(String materialName, String armorModelName, EnumMap<ArmorItem.Type, Integer> armorResistanceMap,
                            int enchantmentValue, float toughness, float knockbackResistance, int durabilityMultiplier,
                            Holder<SoundEvent> equipmentSound, Supplier<Ingredient> repairIngredient)
    {
        this.material = register(armorModelName, armorResistanceMap, enchantmentValue, equipmentSound, toughness, knockbackResistance, repairIngredient);
        this.materialName = materialName;
        this.armorResistanceMap = armorResistanceMap;
        this.enchantmentValue = enchantmentValue;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.durabilityMultiplier = durabilityMultiplier;
        this.equipmentSound = equipmentSound;
        this.repairIngredient = repairIngredient;
    }

    public Holder<ArmorMaterial> getMaterial()
    {
        return material;
    }

    public String getMaterialName()
    {
        return materialName;
    }

    public int getDurabilityMultiplier()
    {
        return durabilityMultiplier;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof ModArmorMaterial another)) return false;
        return materialName.equals(another.materialName);
    }

    @Override
    public int hashCode()
    {
        return materialName.hashCode();
    }

    // copied from ArmorMaterials and modified
    private static Holder<ArmorMaterial> register(
            String armorName,
            EnumMap<ArmorItem.Type, Integer> map,
            int enchantmentValue,
            Holder<SoundEvent> equipSound,
            float toughness,
            float knockbackResistance,
            Supplier<Ingredient> repairIngredient)
    {
        var armorLayers = getNonDyeableArmorLayers(armorName);
        EnumMap<ArmorItem.Type, Integer> enummap = new EnumMap<>(ArmorItem.Type.class);

        for (ArmorItem.Type armoritem$type : ArmorItem.Type.values()) {
            enummap.put(armoritem$type, map.get(armoritem$type));
        }

        return Registry.registerForHolder(
                BuiltInRegistries.ARMOR_MATERIAL,
                Utils.instance.resources.createResourceLocation(Main.MODID, armorName),
                new ArmorMaterial(enummap, enchantmentValue, equipSound, repairIngredient, armorLayers, toughness, knockbackResistance)
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
        var resourceLocation = Utils.instance.resources.createResourceLocation(Main.MODID, armorName);
        return List.of(new ArmorMaterial.Layer(resourceLocation));
    }

    private static ModArmorMaterial copyOf(ModArmorMaterial armorMaterial, String armorModelName)
    {
        return new ModArmorMaterial(
                armorMaterial.materialName,
                armorModelName,
                armorMaterial.armorResistanceMap,
                armorMaterial.enchantmentValue,
                armorMaterial.toughness,
                armorMaterial.knockbackResistance,
                armorMaterial.durabilityMultiplier,
                armorMaterial.equipmentSound,
                armorMaterial.repairIngredient);
    }
}
