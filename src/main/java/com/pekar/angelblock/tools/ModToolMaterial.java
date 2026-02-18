package com.pekar.angelblock.tools;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

public class ModToolMaterial implements Tier
{
    private final String name;
    private final int uses;
    private final float speed;
    private final float attackDamageBonus;
    private final int level;
    private final int enchantability;
    private final Ingredient repairIngredient;
    private boolean isFireResistant;

    protected ModToolMaterial(String name, int uses, float speed, float attackDamageBonus, int level, int enchantability, Ingredient repairIngredient)
    {
        this.name = name;
        this.uses = uses;
        this.speed = speed;
        this.attackDamageBonus = attackDamageBonus;
        this.level = level;
        this.enchantability = enchantability;
        this.repairIngredient = repairIngredient;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public int getUses()
    {
        return uses;
    }

    @Override
    public float getSpeed()
    {
        return speed;
    }

    @Override
    public float getAttackDamageBonus()
    {
        return attackDamageBonus;
    }

    @Override
    public int getEnchantmentValue()
    {
        return enchantability;
    }

    @Override
    public Ingredient getRepairIngredient()
    {
        return repairIngredient;
    }

    @Override
    public TagKey<Block> getIncorrectBlocksForDrops()
    {
        return BlockTags.INCORRECT_FOR_DIAMOND_TOOL;
    }

    public int getLevel()
    {
        return level;
    }

    public boolean isFireResistant()
    {
        return isFireResistant;
    }

    public ModToolMaterial fireResistant()
    {
        this.isFireResistant = true;
        return this;
    }

    public ModToolMaterial clone(String name, int uses)
    {
        var copy = new ModToolMaterial(name, uses, getSpeed(), getAttackDamageBonus(), getLevel(), getEnchantmentValue(), getRepairIngredient());
        if (isFireResistant()) copy.fireResistant();
        return copy;
    }
}
