package com.pekar.angelblock.tools;

import com.pekar.angelblock.potions.PotionRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
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

import java.util.List;

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
    protected void additionalActionOnHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker)
    {
        if (attacker.hasEffect(PotionRegistry.SWORD_FIRE_MODE_EFFECT))
            target.addEffect(new MobEffectInstance(MobEffects.WITHER, 400, 0, true, true));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {
        if (!utils.text.showExtendedDescription(tooltipComponents)) return;

        for (int i = 0; i <= 7; i++)
        {
            tooltipComponents.add(getDescription(i, i == 1 || i == 3, false, false, false, i == 5 || i == 6));
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
}
