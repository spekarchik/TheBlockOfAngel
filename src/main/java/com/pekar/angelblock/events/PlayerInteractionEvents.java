package com.pekar.angelblock.events;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import com.pekar.angelblock.potions.PotionRegistry;
import com.pekar.angelblock.tools.IModTool;
import com.pekar.angelblock.tools.IModToolEnhanceable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

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
    public void onLivingDeathEvent(LivingDeathEvent event)
    {
        LivingEntity entity = event.getEntity();

        if (!entity.level().isClientSide())
        {
            for (var handler : livingDeathEventListeners.values())
            {
                handler.onLivingDeathEvent(event);
            }
        }
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
