package com.pekar.angelblock.tools;

import com.pekar.angelblock.potions.PotionRegistry;
import com.pekar.angelblock.tooltip.ITooltip;
import com.pekar.angelblock.tooltip.TextStyle;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class RendelithicSword extends ModSword
{
    public RendelithicSword(ModToolMaterial material, int attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage, attackSpeed, properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        var player = context.getPlayer();
        var level = player.level();

        if (!canUseToolEffect(player)) return InteractionResult.PASS;

        var pos = context.getClickedPos();

        if (player.hasEffect(PotionRegistry.SWORD_FIRE_MODE_EFFECT))
        {
            if (!level.isClientSide())
            {
                var hand = context.getHand();
                if (Math.abs(player.blockPosition().getX() - pos.getX()) < 2
                        && Math.abs(player.blockPosition().getZ() - pos.getZ()) < 2)
                {
                    setEffectAround(player, hand, level, pos);
                }
                else
                {
                    setEffectAhead(player, hand, level, pos);
                }
            }

            return getToolInteractionResult(true, level.isClientSide());
        }

        return InteractionResult.PASS;
    }

    @Override
    protected void additionalActionOnHurtEnemy(ItemStack stack, LivingEntity target, ServerPlayer attacker)
    {
        if (attacker.hasEffect(PotionRegistry.SWORD_FIRE_MODE_EFFECT))
        {
            target.addEffect(new MobEffectInstance(MobEffects.WITHER, 400, 0, true, true));
            causePlayerSingleEffectExhaustion(attacker);
        }
    }

    @Override
    protected void processBlock(Player player, InteractionHand interactionHand, Level level, BlockPos pos)
    {
        trySetFire(level, pos);
    }

    @Override
    public boolean hasFireMode()
    {
        return true;
    }

    @Override
    public boolean isEnhanced()
    {
        return true;
    }

    @Override
    public void addTooltip(ItemStack stack, TooltipContext context, ITooltip tooltip, TooltipFlag flag)
    {
        if (!utils.text.showExtendedDescription(tooltip)) return;

        tooltip.ignoreEmptyLines();

        for (int i = 0; i <= 10; i++)
        {
            tooltip.addLine(getDescriptionId(), i)
                    .styledAs(TextStyle.Header, i == 1 || i == 3)
                    .styledAs(TextStyle.DarkGray, i >= 5 && i <= 9)
                    .apply();
        }
    }
}
