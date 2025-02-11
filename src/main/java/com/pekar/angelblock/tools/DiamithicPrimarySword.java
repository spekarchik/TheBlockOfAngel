package com.pekar.angelblock.tools;

import com.pekar.angelblock.potions.PotionRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class DiamithicPrimarySword extends ModSword
{
    public DiamithicPrimarySword(Tier material, int attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage, attackSpeed, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker)
    {
        if (attacker.hasEffect(PotionRegistry.SWORD_EXPLOSION_MODE_EFFECT))
        {
            attacker.level().explode(attacker, target.getX() + 0.1, target.getY() + 0.9, target.getZ() + 0.1, 1.0f, false, Level.ExplosionInteraction.NONE);
        }

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

    @Override
    public boolean isEnhancedWeapon()
    {
        return true;
    }

    @Override
    public boolean hasExplosionMode()
    {
        return true;
    }
}
