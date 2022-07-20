package com.pekar.angelblock.events.armor;

import com.pekar.angelblock.armor.ArmorRegistry;
import com.pekar.angelblock.events.effect.*;
import com.pekar.angelblock.events.player.IPlayer;
import com.pekar.angelblock.keybinds.KeyRegistry;
import com.pekar.angelblock.network.packets.CreeperDetectedPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class DiamithicArmor extends Armor
{
    private final IArmorEffect strengthEffect;
    private final IArmorEffect nightVisionEffect;
    private final SwitchingEffectSynchronizer jumpBoostEffect;
    private final IArmorEffect healthBoostEffect;
    private final IArmorEffect hasteEffect;
    private final CreeperDetectedPacket creeperDetectedPacket = new CreeperDetectedPacket();
    private int creeperDetectedCounter;

    private static final double CREEPER_NOTIFY_DISTANCE = 17.0;
    private static final int CREEPER_GLOWING_EFFECT_DURATION = 1200;

    public DiamithicArmor(IPlayer player)
    {
        super(player);
        strengthEffect = new StrengthArmorEffect(player, this, 1);
        nightVisionEffect = new NightVisionArmorEffect(player, this);
        healthBoostEffect = new HealthBoostArmorEffect(player, this, 2);
        hasteEffect = new HasteArmorEffect(player, this);

        var jumpBoostEffect = new JumpBoostArmorEffect(player, this, 2);
        var slowFallingEffect = new SlowFallingSwitchingEffect(player, this);
        this.jumpBoostEffect = new SwitchingEffectSynchronizer(jumpBoostEffect);
        this.jumpBoostEffect.addDependentEffect(slowFallingEffect);
    }

    @Override
    public void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event)
    {
        nightVisionEffect.updateSwitchState();
        jumpBoostEffect.updateSwitchState();
    }

    @Override
    public void onLivingHurtEvent(LivingHurtEvent event)
    {
        boolean isFullArmorSet = player.isFullArmorSetPutOn(this);
        if (isFullArmorSet && event.getSource().isExplosion())
        {
            event.setAmount(event.getAmount() * 0.5f);
        }
    }

    @Override
    public void onLivingAttackEvent(LivingAttackEvent event)
    {
        // none
    }

    @Override
    public void onLivingEquipmentChangeEvent(LivingEquipmentChangeEvent event)
    {
        nightVisionEffect.updateEffectAvailability();
        strengthEffect.updateEffectAvailability();
        jumpBoostEffect.updateEffectAvailability();
        healthBoostEffect.updateEffectAvailability();
        hasteEffect.updateEffectAvailability();

        updatePotionEffects();
    }

    @Override
    public void onLivingJumpEvent(LivingEvent.LivingJumpEvent event)
    {
//        if (!player.isArmorElementPutOn(getLeggingsName())) return;

//        player.setEffect(MobEffects.JUMP, 30, 1);
    }

    @Override
    public void onLivingFallEvent(LivingFallEvent event)
    {
        if (jumpBoostEffect.isEffectOn() && jumpBoostEffect.isActive())
        {
            event.setDamageMultiplier(0);
        }
        else if (player.isArmorElementPutOn(this, EquipmentSlot.FEET))
        {
            event.setDamageMultiplier(0.3f);
        }
    }

    @Override
    public void onCreeperCheck()
    {
        boolean isFullArmorSet = player.isFullArmorSetPutOn(this);
        if (!isFullArmorSet) return;

        Player entityPlayer = player.getEntity();
        var level = entityPlayer.level;
        if (level.isClientSide()) return;

        var monsters = level.getEntities((Entity)null, entityPlayer.getBoundingBox().inflate(CREEPER_NOTIFY_DISTANCE),
                entity -> entity instanceof Creeper);

        for (Entity monster : monsters)
        {
            var entity = (LivingEntity) monster;
            if (!entity.hasEffect(MobEffects.GLOWING))
            {
                var potionEffect = new MobEffectInstance(MobEffects.GLOWING, CREEPER_GLOWING_EFFECT_DURATION, 0 /*amplifier*/, false /*ambient*/, false /*visible*/, false /*showIcon*/);
                entity.addEffect(potionEffect);
            }

            if (++creeperDetectedCounter > 3)
            {
                creeperDetectedPacket.sendToPlayer((ServerPlayer) entityPlayer);
                creeperDetectedCounter = 0;
            }

            return;
        }
    }

    @Override
    public void onKeyInputEvent(String pressedKeyDescription)
    {
        if (pressedKeyDescription.equals(KeyRegistry.NIGHT_VISION.getName()))
        {
            nightVisionEffect.trySwitch();
        }

        if (pressedKeyDescription.equals(KeyRegistry.JUMP_BOOST.getName()))
        {
            jumpBoostEffect.trySwitch();
        }
    }

    @Override
    public void onEntityTravelToDimensionEvent(EntityTravelToDimensionEvent event)
    {
        // none
    }

    @Override
    public void onPlayerChangedDimensionEvent(PlayerEvent.PlayerChangedDimensionEvent event)
    {
        // none
    }

    @Override
    public void onBreakSpeed(net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed event)
    {
        if (jumpBoostEffect.isEffectOn())
        {
            event.setNewSpeed(event.getOriginalSpeed() * 0.2f);
        }
    }

    @Override
    public void onBeingInWater()
    {
        // none
    }

    @Override
    public void onBeingUnderRain()
    {
        // none
    }

    @Override
    public String getModelName()
    {
        return ArmorRegistry.DIAMITHIC_BOOTS.get().getArmorModelName();
    }

    private void updatePotionEffects()
    {
        nightVisionEffect.updateEffectActivity();
        strengthEffect.updateEffectActivity();
        jumpBoostEffect.updateEffectActivity();
        healthBoostEffect.updateEffectActivity();
        hasteEffect.updateEffectActivity();
    }
}
