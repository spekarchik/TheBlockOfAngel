package com.pekar.angelblock.tools;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class RendelithicPrimarySword extends ModSword
{
    public RendelithicPrimarySword(Tier material, int attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage, attackSpeed, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker)
    {
        target.addEffect(new MobEffectInstance(MobEffects.WITHER, 100, 0, true, true));
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {
        for (int i = 0; i <= 2; i++)
        {
            tooltipComponents.add(getDescription(i, i == 1));
        }
    }
}
