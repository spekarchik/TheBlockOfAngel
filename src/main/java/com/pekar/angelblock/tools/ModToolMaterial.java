package com.pekar.angelblock.tools;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import org.apache.commons.lang3.NotImplementedException;

import java.util.List;

public class ModToolMaterial implements Tier
{
    private final String name;
    private final int uses;
    private final float speed;
    private final float attackDamageBonus;
    private final int level;
    private final int enchantability;
    private final Ingredient repairIngredient;

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
    public Tool createToolProperties(TagKey<Block> tagKey)
    {
        return new Tool(List.of(Tool.Rule.minesAndDrops(tagKey, this.getSpeed())), getSpeed(), 1);
    }

    @Override
    public TagKey<Block> getIncorrectBlocksForDrops()
    {
        throw new NotImplementedException("Not implemented [AngelBlock].ModToolMaterial.getIncorrectBlocksForDrops().");
    }

    public int getLevel()
    {
        return level;
    }
}
