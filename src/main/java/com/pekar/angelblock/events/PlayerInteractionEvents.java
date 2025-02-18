package com.pekar.angelblock.events;

import com.pekar.angelblock.blocks.BlockRegistry;
import com.pekar.angelblock.blocks.tile_entities.AngelBlockEntity;
import com.pekar.angelblock.blocks.tile_entities.AngelRodBlockEntity;
import com.pekar.angelblock.blocks.tile_entities.DevilBlockEntity;
import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import com.pekar.angelblock.tools.IModTool;
import com.pekar.angelblock.tools.IModToolEnhanced;
import com.pekar.angelblock.tools.ToolRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
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
        IPlayer player = playerBasic.getPlayerByUUID(entity.getUUID());
        if (player == null) return;

        for (IArmor armor : player.getArmorTypesUsed())
        {
            armor.onLivingHurtEvent(event);
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
        var level = player.level();
        var blockEntity = level.getBlockEntity(pos);

        if (player instanceof ServerPlayer serverPlayer && !serverPlayer.isCreative())
        {
            if (blockEntity instanceof AngelRodBlockEntity angelRodBlockEntity)
            {
                if (player.isSteppingCarefully())
                {
                    var itemStack1 = new ItemStack(ToolRegistry.END_MAGNETIC_ROD.get());
                    itemStack1.setDamageValue(angelRodBlockEntity.getDamage());
                    var itemStack2 = new ItemStack(BlockRegistry.ANGEL_BLOCK.get());
                    var itemStack3 = new ItemStack(Items.TOTEM_OF_UNDYING);

                    player.drop(itemStack1, false);
                    player.drop(itemStack2, false);
                    player.drop(itemStack3, false);

                    return;
                }
                else
                {
                    var itemStack = new ItemStack(ToolRegistry.ANGEL_ROD.get());
                    itemStack.setDamageValue(angelRodBlockEntity.getDamage());
                    player.drop(itemStack, true);

                    return;
                }
            }
        }

        var tool = player.getMainHandItem();
        if (!tool.isEmpty() && tool.getItem() instanceof IModToolEnhanced modTool)
        {
            event.setCanceled(modTool.preventBlockBreak(player, level, pos));
        }

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

                    event.setUseItem(TriState.TRUE);
                }
                else
                {
                    if (!isClientSide)
                        angelBlockEntity.addMonsterToFilter(interactionItem, player);

                    event.setUseItem(TriState.TRUE);
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

                event.setUseItem(success ? TriState.TRUE : TriState.DEFAULT);
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
