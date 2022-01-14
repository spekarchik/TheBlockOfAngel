package com.pekar.angelblock.armor;

import com.pekar.angelblock.items.ItemRegistry;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public class ModArmorMaterial implements ArmorMaterial
{
    private final String name;
    private final int durabilityMultiplier;
    private final int[] slotProtections;
    private final int enchantmentValue;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;
    private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};

    protected static final ArmorMaterial RENDELITHIC = new ModArmorMaterial("angelblock:rendelithic_armor",
            21, new int[] { 3, 6, 8, 3 }, 5, SoundEvents.ARMOR_EQUIP_DIAMOND,
            0F, 0F, () -> Ingredient.of(ItemRegistry.RENDELITHIC_INGOT.get()));

    protected static final ArmorMaterial LIMONITE = new ModArmorMaterial("angelblock:limonite_armor",
            28, new int[] { 3, 5, 8, 3 }, 30, SoundEvents.ARMOR_EQUIP_LEATHER,
            1F, 0F, () -> Ingredient.of(ItemRegistry.LIMONITE_INGOT.get()));

    protected static final ArmorMaterial DIAMITHIC = new ModArmorMaterial("angelblock:diamithic_armor",
            41, new int[] { 3, 6, 9, 3 }, 11, SoundEvents.ARMOR_EQUIP_DIAMOND,
            3F, 0.1F, () -> Ingredient.of(ItemRegistry.DIAMITHIC_INGOT.get()));

    protected static final ArmorMaterial LAPIS = new ModArmorMaterial("angelblock:lapis_armor",
            37, new int[] { 3, 6, 8, 3 }, 21, SoundEvents.ARMOR_EQUIP_DIAMOND,
            2F, 0F, () -> Ingredient.of(ItemRegistry.LAPIS_INGOT.get()));

    protected static final ArmorMaterial SUPER = new ModArmorMaterial("angelblock:super_armor",
            43, new int[] { 3, 6, 9, 3 }, 19, SoundEvents.ARMOR_EQUIP_DIAMOND,
            4F, 0.2F, () -> Ingredient.of(ItemRegistry.SUPER_INGOT.get()));

    public ModArmorMaterial(String name, int durabilityMultiplier, int[] slotProtections, int enchantmentValue,
                            SoundEvent sound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient)
    {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.slotProtections = slotProtections;
        this.enchantmentValue = enchantmentValue;
        this.sound = sound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getDurabilityForSlot(EquipmentSlot equipmentSlot)
    {
        return HEALTH_PER_SLOT[equipmentSlot.getIndex()] * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForSlot(EquipmentSlot equipmentSlot)
    {
        return this.slotProtections[equipmentSlot.getIndex()];
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
