package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import com.pekar.angelblock.network.packets.ForceLivingEquipmentChangeToClient;
import com.pekar.angelblock.potions.ModMobEffect;
import com.pekar.angelblock.potions.PotionRegistry;
import net.minecraft.server.level.ServerPlayer;

public class JumpNegativeArmorEffect extends NegativeTemporaryArmorEffect
{
    public JumpNegativeArmorEffect(IPlayer player, IArmor armor, int slownessAmplifier, int duration)
    {
        super(player, armor, PotionRegistry.ARMOR_HEAVY_JUMP_EFFECT, slownessAmplifier, duration);
        showIcon();
    }

    @Override
    public void onActivated()
    {
        super.onActivated();

        var playerEntity = player.getEntity();
        if (playerEntity.hasEffect(PotionRegistry.ENERGY_CRYSTAL_EFFECT))
        {
            var energyEffect = playerEntity.getEffect(PotionRegistry.ENERGY_CRYSTAL_EFFECT);
            if (energyEffect.getEffect().value() instanceof ModMobEffect modEffect)
            {
                modEffect.removeUnderlyingEffectFor(playerEntity);
            }

            playerEntity.removeEffect(PotionRegistry.ENERGY_CRYSTAL_EFFECT);
        }
    }

    @Override
    public void onDeactivated()
    {
        super.onDeactivated();

        if (player.getEntity() instanceof ServerPlayer serverPlayer)
        {
            new ForceLivingEquipmentChangeToClient().sendToPlayer(serverPlayer);
        }
    }
}
