package com.pekar.angelblock.tools;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.common.ToolAction;

public class ModRod extends ModHoe
{
    public ModRod(Tier material, int attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage, attackSpeed, properties);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction)
    {
        return false;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment)
    {
        return false;
    }

    @Override
    public boolean isEnchantable(ItemStack stack)
    {
        return false;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book)
    {
        return false;
    }
}
