package com.pekar.angelblock.events.armor;

import com.pekar.angelblock.armor.ArmorRegistry;
import com.pekar.angelblock.events.effect.*;
import com.pekar.angelblock.events.player.IPlayer;
import com.pekar.angelblock.keybinds.KeyRegistry;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;

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

    public LapisArmor(IPlayer player)
    {
        super(player);
        nightVisionEffect = new NightVisionArmorEffect(player, this);
        glowingEffect = new GlowingArmorEffect(player, this);
        waterBreathingEffect = new WaterBreathingEffect(player, this);
        hasteEffect = new HasteArmorEffect(player, this);
        luckEffect = new LuckArmorEffect(player, this);
        regenerationEffect = new RegenerationArmorEffect(player, this, 1, 100);
        blindnessEffect = new BlindnessArmorEffect(player, this, 140);
        witherEffect = new WitherEffect(player, this, 0, 600);
        strengthEffect = new StrengthArmorEffect(player, this, 0);
        dolphinsGrace = new DolphinsGraceEffect(player, this);
    }

    @Override
    public void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event)
    {
        nightVisionEffect.updateSwitchState();
        glowingEffect.updateSwitchState();
    }

    @Override
    public void onLivingHurtEvent(LivingHurtEvent event)
    {
        if (isFireOrMagmaDamage(event.getSource()) && !witherEffect.isActive())
        {
            witherEffect.trySwitch();
        }
        else if (isLavaDamage(event.getSource()))
        {
            float amount = event.getAmount();
            event.setAmount(amount * 1.2f);
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

        if (pressedKeyDescription.equals(KeyRegistry.REGENERATION.getName()))
        {
            if (regenerationEffect.isEffectAvailable())
            {
                regenerationEffect.trySwitch();
                blindnessEffect.trySwitch();
            }
        }
    }

    @Override
    public void onEntityTravelToDimensionEvent(EntityTravelToDimensionEvent event)
    {
        // none
    }

    @Override
    public void onBreakSpeed(net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed event)
    {
        // none
    }

    @Override
    public void onBeingInWater()
    {
        // none
    }

    @Override
    public String getHelmetName()
    {
        return ArmorRegistry.LAPIS_HELMET.get().getRegistryName().getPath();
    }

    @Override
    public String getChestPlateName()
    {
        return ArmorRegistry.LAPIS_CHESTPLATE.get().getRegistryName().getPath();
    }

    @Override
    public String getLeggingsName()
    {
        return ArmorRegistry.LAPIS_LEGGINGS.get().getRegistryName().getPath();
    }

    @Override
    public String getBootsName()
    {
        return ArmorRegistry.LAPIS_BOOTS.get().getRegistryName().getPath();
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
        boolean isDamagedByInFire = damageSource == DamageSource.IN_FIRE;
        boolean isDamagedByOnFire = damageSource == DamageSource.ON_FIRE;
        boolean isDamagedByMagma = damageSource == DamageSource.HOT_FLOOR;
        return isDamagedByInFire || isDamagedByOnFire || isDamagedByMagma;
    }

    private boolean isLavaDamage(DamageSource damageSource)
    {
        return damageSource == DamageSource.LAVA;
    }
}
