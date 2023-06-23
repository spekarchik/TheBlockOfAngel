package com.pekar.angelblock.tools;

import com.pekar.angelblock.potions.PotionRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SuperPrimarySword extends ModSword
{
    public SuperPrimarySword(Tier material, int attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage, attackSpeed, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker)
    {
        if (attacker.hasEffect(PotionRegistry.SWORD_EXPLOSION_MODE_EFFECT.get()))
        {
            attacker.level().explode(attacker, target.getX() + 0.1, target.getY() + 0.9, target.getZ() + 0.1, 1.0f, false, Level.ExplosionInteraction.NONE);
        }

        target.addEffect(new MobEffectInstance(MobEffects.WITHER, 100, 0, true, true));
        target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 0, true, true));

        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public float getDestroySpeed(ItemStack itemStack, BlockState blockState)
    {
        var block = blockState.getBlock();
        if (block == Blocks.COBWEB)
        {
            return 60.0F;
        }
        else if (block == Blocks.CACTUS)
        {
            return 40.0F;
        }

        return super.getDestroySpeed(itemStack, blockState);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag)
    {
        for (int i = 1; i <= 5; i++)
        {
            components.add(getDescription(i, i == 1));
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
