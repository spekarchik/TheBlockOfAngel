package com.pekar.angelblock.events;

import com.pekar.angelblock.blocks.BlockRegistry;
import com.pekar.angelblock.events.armor.IPlayerArmor;
import com.pekar.angelblock.events.player.IPlayer;
import com.pekar.angelblock.tools.IModTool;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class PlayerDataEvents implements IEventHandler
{
    private final IPlayerManager playerBasic = PlayerManager.instance();

    @SubscribeEvent
    public void onBreakSpeed(PlayerEvent.BreakSpeed event)
    {
        var entityPlayer = event.getEntity();
        var mainHandItem = entityPlayer.getMainHandItem();
        if (!mainHandItem.isEmpty() && mainHandItem.getItem() instanceof IModTool modTool)
        {
            if (modTool.hasExtraLowEfficiencyDamage(mainHandItem))
                event.setNewSpeed(event.getNewSpeed() * 0.2F);
            else if (modTool.hasLowEfficiencyDamage(mainHandItem))
                event.setNewSpeed(event.getNewSpeed() * 0.5F);
        }

        IPlayer player = playerBasic.getPlayerByUUID(event.getEntity().getUUID());
        if (player == null) return;

        for (IPlayerArmor armor : player.getArmorTypesUsed())
        {
            armor.onBreakSpeed(event);
        }
    }

    @SubscribeEvent
    public void onItemCrafted(PlayerEvent.ItemCraftedEvent event)
    {
        var player = event.getEntity();
        if (event.getCrafting().is(BlockRegistry.DEVIL_BLOCK.get().asItem()))
        {
            player.level().playSound(null, player.blockPosition(), SoundEvents.WARDEN_DEATH, SoundSource.PLAYERS, 1.0f, 1.0f);
        }
        else if (event.getCrafting().is(BlockRegistry.ANGEL_BLOCK.get().asItem()))
        {
            player.level().playSound(null, player.blockPosition(), SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 1.0f, 1.0f);
        }
    }
}
