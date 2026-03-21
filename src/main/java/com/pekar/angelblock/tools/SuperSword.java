package com.pekar.angelblock.tools;

import com.pekar.angelblock.potions.PotionRegistry;
import com.pekar.angelblock.tooltip.ITooltip;
import com.pekar.angelblock.tooltip.TextStyle;
import com.pekar.angelblock.utils.SoundType;
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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class SuperSword extends ModSword
{
    public SuperSword(ModToolMaterial material, int attackDamage, float attackSpeed, Properties properties)
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
        var hand = context.getHand();

        if (player.hasEffect(PotionRegistry.SWORD_EXPLOSION_MODE_EFFECT))
        {
            if (!level.isClientSide())
                explode(player, context.getHand(), level, pos);

            return getToolInteractionResult(true, level.isClientSide());
        }
        else if (player.hasEffect(PotionRegistry.SWORD_FIRE_MODE_EFFECT))
        {
            if (!level.isClientSide())
            {
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
        else if (player.hasEffect(PotionRegistry.SWORD_WEB_MODE_EFFECT))
        {
            var blockState = level.getBlockState(pos);
            if (player.isShiftKeyDown() && utils.blocks.types.isCactiPlantableOn(blockState))
            {
                if (!level.isClientSide())
                    plantCacti(player, level, pos, context.getHand(), context.getClickedFace());

                utils.sound.playSoundByBlock(player, pos, SoundType.PLANT);
            }
            else if (Math.abs(player.blockPosition().getX() - pos.getX()) < 2
                    && Math.abs(player.blockPosition().getZ() - pos.getZ()) < 2)
            {
                if (!level.isClientSide())
                    setEffectAround(player, hand, level, pos);

                utils.sound.playSoundByBlock(player, pos, SoundType.BLOCK_CHANGED);
            }
            else
            {
                if (!level.isClientSide())
                    setEffectAhead(player, hand, level, pos);

                utils.sound.playSoundByBlock(player, pos, SoundType.BLOCK_CHANGED);
            }

            return getToolInteractionResult(true, level.isClientSide());
        }

        return InteractionResult.PASS;
    }

    @Override
    protected void additionalActionOnHurtEnemy(ItemStack stack, LivingEntity target, ServerPlayer attacker)
    {
        if (attacker.hasEffect(PotionRegistry.SWORD_EXPLOSION_MODE_EFFECT))
        {
            attacker.level().explode(attacker, target.getX() + 0.1, target.getY() + 0.9, target.getZ() + 0.1, 1.8f, false, Level.ExplosionInteraction.NONE);
            causePlayerSingleEffectExhaustion(attacker);
        }
        else if (attacker.hasEffect(PotionRegistry.SWORD_WEB_MODE_EFFECT))
        {
            target.addEffect(new MobEffectInstance(MobEffects.POISON, 200, 0, true, true));
            causePlayerSingleEffectExhaustion(attacker);
        }
        else if (attacker.hasEffect(PotionRegistry.SWORD_FIRE_MODE_EFFECT))
        {
            target.addEffect(new MobEffectInstance(MobEffects.WITHER, 400, 0, true, true));
            causePlayerSingleEffectExhaustion(attacker);
        }

        target.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 400, 0, true, true));

        var mainHandItem = attacker.getMainHandItem();
        var interactionHand = !mainHandItem.isEmpty() && mainHandItem.getItem().equals(this) ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
        damageProperHandItemIfSurvivalIgnoreClient(attacker, interactionHand, attacker.level());
        causePlayerSingleEffectExhaustion(attacker);
    }

    @Override
    public float getDestroySpeed(ItemStack itemStack, BlockState blockState)
    {
        if (hasCriticalDamage(itemStack)) return 1F;

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
    protected void processBlock(Player player, InteractionHand interactionHand, Level level, BlockPos pos)
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
    public boolean isEnhanced()
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

    @Override
    public void addTooltip(ItemStack stack, TooltipContext context, ITooltip tooltip, TooltipFlag flag)
    {
        if (!utils.text.showExtendedDescription(tooltip)) return;

        tooltip.ignoreEmptyLines();

        for (int i = 0; i <= 21; i++)
        {
            tooltip.addLine(getDescriptionId(), i)
                    .styledAs(TextStyle.Header, i == 1 || i == 7)
                    .styledAs(TextStyle.Subheader, i == 8 || i == 10 || i == 14)
                    .styledAs(TextStyle.Notice, i == 13)
                    .styledAs(TextStyle.DarkGray, i >= 16 && i <= 19)
                    .apply();
        }
    }
}
