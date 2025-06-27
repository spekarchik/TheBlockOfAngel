package com.pekar.angelblock.items;

import com.pekar.angelblock.events.cleaners.Cleaner;
import com.pekar.angelblock.events.cleaners.TrackedAllay;
import com.pekar.angelblock.network.packets.PlaySoundPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class EvokerAmulet extends ModItemWithDoubleHoverText
{
    private static final double EFFECTIVE_RADIUS = 120.0;

    public EvokerAmulet(Properties properties)
    {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand interactionHand)
    {
        var itemStack = player.getItemInHand(interactionHand);

        if (level instanceof ServerLevel serverLevel)
        {
            var allays = serverLevel.getEntities((Entity)null,
                    player.getBoundingBox().inflate(EFFECTIVE_RADIUS), entity -> entity instanceof Allay);

            if (allays.size() < 3)
            {
                var allay = EntityType.ALLAY.spawn(serverLevel, itemStack, player, player.blockPosition().relative(player.getDirection(), 2).above(), EntitySpawnReason.SPAWN_ITEM_USE, true, true);
                if (allay != null)
                {
                    level.getChunk(player.getOnPos()).addEntity(allay);

                    var targetToTrack = new TrackedAllay(allay, player);
                    Cleaner.add(targetToTrack);
                }

                if (player instanceof ServerPlayer serverPlayer)
                    new PlaySoundPacket(SoundEvents.LEVER_CLICK, 2.0F).sendToPlayer(serverPlayer);
            }
        }

        return sidedSuccess(level.isClientSide());
    }
}
