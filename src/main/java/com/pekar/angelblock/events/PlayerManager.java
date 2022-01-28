package com.pekar.angelblock.events;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.armor.IArmorEvents;
import com.pekar.angelblock.events.block_cleaner.BlockCleaner;
import com.pekar.angelblock.events.player.IPlayer;
import com.pekar.angelblock.events.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerManager implements IEventHandler, IPlayerManager
{
    private final Map<String, IPlayer> players = new ConcurrentHashMap<>();
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

        IPlayer player = new Player(event.getPlayer());
        players.put(player.getPlayerName(), player);

        player.sendMessage("Player joined: " + player.getPlayerName());
        player.sendMessage("set initial dimension: " + event.getPlayer().level.dimension().location().getPath());

        player.updateArmorUsed();

        for (IArmorEvents armor : player.getArmorTypesUsed())
        {
            armor.onPlayerLoggedInEvent(event);
        }
    }

    @SubscribeEvent
    public void onPlayerLoggedOutEvent(PlayerEvent.PlayerLoggedOutEvent event)
    {
        BlockCleaner.clean(event.getPlayer());
        players.remove(event.getPlayer().getName().getContents());
    }

    @SubscribeEvent
    public void onEntityTravelToDimensionEvent(EntityTravelToDimensionEvent event)
    {
        IPlayer player = players.get(event.getEntity().getName().getContents());
        if (player == null) return;

        BlockCleaner.clean(player.getEntity());

        player.sendMessage("was dimension: " + player.getEntity().level.dimension().location().getPath());
        player.sendMessage("set dimension: " + event.getDimension().location().getPath());

        for (IArmorEvents armor : player.getArmorTypesUsed())
        {
            armor.onEntityTravelToDimensionEvent(event);
        }
    }

    @SubscribeEvent
    public void onPlayerClone(net.minecraftforge.event.entity.player.PlayerEvent.Clone event)
    {
        LivingEntity entity = event.getEntityLiving();
        IPlayer player = players.get(entity.getName().getContents());
        if (player == null) return;

        player.sendMessage("Player was cloned.");

        if (entity instanceof net.minecraft.world.entity.player.Player)
        {
            player.updateEntity((net.minecraft.world.entity.player.Player) entity);
        }
    }

    @SubscribeEvent
    public void onLivingEquipmentChangeEvent(LivingEquipmentChangeEvent event)
    {
        IPlayer player = players.get(event.getEntityLiving().getName().getContents());
        if (player == null) return;

        clearSwordEffects(player.getEntity());
//        player.sendMessage("EquipmentChange: " + event.getEntityLiving().getName().getContents());

        // after coming back from the End World a player entity becomes another instance.
        // player.getArmorInventoryList() works incorrect on the old instance.
        // so, it's necessary to update the player
        if (player.getEntity() != event.getEntityLiving())
        {
            player.sendMessage("player <> EntityLiving !!!");
            // IT'S UPDATED IN onPlayerClone()
//            player.updateEntity((net.minecraft.world.entity.player.Player) event.getEntityLiving());
        }

        Iterable<IArmor> armorUsed = player.getArmorTypesUsed();
        Set<IArmor> armorAffected = new HashSet<>((Collection<IArmor>) armorUsed);
        player.updateArmorUsed();
        armorAffected.addAll((Collection<IArmor>) player.getArmorTypesUsed());

        for (IArmor armor : armorAffected)
        {
            armor.onLivingEquipmentChangeEvent(event);
        }
    }

    @Override
    public IPlayer getPlayerByEntityName(String entityName)
    {
        return players.get(entityName);
    }

    @Override
    public IPlayer getPlayerByUUID(UUID uuid)
    {
        return players.values().stream().filter(p -> p.getEntity().getUUID().equals(uuid)).findAny().get();
    }

    @Override
    public void addEntityPlayer(net.minecraft.world.entity.player.Player entity)
    {
        IPlayer player = new Player(entity);
        players.put(player.getPlayerName(), player);
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

    private void clearSwordEffects(net.minecraft.world.entity.player.Player player)
    {
        if (player.level.isClientSide()) return;

//        if (player.hasEffect(MobEffects.FIRE_MODE_POTION))
//        {
//            Item heldItem = player.getHeldItemMainhand().getItem();
//            boolean isRendelithicSword = heldItem == ToolRegistry.RENDELITHIC_SWORD;
//            boolean isSuperSword = heldItem == ToolRegistry.SUPER_SWORD;
//
//            if (!isRendelithicSword && !isSuperSword)
//            {
//                player.removePotionEffect(PotionRegistry.FIRE_MODE_POTION);
//            }
//        }
//
//        if (player.isPotionActive(PotionRegistry.EXPLOSION_MODE_POTION))
//        {
//            Item heldItem = player.getHeldItemMainhand().getItem();
//            boolean isSuperSword = heldItem == ToolRegistry.SUPER_SWORD;
//            boolean isDiamithicSword = heldItem == ToolRegistry.DIAMITHIC_SWORD;
//
//            if (!isDiamithicSword && !isSuperSword)
//            {
//                player.removePotionEffect(PotionRegistry.EXPLOSION_MODE_POTION);
//            }
//        }
//
//        if (player.isPotionActive(PotionRegistry.SINGLE_MODE_POTION))
//        {
//            Item heldItem = player.getHeldItemMainhand().getItem();
//            if (!Utils.isEnhancedTool(heldItem))
//            {
//                player.removePotionEffect(PotionRegistry.SINGLE_MODE_POTION);
//            }
//        }
    }
}
