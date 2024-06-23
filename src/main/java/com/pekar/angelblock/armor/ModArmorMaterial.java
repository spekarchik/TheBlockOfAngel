package com.pekar.angelblock.armor;

import com.pekar.angelblock.items.ItemRegistry;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static net.minecraft.world.item.ArmorItem.Type.*;

public class ModArmorMaterial extends ArmorMaterial
{
    private final String name;
    private final int[] slotProtections;
    private final int enchantmentValue;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;

    private static final Map<ArmorItem.Type, Integer> DEFENSE = new HashMap<>();

    private static final List<Layer> RendelithicLayers = 

    static
    {
        DEFENSE.put(HELMET, 11);
        DEFENSE.put(CHESTPLATE, 16);
        DEFENSE.put(LEGGINGS, 15);
        DEFENSE.put(BOOTS, 13);
        DEFENSE.put(BODY, 16);
    }

    protected static final Holder<ArmorMaterial> RENDELITHIC = Holder.direct(new ArmorMaterial("angelblock:rendelithic_armor",
            21, new int[] { 3, 6, 8, 3 }, 17, SoundEvents.ARMOR_EQUIP_DIAMOND.get(),
            0F, 0F, () -> Ingredient.of(ItemRegistry.RENDELITHIC_INGOT.get())));

    protected static final Holder<ArmorMaterial> RENDELITHIC2 = Holder.direct(new ModArmorMaterial("angelblock:rendelithic_armor2",
            21, new int[] { 3, 6, 8, 3 }, 17, SoundEvents.ARMOR_EQUIP_DIAMOND.get(),
            0F, 0F, () -> Ingredient.of(ItemRegistry.RENDELITHIC_INGOT.get())));

    protected static final Holder<ArmorMaterial> LIMONITE = Holder.direct(new ModArmorMaterial("angelblock:limonite_armor",
            28, new int[] { 3, 5, 8, 3 }, 30, SoundEvents.ARMOR_EQUIP_LEATHER.get(),
            1F, 0F, () -> Ingredient.of(ItemRegistry.LIMONITE_INGOT.get())));

    protected static final Holder<ArmorMaterial> LIMONITE2 = Holder.direct(new ModArmorMaterial("angelblock:limonite_armor2",
            28, new int[] { 3, 5, 8, 3 }, 30, SoundEvents.ARMOR_EQUIP_LEATHER.get(),
            1F, 0F, () -> Ingredient.of(ItemRegistry.LIMONITE_INGOT.get())));

    protected static final Holder<ArmorMaterial> DIAMITHIC = Holder.direct(new ModArmorMaterial("angelblock:diamithic_armor",
            41, new int[] { 3, 6, 9, 3 }, 14, SoundEvents.ARMOR_EQUIP_DIAMOND.get(),
            3F, 0.1F, () -> Ingredient.of(ItemRegistry.DIAMITHIC_INGOT.get())));

    protected static final Holder<ArmorMaterial> DIAMITHIC2 = Holder.direct(new ModArmorMaterial("angelblock:diamithic_armor2",
            41, new int[] { 3, 6, 9, 3 }, 14, SoundEvents.ARMOR_EQUIP_DIAMOND.get(),
            3F, 0.1F, () -> Ingredient.of(ItemRegistry.DIAMITHIC_INGOT.get())));

    protected static final Holder<ArmorMaterial> LAPIS = Holder.direct(new ModArmorMaterial("angelblock:lapis_armor",
            37, new int[] { 3, 6, 8, 3 }, 12, SoundEvents.ARMOR_EQUIP_DIAMOND.get(),
            2F, 0F, () -> Ingredient.of(ItemRegistry.LAPIS_INGOT.get())));

    protected static final Holder<ArmorMaterial> LAPIS2 = Holder.direct(new ModArmorMaterial("angelblock:lapis_armor2",
            37, new int[] { 3, 6, 8, 3 }, 12, SoundEvents.ARMOR_EQUIP_DIAMOND.get(),
            2F, 0F, () -> Ingredient.of(ItemRegistry.LAPIS_INGOT.get())));

    protected static final Holder<ArmorMaterial> SUPER = Holder.direct(new ModArmorMaterial("angelblock:super_armor",
            43, new int[] { 3, 6, 9, 3 }, 25, SoundEvents.ARMOR_EQUIP_DIAMOND.get(),
            4F, 0.2F, () -> Ingredient.of(ItemRegistry.SUPER_INGOT.get())));

    protected static final Holder<ArmorMaterial> SUPER2 = Holder.direct(new ModArmorMaterial("angelblock:super_armor2",
            43, new int[] { 3, 6, 9, 3 }, 25, SoundEvents.ARMOR_EQUIP_DIAMOND.get(),
            4F, 0.2F, () -> Ingredient.of(ItemRegistry.SUPER_INGOT.get())));

    protected static final Holder<ArmorMaterial> FLYING = Holder.direct(new ModArmorMaterial("angelblock:flying_armor",
            3, new int[] { 1, 1, 1, 1 }, 30, SoundEvents.ARMOR_EQUIP_ELYTRA.get(),
            0F, 0F, () -> Ingredient.of(Items.PHANTOM_MEMBRANE)));

    public ModArmorMaterial(String name, int durabilityMultiplier, int[] slotProtections, int enchantmentValue,
                            SoundEvent sound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient)
    {
        this.name = name;
        this.slotProtections = slotProtections;
        this.enchantmentValue = enchantmentValue;
        this.sound = sound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type armorType)
    {
        return this.slotProtections[armorType.getSlot().getIndex()];
    }

    @Override
    public int getEnchantmentValue()
    {
        return enchantmentValue;
    }

    @Override
    public SoundEvent getEquipSound()
    {
        return sound;
    }

    @Override
    public Ingredient getRepairIngredient()
    {
        return repairIngredient.get();
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public float getToughness()
    {
        return toughness;
    }

    @Override
    public float getKnockbackResistance()
    {
        return knockbackResistance;
    }
}
