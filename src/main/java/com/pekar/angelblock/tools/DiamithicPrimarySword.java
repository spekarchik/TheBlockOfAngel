package com.pekar.angelblock.tools;

import com.pekar.angelblock.potions.PotionRegistry;
import com.pekar.angelblock.tooltip.ITooltip;
import com.pekar.angelblock.tooltip.TextStyle;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class DiamithicPrimarySword extends ModSword
{
    public DiamithicPrimarySword(ModToolMaterial material, int attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage, attackSpeed, properties);
    }

    @Override
    protected void additionalActionOnHurtEnemy(ItemStack stack, LivingEntity target, ServerPlayer attacker)
    {
        if (attacker.hasEffect(PotionRegistry.SWORD_EXPLOSION_MODE_EFFECT))
        {
            attacker.level().explode(attacker, target.getX() + 0.1, target.getY() + 0.9, target.getZ() + 0.1, 1.0f, false, Level.ExplosionInteraction.NONE);

            var mainHandItem = attacker.getMainHandItem();
            var interactionHand = !mainHandItem.isEmpty() && mainHandItem.getItem().equals(this) ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
            damageProperHandItemIfSurvivalIgnoreClient(attacker, interactionHand, attacker.level());
            causePlayerSingleEffectExhaustion(attacker);
        }
    }

    @Override
    public boolean hasExplosionMode()
    {
        return true;
    }

    @Override
    public void addTooltip(ItemStack stack, TooltipContext context, ITooltip tooltip, TooltipFlag flag)
    {
        if (!utils.text.showExtendedDescription(tooltip, flag)) return;

        for (int i = 0; i <= 7; i++)
        {
            tooltip.addLine(getDescriptionId(), i).styledAs(TextStyle.Header, i == 1).styledAs(TextStyle.DarkGray, i >= 3 && i <= 6).apply();
        }
    }
}
