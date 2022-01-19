package com.pekar.angelblock.events;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.armor.IArmorEvents;
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

//        player.sendMessage("Player joined: " + event.player.getName());
//        player.sendMessage("set initial dimension: " + event.player.dimension);

        player.updateArmorUsed();

        for (IArmorEvents armor : player.getArmorTypesUsed())
        {
            armor.onPlayerLoggedInEvent(event);
        }
    }

    @SubscribeEvent
    public void onPlayerLoggedOutEvent(PlayerEvent.PlayerLoggedOutEvent event)
    {
//        BlockCleaner.clean(event.player);
        players.remove(event.getPlayer().getName().getContents());
    }

    @SubscribeEvent
    public void onEntityTravelToDimensionEvent(EntityTravelToDimensionEvent event)
    {
        IPlayer player = players.get(event.getEntity().getName().getContents());
        if (player == null) return;

//        BlockCleaner.clean(player.getEntity());

//        updateDimension(event);
//        player.sendMessage("set dimension: " + player.getDimension());

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
//        player.sendMessage("EquipmentChange " + event.getEntityLiving().getName());

//        if (player.getEntity() != event.getEntityLiving())
//            player.sendMessage("player <> EntityLiving !!!");

        // after coming back from the End World a player entity becomes another instance.
        // player.getArmorInventoryList() works incorrect on the old instance.
        // so, it's necessary to update the player
        if (player.getEntity() != event.getEntityLiving())
        {
            player.updateEntity((net.minecraft.world.entity.player.Player) event.getEntityLiving());
        }

        Iterable<IArmor> armorUsed = player.getArmorTypesUsed();
        Set<IArmorEvents> armorAffected = new HashSet<>((Collection<? extends IArmorEvents>) armorUsed);
        player.updateArmorUsed();
        armorAffected.addAll((Collection<? extends IArmorEvents>) player.getArmorTypesUsed());

        for (IArmorEvents armor : armorAffected)
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

//    private void updateDimension(EntityTravelToDimensionEvent event)
//    {
//        net.minecraft.world.entity.player.Player player = (net.minecraft.world.entity.player.Player) event.getEntity();
//        int dimension = player.dimension == event.getDimension() ? 0 : event.getDimension(); // minecraft bug?
//        players.get(player.getName()).updateDimension(dimension);
//    }

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
