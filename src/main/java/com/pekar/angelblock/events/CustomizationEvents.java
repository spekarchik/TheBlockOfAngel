package com.pekar.angelblock.events;

import com.pekar.angelblock.menus.CustomCraftingMenuProvider;
import com.pekar.angelblock.menus.CustomSmithingMenuProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

public class CustomizationEvents implements IEventHandler
{
    @SubscribeEvent
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event)
    {
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        Player player = event.getEntity();
        BlockState state = level.getBlockState(pos);

        if (state.getBlock() == Blocks.SMITHING_TABLE)
        {
            event.setCanceled(true); // Not the standard menu to be shown
            event.setCancellationResult(level.isClientSide ? InteractionResult.SUCCESS : InteractionResult.SUCCESS_SERVER);

            if (!level.isClientSide && player instanceof ServerPlayer serverPlayer)
            {
                var accessLevel = ContainerLevelAccess.create(level, pos);
                serverPlayer.openMenu(new CustomSmithingMenuProvider(accessLevel));
            }
        }
        else if (state.getBlock() == Blocks.CRAFTING_TABLE)
        {
            event.setCanceled(true); // Not the standard menu to be shown
            event.setCancellationResult(level.isClientSide() ? InteractionResult.SUCCESS : InteractionResult.SUCCESS_SERVER);

            if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer)
            {
                var accessLevel = ContainerLevelAccess.create(level, pos);
                serverPlayer.openMenu(new CustomCraftingMenuProvider(accessLevel));
            }
        }
    }
}
