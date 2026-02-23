package com.pekar.angelblock.events;

import com.pekar.angelblock.armor.ModHumanoidArmor;
import com.pekar.angelblock.events.armor.IPlayerArmor;
import com.pekar.angelblock.events.armor.IPlayerArmorEvents;
import com.pekar.angelblock.events.cleaners.Cleaner;
import com.pekar.angelblock.events.player.IPlayer;
import com.pekar.angelblock.events.player.Player;
import com.pekar.angelblock.events.scheduler.PlayerScheduler;
import com.pekar.angelblock.events.scheduler.allay.RestoreAllaysTask;
import com.pekar.angelblock.items.ItemRegistry;
import com.pekar.angelblock.network.packets.PlaySoundPacket;
import com.pekar.angelblock.network.packets.UpdateArmorDurabilityPacketToClient;
import com.pekar.angelblock.potions.ModMobEffect;
import com.pekar.angelblock.potions.PotionRegistry;
import com.pekar.angelblock.utils.Utils;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityTravelToDimensionEvent;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.ToIntFunction;

public class PlayerManager implements IEventHandler, IPlayerManager
{
    private final Map<UUID, IPlayer> players = new ConcurrentHashMap<>();
    private static final IPlayerManager instance;

    static
    {
        instance = new PlayerManager();
    }

    public static IPlayerManager instance()
    {
        return instance;
    }

