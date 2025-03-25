package com.pekar.angelblock.tools;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.level.block.Block;

public class ModToolMaterial
{
    private final String name;
    private final int durability;
    private final float speed;
    private final float attackDamageBonus;
    private final int level;
    private final int enchantmentValue;
    private final TagKey<Item> repairIngredient;
    private final ToolMaterial vanillaMaterial;
    private final TagKey<Block> incorrectBlocksForDrops;

    protected ModToolMaterial(String name, TagKey<Block> incorrectBlocksForDrops, int durability, float speed, float attackDamageBonus, int level, int enchantmentValue, TagKey<Item> repairItems)
    {
        this.name = name;
        this.durability = durability;
        this.speed = speed;
        this.attackDamageBonus = attackDamageBonus;
        this.level = level;
        this.enchantmentValue = enchantmentValue;
        this.repairIngredient = repairItems;
        this.incorrectBlocksForDrops = incorrectBlocksForDrops; // BlockTags.INCORRECT_FOR_DIAMOND_TOOL

        this.vanillaMaterial = new ToolMaterial(incorrectBlocksForDrops, durability, speed, attackDamageBonus, enchantmentValue, repairItems);
    }

    public String getName()
    {
        return name;
    }

    public int getDurability()
    {
        return durability;
    }

    public float getSpeed()
    {
        return speed;
    }

    public float getAttackDamageBonus()
    {
        return attackDamageBonus;
    }

    public int getEnchantmentValue()
    {
        return enchantmentValue;
    }

    public TagKey<Item> getRepairItems()
    {
        return repairIngredient;
    }

    public int getLevel()
    {
        return level;
    }

    public TagKey<Block> getIncorrectBlocksForDrops()
    {
        return incorrectBlocksForDrops;
    }

    public ToolMaterial getVanillaMaterial()
    {
        return vanillaMaterial;
    }

    public ModToolMaterial clone(String name, int durability)
    {
        return new ModToolMaterial(name, getIncorrectBlocksForDrops(), durability, getSpeed(), getAttackDamageBonus(), getLevel(), getEnchantmentValue(), getRepairItems());
    }
}
