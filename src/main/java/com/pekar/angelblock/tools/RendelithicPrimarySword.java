package com.pekar.angelblock.tools;

import com.pekar.angelblock.tooltip.ITooltip;
import com.pekar.angelblock.tooltip.TextStyle;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class RendelithicPrimarySword extends ModSword
{
    public RendelithicPrimarySword(ModToolMaterial material, int attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage, attackSpeed, properties);
    }

    @Override
    protected void additionalActionOnHurtEnemy(ItemStack stack, LivingEntity target, ServerPlayer attacker)
    {
        target.addEffect(new MobEffectInstance(MobEffects.WITHER, 100, 0, true, true));
        causePlayerSingleEffectExhaustion(attacker);
    }

    @Override
    public void addTooltip(ItemStack stack, TooltipContext context, ITooltip tooltip, TooltipFlag flag)
    {
        if (!utils.text.showExtendedDescription(tooltip, flag)) return;

        tooltip.ignoreEmptyLines();

        for (int i = 0; i <= 7; i++)
        {
            tooltip.addLine(getDescriptionId(), i)
                    .styledAs(TextStyle.Header, i == 1)
                    .styledAs(TextStyle.DarkGray, i >= 3 && i <= 6)
                    .apply();
        }
    }
}
