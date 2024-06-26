package com.pekar.angelblock.tools;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

public class ModToolMaterial implements Tier
{
    private final int uses;
    private final float speed;
    private final float attackDamageBonus;
    private final int level;
    private final int enchantability;
    private final Ingredient repairIngredient;
    private final TagKey<Block> incorrectBlocksForDrops;

    protected ModToolMaterial(int uses, float speed, float attackDamageBonus, int level, int enchantability, Ingredient repairIngredient)
    {
        this.uses = uses;
        this.speed = speed;
        this.attackDamageBonus = attackDamageBonus;
        this.level = level;
        this.enchantability = enchantability;
        this.repairIngredient = repairIngredient;
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
    public Tool createToolProperties(TagKey<Block> tagKey)
    {
        return Tier.super.createToolProperties(tagKey);
    }

    @Override
    public TagKey<Block> getIncorrectBlocksForDrops()
    {
        return incorrectBlocksForDrops;
    }

    public int getLevel()
    {
        return level;
    }
}
