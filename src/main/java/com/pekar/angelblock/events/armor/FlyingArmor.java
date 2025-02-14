package com.pekar.angelblock.events.armor;

import com.pekar.angelblock.armor.ArmorRegistry;
import com.pekar.angelblock.events.effect.*;
import com.pekar.angelblock.events.player.IPlayer;
import com.pekar.angelblock.keybinds.KeyRegistry;
import net.minecraft.world.entity.EquipmentSlot;
import net.neoforged.neoforge.event.entity.EntityTravelToDimensionEvent;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class FlyingArmor extends Armor
{
    private final SwitchingEffectSynchronizer jumpBoostEffect;
    private final IArmorEffect slowFallingSwitchingEffect;
    private static final int JUMP_BOOST_AMPLIFIER = 24;

    public FlyingArmor(IPlayer player)
    {
        super(player);

        slowFallingSwitchingEffect = new SlowFallingSwitchingEffect(player, this).setupAvailability(this::isLevitationSwitchingEffectAvailable);

        var speedEffect = new SpeedSwitchingEffect(player, this, 1);
        speedEffect.setupAvailability(this::isJumpEffectAvailable);
        var jumpBoostEffect = new JumpBoostArmorEffect(player, this, JUMP_BOOST_AMPLIFIER);
        jumpBoostEffect.setupAvailability(this::isJumpEffectAvailable).hideIcon();
        var superJumpEffect = new SuperJumpSwitchingEffect(player, this);
        superJumpEffect.setupAvailability(this::isJumpEffectAvailable);
        this.jumpBoostEffect = new SwitchingEffectSynchronizer(jumpBoostEffect);
        this.jumpBoostEffect.addDependentEffect(speedEffect);
        this.jumpBoostEffect.addDependentEffect(superJumpEffect);
    }

    @Override
    public String getModelName()
    {
        return ArmorRegistry.FLYING_BOOTS.get().getMaterialName();
    }

    @Override
    public int getPriority()
    {
        return 1;
    }

    @Override
    public void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event)
    {
        jumpBoostEffect.updateSwitchState();

        if (!player.isNether())
        {
            slowFallingSwitchingEffect.updateSwitchState();
        }
    }

    @Override
    public void onLivingHurtEvent(LivingIncomingDamageEvent event)
    {
        if (isFreezeDamage(event.getSource()))
        {
            boolean areBootsWorn = player.isArmorElementPutOn(this, EquipmentSlot.FEET);
            event.setCanceled(areBootsWorn);
        }
    }

    @Override
    public void onLivingEquipmentChangeEvent(LivingEquipmentChangeEvent event)
    {
        jumpBoostEffect.updateEffectAvailability();
        slowFallingSwitchingEffect.updateEffectAvailability();

        slowFallingSwitchingEffect.updateSwitchState();
        updatePotionEffects();
    }

    @Override
    public void onLivingJumpEvent(LivingEvent.LivingJumpEvent event)
    {
    }

    @Override
    public void onLivingFallEvent(LivingFallEvent event)
    {
        if (player.isArmorElementPutOn(this, EquipmentSlot.FEET))
        {
            event.setDamageMultiplier(0.1F);
        }
    }

    @Override
    public void onKeyInputEvent(String pressedKeyDescription)
    {
        if (pressedKeyDescription.equals(KeyRegistry.SUPER_JUMP.getName()))
        {
            if (!player.isNether())
            {
                jumpBoostEffect.trySwitch();
            }
        }

        if (pressedKeyDescription.equals(KeyRegistry.LEVITATION.getName()))
        {
            if (!player.isNether())
            {
                slowFallingSwitchingEffect.trySwitch();

                if (slowFallingSwitchingEffect.isActive())
                    player.getEntity().stopFallFlying();
            }
        }
    }

    @Override
    public void onEntityTravelToDimensionEvent(EntityTravelToDimensionEvent event)
    {
    }

    @Override
    public void onPlayerChangedDimensionEvent(PlayerEvent.PlayerChangedDimensionEvent event)
    {
        jumpBoostEffect.updateEffectAvailability();
        slowFallingSwitchingEffect.updateEffectAvailability();

        updatePotionEffects();
    }

    @Override
    public void onBreakSpeed(PlayerEvent.BreakSpeed event)
    {
        if (player.getEntity().isInWaterRainOrBubble() || jumpBoostEffect.isEffectOn())
        {
            event.setNewSpeed(event.getOriginalSpeed() * 0.2f);
        }
    }

    @Override
    public void onBeingInWater()
    {
    }

    @Override
    public void onBeingUnderRain()
    {
    }

    @Override
    public void onCreeperCheck()
    {
    }

    private void updatePotionEffects()
    {
        jumpBoostEffect.updateEffectActivity();
        slowFallingSwitchingEffect.updateEffectActivity();
    }

    private boolean isLevitationSwitchingEffectAvailable(IPlayer player, IArmor armor)
    {
        return !player.isNether() && player.isFullArmorSetPutOn(this);
    }

    private boolean isJumpEffectAvailable(IPlayer player, IArmor armor)
    {
        var boots = player.getEntity().getItemBySlot(EquipmentSlot.FEET);
        var leggings = player.getEntity().getItemBySlot(EquipmentSlot.LEGS);

        int bootsDamage = boots.getDamageValue();
        int leggingsDamage = leggings.getDamageValue();
        int maxBootsDamageToJump = boots.getMaxDamage() / 2;
        int maxLeggingsDamageToJump = leggings.getMaxDamage() / 2;

        return !player.isNether() && player.isFullArmorSetPutOn(this)
                && bootsDamage < maxBootsDamageToJump && leggingsDamage < maxLeggingsDamageToJump;
    }
}
