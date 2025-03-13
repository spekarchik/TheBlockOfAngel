package com.pekar.angelblock.tools;

import com.pekar.angelblock.potions.PotionRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import java.util.List;

public class DiamithicSword extends ModSword
{
    public DiamithicSword(Tier material, int attackDamage, float attackSpeed, Properties properties)
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

        if (player.hasEffect(PotionRegistry.SWORD_EXPLOSION_MODE_EFFECT))
        {
            if (!level.isClientSide())
                explode(player, context.getHand(), level, pos);

            return getToolInteractionResult(true, level.isClientSide());
        }

        return InteractionResult.PASS;
    }

    @Override
    protected void additionalActionOnHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker)
    {
        if (attacker.hasEffect(PotionRegistry.SWORD_EXPLOSION_MODE_EFFECT))
        {
            attacker.level().explode(attacker, target.getX() + 0.1, target.getY() + 0.9, target.getZ() + 0.1, 1.8f, false, Level.ExplosionInteraction.NONE);

            if (attacker instanceof Player player)
            {
                var mainHandItem = attacker.getMainHandItem();
                var interactionHand = !mainHandItem.isEmpty() && mainHandItem.getItem().equals(this) ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
                damageProperHandItemIfSurvivalIgnoreClient(player, interactionHand, attacker.level());
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {
        if (!utils.text.showExtendedDescription(tooltipComponents)) return;

        for (int i = 0; i <= 6; i++)
        {
            tooltipComponents.add(getDescription(i, i == 1 || i == 3, false, i == 5));
        }
    }

    @Override
    public boolean isEnhanced()
    {
        return true;
    }

    @Override
    public boolean hasExplosionMode()
    {
        return true;
    }
}
