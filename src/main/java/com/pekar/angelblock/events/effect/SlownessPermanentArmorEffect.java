package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IPlayerArmor;
import com.pekar.angelblock.events.effect.base.PermanentPlayerArmorEffect;
import com.pekar.angelblock.events.player.IPlayer;
import com.pekar.angelblock.network.packets.ForceLivingEquipmentChangeToClient;
import com.pekar.angelblock.potions.ModMobEffect;
import com.pekar.angelblock.potions.PotionRegistry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffects;

public class SlownessPermanentArmorEffect extends PermanentPlayerArmorEffect
{
    public SlownessPermanentArmorEffect(IPlayer player, IPlayerArmor armor, int defaultAmplifier)
    {
        super(player, armor, MobEffects.MOVEMENT_SLOWDOWN, defaultAmplifier);
    }

    @Override
    public void onActivated()
    {
        super.onActivated();

        var playerEntity = mob.getEntity();
        if (playerEntity.hasEffect(PotionRegistry.ENERGY_CRYSTAL_EFFECT))
        {
            var energyEffect = playerEntity.getEffect(PotionRegistry.ENERGY_CRYSTAL_EFFECT);
            if (energyEffect.getEffect().value() instanceof ModMobEffect modEffect)
            {
                modEffect.removeUnderlyingEffectFor(playerEntity);
            }

            playerEntity.removeEffect(PotionRegistry.ENERGY_CRYSTAL_EFFECT);
        }

        if (playerEntity.hasEffect(MobEffects.MOVEMENT_SPEED))
        {
            playerEntity.removeEffect(MobEffects.MOVEMENT_SPEED);
        }

        if (playerEntity.hasEffect(MobEffects.DOLPHINS_GRACE))
        {
            playerEntity.removeEffect(MobEffects.DOLPHINS_GRACE);
        }
    }

    @Override
    public void onDeactivated()
    {
        super.onDeactivated();

        if (mob.getEntity() instanceof ServerPlayer serverPlayer)
        {
            new ForceLivingEquipmentChangeToClient().sendToPlayer(serverPlayer);
        }
    }
}
