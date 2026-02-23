package com.pekar.angelblock.events;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.cleaners.Cleaner;
import com.pekar.angelblock.events.player.IPlayer;
import com.pekar.angelblock.events.scheduler.PlayerScheduler;
import com.pekar.angelblock.events.scheduler.allay.RestoreAllaysTask;
import com.pekar.angelblock.items.ItemRegistry;
import com.pekar.angelblock.potions.PotionRegistry;
import com.pekar.angelblock.tools.IModTool;
import com.pekar.angelblock.tools.IModToolEnhanceable;
import com.pekar.angelblock.utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Relative;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.EnumSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerInteractionEvents implements IEventHandler
{
    private final IPlayerManager playerBasic = PlayerManager.instance();
    private static final Map<BlockPos, ILivingDeathEventHandler> livingDeathEventListeners = new ConcurrentHashMap<>();

    @SubscribeEvent
    public void onLivingHurtEvent(LivingIncomingDamageEvent event)
    {
        LivingEntity entity = event.getEntity();
        var damageSource = event.getSource();
        var attacker = damageSource.getEntity();

        if (attacker != null && (damageSource.is(DamageTypes.PLAYER_ATTACK) || damageSource.is(DamageTypes.MOB_ATTACK)))
        {
            var weapon = attacker.getWeaponItem();
            if (weapon != null && weapon.getItem() instanceof IModTool modTool && modTool.hasCriticalDamage(weapon))
            {
                event.setCanceled(true);
                return;
            }
        }

        IPlayer player = playerBasic.getPlayerByUUID(entity.getUUID());
        if (player == null) return;

        for (IArmor armor : player.getArmorTypesUsed())
        {
            armor.onLivingHurtEvent(event);
        }
    }

    @SubscribeEvent
    public void onLivingDamageEvent(LivingDamageEvent.Pre event)
    {
        LivingEntity entity = event.getEntity();
        var damageSource = event.getSource();
        var attacker = damageSource.getEntity();

        if (attacker != null && (damageSource.is(DamageTypes.PLAYER_ATTACK) || damageSource.is(DamageTypes.MOB_ATTACK)))
        {
            var weapon = attacker.getWeaponItem();
            if (weapon != null && weapon.getItem() instanceof IModTool modTool)
            {
                if (modTool.hasExtraLowEfficiencyDamage(weapon))
                    event.setNewDamage(event.getNewDamage() * 0.4F);
                else if (modTool.hasLowEfficiencyDamage(weapon))
                    event.setNewDamage(event.getNewDamage() * 0.6F);
            }
        }

        IPlayer player = playerBasic.getPlayerByUUID(entity.getUUID());
        if (player == null) return;

        for (IArmor armor : player.getArmorTypesUsed())
        {
            armor.onLivingDamageEvent(event);
        }
    }

    @SubscribeEvent
    public void onEffectAdded(MobEffectEvent.Added event)
    {
        var entity = event.getEntity();
        IPlayer player = playerBasic.getPlayerByUUID(entity.getUUID());
        if (player == null) return;

        for (IArmor armor : player.getArmorTypesUsed())
        {
            armor.onEffectAddedEvent(event);
        }
    }

    @SubscribeEvent
    public void onLivingJumpEvent(LivingEvent.LivingJumpEvent event)
    {
        IPlayer player = playerBasic.getPlayerByUUID(event.getEntity().getUUID());
        if (player == null) return;

        for (IArmor armor : player.getArmorTypesUsed())
        {
            armor.onLivingJumpEvent(event);
        }
    }

    @SubscribeEvent
    public void onLivingFallEvent(LivingFallEvent event)
    {
        IPlayer player = playerBasic.getPlayerByUUID(event.getEntity().getUUID());
        if (player == null) return;

        for (IArmor armor : player.getArmorTypesUsed())
        {
            armor.onLivingFallEvent(event);
        }
    }

    @SubscribeEvent
    public void onBreakEvent(BlockEvent.BreakEvent event)
    {
        var pos = event.getPos();
        var player = event.getPlayer();

        var tool = player.getMainHandItem();
        if (!tool.isEmpty() && tool.getItem() instanceof IModToolEnhanceable modTool)
        {
            if (modTool.hasCriticalDamage(tool))
            {
                if (player.hasEffect(PotionRegistry.TOOL_ADVANCED_MODE_EFFECT))
                    player.removeEffect(PotionRegistry.TOOL_ADVANCED_MODE_EFFECT);
            }

            event.setCanceled(modTool.preventBlockBreak(player, tool, pos));
        }
    }

    //@SubscribeEvent
    public void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event)
    {
    }

    @SubscribeEvent
    public void onLivingInteractionEvent(PlayerInteractEvent.EntityInteractSpecific event)
    {
        if (!(event.getTarget() instanceof LivingEntity target)) return;
        if (target instanceof Player) return;

        var itemStack = event.getItemStack();
        if (itemStack.is(ItemRegistry.SOARING_SPORE_ESSENCE))
        {
            if (!target.hasEffect(MobEffects.GLOWING) || !target.hasEffect(MobEffects.SLOW_FALLING))
            {
                var player = event.getEntity();
                if (event.getSide() == LogicalSide.SERVER)
                {
                    target.addEffect(new MobEffectInstance(MobEffects.GLOWING, MobEffectInstance.INFINITE_DURATION, 0, true, true));
                    target.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, MobEffectInstance.INFINITE_DURATION, 0, true, true));

                    if (!player.isCreative())
                        itemStack.shrink(1);
                }

                Utils.instance.sound.playSoundByLivingEntity(player, target, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.NEUTRAL, 1F, 1F);

                event.setCanceled(true);
                event.setCancellationResult(event.getSide() == LogicalSide.CLIENT ? InteractionResult.SUCCESS : InteractionResult.SUCCESS_SERVER);
            }
        }
        else if (itemStack.is(ItemRegistry.MARINE_CRYSTAL))
        {
            target.removeAllEffects();
            var player = event.getEntity();
            Utils.instance.sound.playSoundByLivingEntity(player, target, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.NEUTRAL, 1F, 1F);

            event.setCanceled(true);
            event.setCancellationResult(event.getSide() == LogicalSide.CLIENT ? InteractionResult.SUCCESS : InteractionResult.SUCCESS_SERVER);
        }
    }

    @SubscribeEvent
    public void onLivingDeathEvent(LivingDeathEvent event)
    {
        LivingEntity entity = event.getEntity();

        if (!entity.level().isClientSide())
        {
            if (entity instanceof Player player)
                Cleaner.clean(player);

            for (var handler : livingDeathEventListeners.values())
            {
                handler.onLivingDeathEvent(event);
            }

            if (entity.hasEffect(MobEffects.LUCK) && !event.isCanceled() && !event.getSource().is(DamageTypes.GENERIC_KILL))
            {
                int luckLevel = entity.getEffect(MobEffects.LUCK).getAmplifier() + 1;
                float chanceToAvoidDeath = 0.25F * luckLevel;

                if (entity.getRandom().nextFloat() < chanceToAvoidDeath)
                {
                    event.setCanceled(true);
                    entity.setHealth(1.0F);
                    teleportPlayer(entity);
                }
            }
        }
    }

    private void teleportPlayer(LivingEntity entity)
    {
        if (entity instanceof ServerPlayer serverPlayer)
        {
            TeleportTransition.PostTeleportTransition postTeleportTransition = p -> {
                if (p instanceof LivingEntity livingEntity)
                    protectPlayer(livingEntity);
                if (p instanceof ServerPlayer player)
                    PlayerScheduler.add(new RestoreAllaysTask(player, 20));
            };

            TeleportTransition transition = serverPlayer.findRespawnPositionAndUseSpawnBlock(true, postTeleportTransition);

            var targetLevel = transition.newLevel();
            Vec3 targetPos = transition.position();

            serverPlayer.teleportTo(
                    targetLevel,
                    targetPos.x,
                    targetPos.y,
                    targetPos.z,
                    EnumSet.noneOf(Relative.class),
                    serverPlayer.getYRot(), serverPlayer.getXRot(),
                    true
            );

            transition.postTeleportTransition().onTransition(serverPlayer);

            ((ServerLevel)serverPlayer.level()).sendParticles(
                    ParticleTypes.PORTAL,
                    targetPos.x, targetPos.y + 1, targetPos.z,
                    50, 0.5, 1, 0.5, 0.1
            );

            serverPlayer.level().playSound(null, serverPlayer.blockPosition(), SoundEvents.PORTAL_TRAVEL, SoundSource.PLAYERS);
        }
    }

    private void protectPlayer(LivingEntity player)
    {
        for (MobEffectInstance effect : player.getActiveEffects())
        {
            if (effect.getEffect().value().getCategory() == MobEffectCategory.HARMFUL)
            {
                player.removeEffect(effect.getEffect());
            }
        }

        player.clearFire();
        player.setDeltaMovement(Vec3.ZERO);
        player.fallDistance = 0;
        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 1));
        player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 2400, 0));
        player.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, 40));
        player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 40));
    }

    public static void subscribeLivingDeath(ILivingDeathEventHandler handler)
    {
        livingDeathEventListeners.put(handler.getPosition(), handler);
    }

    public static void unsubscribeLivingDeath(ILivingDeathEventHandler handler)
    {
        livingDeathEventListeners.remove(handler.getPosition());
    }
}
