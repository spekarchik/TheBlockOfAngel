package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IPlayerArmor;
import com.pekar.angelblock.events.effect.base.NegativeTemporaryArmorEffect;
import com.pekar.angelblock.events.player.IPlayer;
import com.pekar.angelblock.network.packets.ForceLivingEquipmentChangeToClient;
import com.pekar.angelblock.potions.ModMobEffect;
import com.pekar.angelblock.potions.PotionRegistry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffects;

public class JumpNegativeArmorEffect extends NegativeTemporaryArmorEffect
{
    public JumpNegativeArmorEffect(IPlayer player, IPlayerArmor armor, int slownessAmplifier, int duration)
    {
        super(player, armor, PotionRegistry.ARMOR_HEAVY_JUMP_EFFECT, slownessAmplifier, duration);
        setup().showIcon();
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

        if (playerEntity.hasEffect(MobEffects.JUMP_BOOST))
        {
            playerEntity.removeEffect(MobEffects.JUMP_BOOST);
        }

        if (playerEntity.hasEffect(MobEffects.DOLPHINS_GRACE))
        {
            playerEntity.removeEffect(MobEffects.DOLPHINS_GRACE);
        }

        if (playerEntity.hasEffect(MobEffects.SPEED))
        {
            playerEntity.removeEffect(MobEffects.SPEED);
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
