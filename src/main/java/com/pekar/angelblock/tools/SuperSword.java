package com.pekar.angelblock.tools;

import com.pekar.angelblock.network.packets.PlaySoundPacket;
import com.pekar.angelblock.network.packets.SoundType;
import com.pekar.angelblock.potions.PotionRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class SuperSword extends ModSword
{
    public SuperSword(Tier material, int attackDamage, float attackSpeed, Properties properties)
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
                explode(player, level, pos);

            return getToolInteractionResult(true, level.isClientSide());
        }

        if (player.hasEffect(PotionRegistry.SWORD_FIRE_MODE_EFFECT))
        {
            if (!level.isClientSide())
            {
                if (Math.abs(player.blockPosition().getX() - pos.getX()) < 2
                        && Math.abs(player.blockPosition().getZ() - pos.getZ()) < 2)
                {
                    setEffectAround(player, level, pos);
                }
                else
                {
                    setEffectAhead(player, level, pos);
                }
            }

            return getToolInteractionResult(true, level.isClientSide());
        }

        if (player.hasEffect(PotionRegistry.SWORD_WEB_MODE_EFFECT))
        {
            if (!level.isClientSide())
            {
                if (level.getBlockState(pos).getBlock() == Blocks.SAND)
                {
                    plantCacti(player, level, pos, context.getHand(), context.getClickedFace());
                    new PlaySoundPacket(SoundType.PLANT).sendToPlayer((ServerPlayer) player);
                }
                else if (Math.abs(player.blockPosition().getX() - pos.getX()) < 2
                        && Math.abs(player.blockPosition().getZ() - pos.getZ()) < 2)
                {
                    setEffectAround(player, level, pos);
                    new PlaySoundPacket(SoundType.BLOCK_CHANGED).sendToPlayer((ServerPlayer) player);
                }
                else
                {
                    setEffectAhead(player, level, pos);
                    new PlaySoundPacket(SoundType.BLOCK_CHANGED).sendToPlayer((ServerPlayer) player);
                }
            }

            return getToolInteractionResult(true, level.isClientSide());
        }

        return InteractionResult.PASS;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker)
    {
        if (attacker.hasEffect(PotionRegistry.SWORD_EXPLOSION_MODE_EFFECT))
        {
            attacker.level().explode(attacker, target.getX() + 0.1, target.getY() + 0.9, target.getZ() + 0.1, 1.8f, false, Level.ExplosionInteraction.NONE);
        }

        target.addEffect(new MobEffectInstance(MobEffects.WITHER, 400, 0, true, true));
        target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 400, 0, true, true));

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
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {
        for (int i = 0; i <= 14; i++)
        {
            tooltipComponents.add(getDescription(i, i == 1 || i == 6, i == 7 || i == 9 || i == 13, i == 12));
        }
    }

    @Override
    protected void processBlock(Player player, Level level, BlockPos pos)
    {
        if (player.hasEffect(PotionRegistry.SWORD_FIRE_MODE_EFFECT))
        {
            trySetFire(level, pos);
        }
        else if (player.hasEffect(PotionRegistry.SWORD_WEB_MODE_EFFECT))
        {
            setWeb(player, level, pos.above());
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

    @Override
    public boolean hasFireMode()
    {
        return true;
    }

    @Override
    public boolean hasWebMode()
    {
        return true;
    }
}
