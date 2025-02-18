package com.pekar.angelblock.events;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.armor.IArmorEvents;
import com.pekar.angelblock.events.block_cleaner.BlockCleaner;
import com.pekar.angelblock.events.player.IPlayer;
import com.pekar.angelblock.events.player.Player;
import com.pekar.angelblock.items.ItemRegistry;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityTravelToDimensionEvent;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

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
//        event.player.sendMessage(new TextComponentString("Player appeared: " + event.player.getName()));

        IPlayer player = new Player(event.getEntity());
        players.put(player.getEntity().getUUID(), player);

//        player.sendMessage("Player joined: " + player.getPlayerName());
//        player.sendMessage("set initial dimension: " + event.getPlayer().level.dimension().location().getPath());

        player.updateArmorUsed();

        for (IArmorEvents armor : player.getArmorTypesUsed())
        {
            armor.onPlayerLoggedInEvent(event);
        }
    }

    @SubscribeEvent
    public void onPlayerLoggedOutEvent(PlayerEvent.PlayerLoggedOutEvent event)
    {
        BlockCleaner.clean(event.getEntity());
        players.remove(event.getEntity().getUUID());
    }

    @SubscribeEvent
    public void onEntityTravelToDimensionEvent(EntityTravelToDimensionEvent event)
    {
        IPlayer player = players.get(event.getEntity().getUUID());
        if (player == null) return;

        BlockCleaner.clean(player.getEntity());

        for (IArmorEvents armor : player.getArmorTypesUsed())
        {
            armor.onEntityTravelToDimensionEvent(event);
        }
    }

    @SubscribeEvent
    public void onPlayerChangedDimensionEvent(PlayerEvent.PlayerChangedDimensionEvent event)
    {
        IPlayer player = players.get(event.getEntity().getUUID());
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
        IPlayer player = players.get(entity.getUUID());
        if (player == null) return;

//        player.sendMessage("Player was cloned.");

        player.updateEntity(entity);
    }

    @SubscribeEvent
    public void onLivingEquipmentChangeEvent(LivingEquipmentChangeEvent event)
    {
        IPlayer player = players.get(event.getEntity().getUUID());
        if (player == null) return;

//        player.sendMessage("EquipmentChange: " + event.getEntityLiving().getName().getString());

        // after coming back from the End World a player entity becomes another instance.
        // player.getArmorInventoryList() works incorrect on the old instance.
        // so, it's necessary to update the player
//        if (player.getEntity() != event.getEntity())
//        {
//            player.sendMessage("player <> EntityLiving !!!");
//            // IT'S UPDATED IN onPlayerClone()
////            player.updateEntity((net.minecraft.world.entity.player.Player) event.getEntityLiving());
//        }

        var playerEntity = player.getEntity();
        var oldSlotItem = event.getFrom();
        var offHandItemStack = playerEntity.getOffhandItem();

        removeEffectIfHoldItem(playerEntity, MobEffects.NIGHT_VISION, oldSlotItem, offHandItemStack, ItemRegistry.GUARDIAN_EYE.get());
//        removeEffectIfHoldItem(playerEntity, MobEffects.LEVITATION, oldSlotItem, offHandItemStack, ItemRegistry.END_SAPPHIRE.get());
//        removeEffectIfHoldItem(playerEntity, MobEffects.ABSORPTION, oldSlotItem, offHandItemStack, ItemRegistry.BIOS_DIAMOND.get());

        Iterable<IArmor> armorUsed = player.getArmorTypesUsed();
        Set<IArmor> armorAffected = new HashSet<>((Collection<IArmor>) armorUsed);
        player.updateArmorUsed();
        armorAffected.addAll((Collection<IArmor>) player.getArmorTypesUsed());

        for (IArmor armor : armorAffected.stream().sorted(Comparator.comparing(IArmor::getPriority)).toList())
        {
            armor.onLivingEquipmentChangeEvent(event);
        }
    }

    @Override
    public IPlayer getPlayerByUUID(UUID uuid)
    {
        return players.values().stream().filter(p -> p.getEntity().getUUID().equals(uuid)).findAny().orElseThrow();
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
        if (!player.hasEffect(effect)) return;

        if ((!slotItemStack.isEmpty() && slotItemStack.getItem() == holdItemToCheck)
                || (!offHandItemStack.isEmpty() && offHandItemStack.getItem() == holdItemToCheck))
        {
            player.removeEffect(effect);
        }
    }
}
