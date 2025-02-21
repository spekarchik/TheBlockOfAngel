package com.pekar.angelblock.events.armor;

import com.pekar.angelblock.armor.ArmorRegistry;
import com.pekar.angelblock.events.effect.*;
import com.pekar.angelblock.events.player.IPlayer;
import com.pekar.angelblock.keybinds.KeyRegistry;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.neoforged.neoforge.event.entity.EntityTravelToDimensionEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class LapisArmor extends Armor
{
    private final IArmorEffect nightVisionEffect;
    private final IArmorEffect glowingEffect;
    private final IArmorEffect waterBreathingEffect;
    private final IArmorEffect hasteEffect;
    private final IArmorEffect luckEffect;
    private final IArmorEffect regenerationEffect;
    private final IArmorEffect blindnessEffect;
    private final IArmorEffect witherEffect;
    private final IArmorEffect strengthEffect;
    private final IArmorEffect dolphinsGrace;

    private static final int REGENERATION_EFFECT_DURATION = 300;
    private static final int REGENERATION_NEGATIVE_EFFECT_DURATION = 200;

    public LapisArmor(IPlayer player)
    {
        super(player);
        nightVisionEffect = new NightVisionArmorEffect(player, this);
        glowingEffect = new GlowingArmorEffect(player, this);
        waterBreathingEffect = new WaterBreathingEffect(player, this);
        hasteEffect = new HasteArmorEffect(player, this);
        luckEffect = new LuckArmorEffect(player, this);
        regenerationEffect = new RegenerationArmorEffect(player, this, 0, REGENERATION_EFFECT_DURATION);
        blindnessEffect = new BlindnessArmorEffect(player, this, REGENERATION_NEGATIVE_EFFECT_DURATION).showIcon();
        witherEffect = new WitherEffect(player, this, 0, 600).showIcon();
        strengthEffect = new StrengthArmorEffect(player, this, 0).availableOnChestPlateWithStrengthBooster();
        dolphinsGrace = new DolphinsGraceEffect(player, this);
    }

    @Override
    public void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event)
    {
        nightVisionEffect.updateSwitchState();
        glowingEffect.updateSwitchState();
        dolphinsGrace.updateSwitchState();
    }

    @Override
    public void onLivingHurtEvent(LivingIncomingDamageEvent event)
    {
        var damageSource = event.getSource();

        if (isFireOrMagmaDamage(damageSource) && !witherEffect.isActive())
        {
            witherEffect.trySwitch();
        }
        else if (isLavaDamage(damageSource))
        {
            event.setAmount(event.getAmount() * 1.2f);
        }
        else if (isFreezeDamage(damageSource))
        {
            event.setAmount(event.getAmount() * 2f);
        }
    }

    @Override
    public void onLivingEquipmentChangeEvent(LivingEquipmentChangeEvent event)
    {
        nightVisionEffect.updateEffectAvailability();
        hasteEffect.updateEffectAvailability();
        waterBreathingEffect.updateEffectAvailability();
        luckEffect.updateEffectAvailability();
        glowingEffect.updateEffectAvailability();
        regenerationEffect.updateEffectAvailability();
        blindnessEffect.updateEffectAvailability();
        witherEffect.updateEffectAvailability();
        strengthEffect.updateEffectAvailability();
        dolphinsGrace.updateEffectAvailability();

        updatePotionEffects();
    }

    @Override
    public void onLivingJumpEvent(LivingEvent.LivingJumpEvent event)
    {
        // none
    }

    @Override
    public void onLivingFallEvent(LivingFallEvent event)
    {
        // none
    }

    @Override
    public void onCreeperCheck()
    {
        // none
    }

    @Override
    public void onEffectAddedEvent(MobEffectEvent.Added event)
    {
    }

    @Override
    public void onKeyInputEvent(String pressedKeyDescription)
    {
        if (pressedKeyDescription.equals(KeyRegistry.NIGHT_VISION.getName()))
        {
            nightVisionEffect.trySwitch();
        }

        if (pressedKeyDescription.equals(KeyRegistry.GLOWING.getName()))
        {
            glowingEffect.trySwitch();
        }

        if (pressedKeyDescription.equals(KeyRegistry.JUMP_BOOST.getName()))
        {
            dolphinsGrace.trySwitch();
        }

        if (pressedKeyDescription.equals(KeyRegistry.REGENERATION.getName()))
        {
            if (regenerationEffect.isEffectAvailable() && player.getEntity().getHealth() < player.getEntity().getMaxHealth())
            {
                blindnessEffect.trySwitch();

                if (!regenerationEffect.isActive())
                {
                    regenerationEffect.trySwitch();
                }
            }
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
    public void onBreakSpeed(PlayerEvent.BreakSpeed event)
    {
        // none
    }

    @Override
    public void onBeingInLava()
    {
        // none
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
    public String getFamilyName()
    {
        return ArmorRegistry.LAPIS_BOOTS.get().getArmorFamilyName();
    }

    @Override
    public int getPriority()
    {
        return 5;
    }

    private void updatePotionEffects()
    {
        nightVisionEffect.updateEffectActivity();
        hasteEffect.updateEffectActivity();
        waterBreathingEffect.updateEffectActivity();
        luckEffect.updateEffectActivity();
        glowingEffect.updateEffectActivity();
        regenerationEffect.updateEffectActivity();
        blindnessEffect.updateEffectActivity();
        strengthEffect.updateEffectActivity();
        dolphinsGrace.updateEffectActivity();
    }

    private boolean isFireOrMagmaDamage(DamageSource damageSource)
    {
        boolean isDamagedByInFire = damageSource.is(DamageTypes.IN_FIRE);
        boolean isDamagedByOnFire = damageSource.is(DamageTypes.ON_FIRE);
        boolean isDamagedByMagma = damageSource.is(DamageTypes.HOT_FLOOR);
        return isDamagedByInFire || isDamagedByOnFire || isDamagedByMagma;
    }
}
