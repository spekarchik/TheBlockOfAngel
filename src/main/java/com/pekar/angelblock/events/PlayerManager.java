package com.pekar.angelblock.events;

import com.pekar.angelblock.armor.ModArmor;
import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.armor.IArmorEvents;
import com.pekar.angelblock.events.cleaners.Cleaner;
import com.pekar.angelblock.events.player.IPlayer;
import com.pekar.angelblock.events.player.Player;
import com.pekar.angelblock.events.scheduler.PlayerScheduler;
import com.pekar.angelblock.events.scheduler.allay.RestoreAllaysTask;
import com.pekar.angelblock.items.ItemRegistry;
import com.pekar.angelblock.network.packets.PlaySoundPacket;
import com.pekar.angelblock.network.packets.UpdateArmorDurabilityPacketToClient;
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
        players.put(player.getEntity().getUUID(), player);

        player.updateArmorUsed();

        if (entity instanceof ServerPlayer serverPlayer)
            new UpdateArmorDurabilityPacketToClient().sendToPlayer(serverPlayer);

        for (IArmorEvents armor : player.getArmorTypesUsed())
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

        var playerEntity = player.getEntity();
        Cleaner.clean(playerEntity);

        for (IArmorEvents armor : player.getArmorTypesUsed())
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

        for (IArmorEvents armor : player.getArmorTypesUsed())
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

        var playerEntity = player.getEntity();
        var oldSlotItem = event.getFrom();
        var offHandItemStack = playerEntity.getOffhandItem();

        removeEffectIfHoldItem(playerEntity, MobEffects.NIGHT_VISION, oldSlotItem, offHandItemStack, ItemRegistry.GUARDIAN_EYE.get());
//        removeEffectIfHoldItem(playerEntity, MobEffects.LEVITATION, oldSlotItem, offHandItemStack, ItemRegistry.END_SAPPHIRE.get());
//        removeEffectIfHoldItem(playerEntity, MobEffects.ABSORPTION, oldSlotItem, offHandItemStack, ItemRegistry.BIOS_DIAMOND.get());

        if (entity instanceof ServerPlayer serverPlayer)
        {
            if (event.getTo().is(ItemRegistry.ENERGY_CRYSTAL))
            {
                if (!entity.hasEffect(MobEffects.SPEED) && !entity.hasEffect(MobEffects.SLOWNESS))
                {
                    player.setEffect(MobEffects.SPEED, MobEffectInstance.INFINITE_DURATION, 3);
                    new PlaySoundPacket(SoundEvents.NOTE_BLOCK_HAT.value(), 2.0F).sendToPlayer(serverPlayer);
                }
            }
            else if (oldSlotItem.is(ItemRegistry.ENERGY_CRYSTAL) && !entity.getMainHandItem().is(ItemRegistry.ENERGY_CRYSTAL) && !entity.getOffhandItem().is(ItemRegistry.ENERGY_CRYSTAL))
            {
                if (entity.hasEffect(MobEffects.SPEED))
                {
                    player.clearEffect(MobEffects.SPEED);
                    new PlaySoundPacket(SoundEvents.LEVER_CLICK, 2.0F).sendToPlayer(serverPlayer);
                }
            }
        }

        var slot = event.getSlot();
        if (slot.isArmor() || slot == EquipmentSlot.MAINHAND || slot == EquipmentSlot.OFFHAND)
        {
            Iterable<IArmor> armorUsed = player.getArmorTypesUsed();
            Set<IArmor> armorAffected = new HashSet<>((Collection<IArmor>) armorUsed);
            player.updateArmorUsed();
            armorAffected.addAll((Collection<IArmor>) player.getArmorTypesUsed());

            if (armorAffected.isEmpty())
            {
                Utils.instance.attributeModifiers.removeArmorAttributeModifier(playerEntity);
            }

            ToIntFunction<IArmor> armorPriority = getArmorPriorityFunction(event);

            for (IArmor armor : armorAffected.stream().sorted(Comparator.comparingInt(armorPriority)).toList())
            {
                armor.onLivingEquipmentChangeEvent(event);
            }
        }
    }

    private static @NotNull ToIntFunction<IArmor> getArmorPriorityFunction(LivingEquipmentChangeEvent event)
    {
        var from = event.getFrom();
        var to = event.getTo();
        String fromItemName;
        String toItemName;
        if (from.getItem() instanceof ModArmor modArmor)
        {
            fromItemName = modArmor.getArmorFamilyName();
        }
        else
        {
            fromItemName = "";
        }

        if (to.getItem() instanceof ModArmor modArmor)
        {
            toItemName = modArmor.getArmorFamilyName();
        }
        else
        {
            toItemName = "";
        }

        ToIntFunction<IArmor> armorPriority = a ->
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
        return players.values().stream().filter(p -> p.getEntity().getUUID().equals(uuid)).findAny().orElse(null);
    }

    @Override
    public void addEntityPlayer(net.minecraft.world.entity.player.Player entity)
    {
        IPlayer player = new Player(entity);
        players.put(player.getEntity().getUUID(), player);
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

    private void removeEffectIfHoldItem(net.minecraft.world.entity.player.Player player, Holder<MobEffect> effect, ItemStack slotItemStack, ItemStack offHandItemStack, Item holdItemToCheck)
    {
        if (!(player instanceof ServerPlayer serverPlayer) || !player.hasEffect(effect)) return;

        if ((!slotItemStack.isEmpty() && slotItemStack.getItem() == holdItemToCheck)
                || (!offHandItemStack.isEmpty() && offHandItemStack.getItem() == holdItemToCheck))
        {
            player.removeEffect(effect);
            new PlaySoundPacket(SoundEvents.LEVER_CLICK, 2.0F).sendToPlayer(serverPlayer);
        }
    }
}
