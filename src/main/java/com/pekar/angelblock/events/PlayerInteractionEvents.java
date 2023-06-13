package com.pekar.angelblock.events;

import com.pekar.angelblock.blocks.BlockRegistry;
import com.pekar.angelblock.blocks.tile_entities.AngelBlockEntity;
import com.pekar.angelblock.blocks.tile_entities.DevilBlockEntity;
import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerInteractionEvents implements IEventHandler
{
    private final IPlayerManager playerBasic = PlayerManager.instance();
    private static final Map<BlockPos, ILivingDeathEventHandler> livingDeathEventListeners = new ConcurrentHashMap<>();

    @SubscribeEvent
    public void onLivingHurtEvent(LivingHurtEvent event)
    {
        LivingEntity entity = event.getEntity();
        IPlayer player = playerBasic.getPlayerByEntityName(entity.getName().getString());
        if (player == null) return;

        for (IArmor armor : player.getArmorTypesUsed())
        {
            armor.onLivingHurtEvent(event);
        }
    }

    @SubscribeEvent
    public void onLivingAttackEvent(LivingAttackEvent event)
    {
        IPlayer player = playerBasic.getPlayerByEntityName(event.getEntity().getName().getString());
        if (player == null) return;

        for (IArmor armor : player.getArmorTypesUsed())
        {
            armor.onLivingAttackEvent(event);
        }
    }

    @SubscribeEvent
    public void onLivingJumpEvent(LivingEvent.LivingJumpEvent event)
    {
        IPlayer player = playerBasic.getPlayerByEntityName(event.getEntity().getName().getString());
        if (player == null) return;

        for (IArmor armor : player.getArmorTypesUsed())
        {
            armor.onLivingJumpEvent(event);
        }
    }

    @SubscribeEvent
    public void onLivingFallEvent(LivingFallEvent event)
    {
        IPlayer player = playerBasic.getPlayerByEntityName(event.getEntity().getName().getString());
        if (player == null) return;

        for (IArmor armor : player.getArmorTypesUsed())
        {
            armor.onLivingFallEvent(event);
        }
    }

    // TODO: Remove?
    public void onBreakEvent(BlockEvent.BreakEvent event)
    {
        IPlayer player = playerBasic.getPlayerByEntityName(event.getPlayer().getName().getString());
        if (player == null) return;

        ItemStack tool = event.getPlayer().getItemInHand(InteractionHand.MAIN_HAND);
        if (tool == null) return;
        Item item = tool.getItem();
        if (item == null) return;

        BlockState blockState = event.getState();
        Block block = blockState.getBlock();

//        if (item instanceof ModShovel)
//        {
//            if (item instanceof SuperShovel)
//            {
//                boolean isToolEffective = block.isToolEffective("shovel", blockState);
//                event.setCanceled(isToolEffective && Utils.isNearLavaOrWaterOrUnsafe(event.getPlayer(), event.getPos()));
//                return;
//            }
//            else if (item instanceof LapisShovel)
//            {
//                boolean isToolEffective = block.isToolEffective("shovel", blockState);
//                event.setCanceled(isToolEffective && Utils.isNearWater(event.getWorld(), event.getPos()));
//                return;
//            }
//            else if (item instanceof RendelithicShovel)
//            {
//                boolean isToolEffective = block.isToolEffective("shovel", blockState);
//                event.setCanceled(isToolEffective && Utils.isNearLava(event.getWorld(), event.getPos()));
//                return;
//            }
//        }
//        else if (item instanceof ModPickaxe)
//        {
//            if (item instanceof RendelithicPickaxe)
//            {
//                boolean isToolEffective = block.isToolEffective("pickaxe", blockState);
//                event.setCanceled(isToolEffective && Utils.isNearLava(event.getWorld(), event.getPos()));
//                return;
//            }
//            else if (item instanceof LapisPickaxe)
//            {
//                boolean isToolEffective = block.isToolEffective("pickaxe", blockState);
//                event.setCanceled(isToolEffective && Utils.isNearWater(event.getWorld(), event.getPos()));
//                return;
//            }
//            else if (item instanceof DiamithicPickaxe)
//            {
//                boolean isToolEffective = block.isToolEffective("pickaxe", blockState);
//                event.setCanceled(isToolEffective && !Utils.isFallSafeWide(event.getPlayer(), event.getPos()));
//                return;
//            }
//            else if (item instanceof LimonitePickaxe)
//            {
//                boolean isToolEffective = block.isToolEffective("pickaxe", blockState);
//                event.setCanceled(isToolEffective && !Utils.isFallSafeExact(event.getPlayer(), event.getPos()));
//                return;
//            }
//            else if (item instanceof SuperPickaxe)
//            {
//                boolean isToolEffective = block.isToolEffective("pickaxe", blockState);
//                event.setCanceled(isToolEffective && Utils.isNearLavaOrWaterOrUnsafe(event.getPlayer(), event.getPos()));
//                return;
//            }
//        }
//        else
//        {
//            if (item instanceof SuperAxe)
//            {
//                boolean isToolEffective = block.isToolEffective("axe", blockState);
//                event.setCanceled(isToolEffective && Utils.isNearLavaOrWaterOrUnsafeOrStandingOnBreakingBlock(event.getPlayer(), event.getPos()));
//                return;
//            }
//            else if (item instanceof DiamithicAxe)
//            {
//                boolean isToolEffective = block.isToolEffective("axe", blockState);
//                event.setCanceled(isToolEffective && !Utils.isFallSafeWide(event.getPlayer(), event.getPos()));
//                return;
//            }
//            else if (item instanceof LimoniteAxe)
//            {
//                boolean isToolEffective = block.isToolEffective("axe", blockState);
//                event.setCanceled(isToolEffective && Utils.isStandingOnBreakingBlock(event.getPlayer(), event.getPos()));
//                return;
//            }
//        }

    }

    @SubscribeEvent
    public void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event)
    {
        var player = event.getEntity();

        var pos = event.getPos();
        var level = player.level();
        var block = level.getBlockState(pos).getBlock();

        if (block == BlockRegistry.ANGEL_BLOCK.get())
        {
            var blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof AngelBlockEntity angelBlockEntity)
            {
                var interactionItemStack = player.getItemInHand(event.getHand());
                if (interactionItemStack.isEmpty()) return;

                var isClientSide = level.isClientSide();

                var interactionItem = interactionItemStack.getItem();
                if (interactionItem == Items.FLINT)
                {
                    if (!isClientSide)
                        angelBlockEntity.resetFilter(player);

                    event.setUseItem(Event.Result.ALLOW);
                }
                else
                {
                    if (!isClientSide)
                        angelBlockEntity.addMonsterToFilter(interactionItem, player);

                    event.setUseItem(Event.Result.ALLOW);
                }
            }
        }

        if (block == BlockRegistry.DEVIL_BLOCK.get())
        {
            var blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof DevilBlockEntity devilBlockEntity)
            {
                var interactionItemStack = player.getItemInHand(event.getHand());
                if (interactionItemStack.isEmpty()) return;

                var interactionItem = interactionItemStack.getItem();

                var success = devilBlockEntity.spawnMonster(interactionItem, player, interactionItemStack);

                event.setUseItem(success ? Event.Result.ALLOW : Event.Result.DEFAULT);
            }
        }
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