    @SubscribeEvent
    public void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event)
    {
        var entity = event.getEntity();

        PlayerScheduler.add(new RestoreAllaysTask((ServerPlayer) entity, 20));
        //AllayManager.restoreSavedAllays(entity);

        IPlayer player = new Player(entity);
        players.put(player.getPlayerEntity().getUUID(), player);

        player.updateArmorUsed();

        if (entity instanceof ServerPlayer serverPlayer)
            new UpdateArmorDurabilityPacketToClient().sendToPlayer(serverPlayer);

        for (IPlayerArmorEvents armor : player.getArmorTypesUsed())
        {
            armor.onPlayerLoggedInEvent(event);
        }
    }

    @SubscribeEvent
    public void onPlayerLoggedOutEvent(PlayerEvent.PlayerLoggedOutEvent event)
    {
        var player = (ServerPlayer) event.getEntity();
        Cleaner.clean(player);
        PlayerScheduler.cancelAll(player);
        players.remove(event.getEntity().getUUID());
    }

    @SubscribeEvent
    public void onEntityTravelToDimensionEvent(EntityTravelToDimensionEvent event)
    {
        var entity = event.getEntity();
        IPlayer player = players.get(entity.getUUID());
        if (player == null) return;

        var playerEntity = player.getPlayerEntity();
        Cleaner.clean(playerEntity);

        for (IPlayerArmorEvents armor : player.getArmorTypesUsed())
        {
            armor.onEntityTravelToDimensionEvent(event);
        }
    }

    @SubscribeEvent
    public void onPlayerChangedDimensionEvent(PlayerEvent.PlayerChangedDimensionEvent event)
    {
        var entity = event.getEntity();

        PlayerScheduler.add(new RestoreAllaysTask((ServerPlayer) entity, 20));

        IPlayer player = players.get(entity.getUUID());
        if (player == null) return;

        for (IPlayerArmorEvents armor : player.getArmorTypesUsed())
        {
            armor.onPlayerChangedDimensionEvent(event);
        }
    }

    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event)
    {
        var entity = event.getEntity();

        if (entity.level().isClientSide()) return;

        PlayerScheduler.add(new RestoreAllaysTask((ServerPlayer) entity, 20));

        IPlayer player = players.get(entity.getUUID());
        if (player == null) return;

//        player.sendMessage("Player was cloned.");

        player.updateEntity(entity);
    }

    @SubscribeEvent
    public void onLivingEquipmentChangeEvent(LivingEquipmentChangeEvent event)
    {
        var entity = event.getEntity();
        if (entity instanceof ServerPlayer serverPlayer)
            new UpdateArmorDurabilityPacketToClient().sendToPlayer(serverPlayer);

        IPlayer player = players.get(entity.getUUID());
        if (player == null) return;

        var playerEntity = player.getPlayerEntity();
        var oldSlotItem = event.getFrom();

        removeEffectIfHoldItem(playerEntity, PotionRegistry.ELDER_GUARDIAN_EYE_EFFECT, oldSlotItem, ItemRegistry.GUARDIAN_EYE.get());
//        removeEffectIfHoldItem(playerEntity, MobEffects.LEVITATION, oldSlotItem, ItemRegistry.END_SAPPHIRE.get());
//        removeEffectIfHoldItem(playerEntity, MobEffects.ABSORPTION, oldSlotItem, ItemRegistry.BIOS_DIAMOND.get());

        if (entity instanceof ServerPlayer serverPlayer)
        {
            if (playerEntity.getMainHandItem().is(ItemRegistry.ENERGY_CRYSTAL) || playerEntity.getOffhandItem().is(ItemRegistry.ENERGY_CRYSTAL))
            {
                trySetEnergyCrystalEffect(player);
            }
            else
            {
                removeEffectIfHoldItem(playerEntity, PotionRegistry.ENERGY_CRYSTAL_EFFECT, oldSlotItem, ItemRegistry.ENERGY_CRYSTAL.get());
            }
        }

        var slot = event.getSlot();
        if (slot.isArmor() || slot == EquipmentSlot.MAINHAND || slot == EquipmentSlot.OFFHAND)
        {
            Iterable<IPlayerArmor> armorUsed = player.getArmorTypesUsed();
            Set<IPlayerArmor> armorAffected = new HashSet<>((Collection<IPlayerArmor>) armorUsed);
            player.updateArmorUsed();
            armorAffected.addAll((Collection<IPlayerArmor>) player.getArmorTypesUsed());

            if (armorAffected.isEmpty())
            {
                Utils.instance.attributeModifiers.removeArmorAttributeModifier(playerEntity);
            }

            ToIntFunction<IPlayerArmor> armorPriority = getArmorPriorityFunction(event);

            for (IPlayerArmor armor : armorAffected.stream().sorted(Comparator.comparingInt(armorPriority)).toList())
            {
                armor.onLivingEquipmentChangeEvent(event);
            }
        }
    }

    private void trySetEnergyCrystalEffect(IPlayer player)
    {
        var serverPlayer = (ServerPlayer) player.getPlayerEntity();
        if (!serverPlayer.hasEffect(PotionRegistry.ENERGY_CRYSTAL_EFFECT) && !serverPlayer.hasEffect(MobEffects.SLOWNESS)
                && !serverPlayer.hasEffect(PotionRegistry.ARMOR_HEAVY_JUMP_EFFECT) && !serverPlayer.hasEffect(MobEffects.MINING_FATIGUE))
        {
            player.setMagicItemEffect(PotionRegistry.ENERGY_CRYSTAL_EFFECT, MobEffectInstance.INFINITE_DURATION, 0, true);
            new PlaySoundPacket(SoundEvents.NOTE_BLOCK_HAT.value(), 2.0F).sendToPlayer(serverPlayer);
        }
    }

    private static @NotNull ToIntFunction<IPlayerArmor> getArmorPriorityFunction(LivingEquipmentChangeEvent event)
    {
        var from = event.getFrom();
        var to = event.getTo();
        String fromItemName;
        String toItemName;
        if (from.getItem() instanceof ModHumanoidArmor modArmor)
        {
            fromItemName = modArmor.getArmorFamilyName();
        }
        else
        {
            fromItemName = "";
        }

        if (to.getItem() instanceof ModHumanoidArmor modArmor)
        {
            toItemName = modArmor.getArmorFamilyName();
        }
        else
        {
            toItemName = "";
        }

        ToIntFunction<IPlayerArmor> armorPriority = a ->
        {
            if (a.getFamilyName().equals(fromItemName)) return 0;
            if (a.getFamilyName().equals(toItemName)) return 100;
            return a.getPriority() + 2;
        };

        return armorPriority;
    }

    @Override
    public IPlayer getPlayerByUUID(UUID uuid)
    {
        return players.get(uuid);
    }

    @Override
    public void addEntityPlayer(net.minecraft.world.entity.player.Player entity)
    {
        IPlayer player = new Player(entity);
        players.put(player.getPlayerEntity().getUUID(), player);
        player.updateArmorUsed();
    }

    @Override
    public void sendMessage(String message)
    {
        for (IPlayer player : players.values())
        {
            player.sendMessage(message);
        }
    }

    private void removeEffectIfHoldItem(net.minecraft.world.entity.player.Player player, Holder<MobEffect> effect, ItemStack oldItemStack, Item holdItemToCheck)
    {
        if (!(player instanceof ServerPlayer serverPlayer) || !player.hasEffect(effect)) return;

        if (!oldItemStack.isEmpty() && oldItemStack.getItem() == holdItemToCheck
                && !player.getMainHandItem().is(holdItemToCheck)
                && !player.getOffhandItem().is(holdItemToCheck))
        {
            if (effect.value() instanceof ModMobEffect modEffect)
            {
                modEffect.removeUnderlyingEffectFor(player);
            }

            player.removeEffect(effect);
            new PlaySoundPacket(SoundEvents.LEVER_CLICK, 2.0F).sendToPlayer(serverPlayer);
        }
    }
}
