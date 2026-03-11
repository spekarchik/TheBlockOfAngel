package com.pekar.angelblock.events.armor;

import com.pekar.angelblock.Main;
import com.pekar.angelblock.armor.PlayerArmorType;
import com.pekar.angelblock.events.effect.*;
import com.pekar.angelblock.events.effect.base.IPermanentArmorEffect;
import com.pekar.angelblock.events.effect.base.ISwitchingArmorEffect;
import com.pekar.angelblock.events.effect.base.ITemporaryArmorEffect;
import com.pekar.angelblock.events.effect.base.ITemporaryPersistentArmorEffect;
import com.pekar.angelblock.events.player.IPlayer;
import com.pekar.angelblock.keybinds.KeyRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.neoforged.neoforge.event.entity.EntityTravelToDimensionEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import static com.pekar.angelblock.utils.Resources.createResourceLocation;

public class AquariteArmorController extends PlayerArmor
{
    private final IPermanentArmorEffect waterBreathingEffect;
    private final IPermanentArmorEffect hasteEffect;
    private final IPermanentArmorEffect luckEffect;
    private final IPermanentArmorEffect strengthEffect;
    private final ITemporaryArmorEffect regenerationEffect;
    private final ITemporaryPersistentArmorEffect blindnessEffect;
    private final ITemporaryPersistentArmorEffect witherEffect;
    private final ISwitchingArmorEffect dolphinsGrace;
    private final ISwitchingArmorEffect nightVisionEffect;
    private final ISwitchingArmorEffect glowingEffect;

    private static final int REGENERATION_EFFECT_DURATION = 300;
    private static final int REGENERATION_NEGATIVE_EFFECT_DURATION = 200;

    public AquariteArmorController(IPlayer player)
    {
        super(player, PlayerArmorType.AQUARITE);

        nightVisionEffect = new NightVisionSwitchingArmorEffect(player, this);
        nightVisionEffect.setup().availableOnHelmetWithNightVision();
        glowingEffect = new GlowingSwitchingArmorEffect(player, this);
        glowingEffect.setup().availableIfSlotSet(EquipmentSlot.CHEST);
        waterBreathingEffect = new WaterBreathingPermanentEffect(player, this);
        hasteEffect = new HastePermanentArmorEffect(player, this);
        luckEffect = new LuckPermanentArmorEffect(player, this);
        luckEffect.setup().availableIfSlotSet(EquipmentSlot.CHEST);
        regenerationEffect = new RegenerationTemporaryArmorEffect(player, this, 0, REGENERATION_EFFECT_DURATION);
        blindnessEffect = new BlindnessNegativeArmorEffect(player, this, REGENERATION_NEGATIVE_EFFECT_DURATION);
        blindnessEffect.setup().showIcon();
        witherEffect = new WitherNegativeEffect(player, this, 0, 600);
        witherEffect.setup().showIcon();
        strengthEffect = new StrengthPermanentArmorEffect(player, this, 0);
        strengthEffect.setup().availableOnChestPlateWithStrengthBooster();
        dolphinsGrace = new DolphinsGraceSwitchingEffect(player, this);
    }

    @Override
    protected void updateAvailability()
    {
        nightVisionEffect.updateAvailability();
        hasteEffect.updateAvailability();
        waterBreathingEffect.updateAvailability();
        luckEffect.updateAvailability();
        glowingEffect.updateAvailability();
        regenerationEffect.updateAvailability();
        blindnessEffect.updateAvailability();
        witherEffect.updateAvailability();
        strengthEffect.updateAvailability();
        dolphinsGrace.updateAvailability();
    }

    @Override
    protected void updateEffectStates()
    {
        nightVisionEffect.updateSwitchState();
        glowingEffect.updateSwitchState();
        dolphinsGrace.updateSwitchState();
    }

    @Override
    protected void updateActivityForHeadSlot()
    {
        nightVisionEffect.updateActivity();
        waterBreathingEffect.updateActivity();
    }

    @Override
    protected void updateActivityForFeetSlot()
    {
        dolphinsGrace.updateActivity();
    }

    @Override
    protected void updateActivityForLegsSlot()
    {
        regenerationEffect.updateActivity();
    }

    @Override
    protected void updateActivityForChestSlot()
    {
        hasteEffect.updateActivity();
        luckEffect.updateActivity();
        glowingEffect.updateActivity();
        strengthEffect.updateActivity();
    }

    @Override
    protected void updateActivity(EquipmentSlot slot)
    {
        blindnessEffect.updateActivity();
    }

    @Override
    public void onLivingHurtEvent(LivingIncomingDamageEvent event)
    {
    }

    @Override
    public void onLivingDamageEvent(LivingDamageEvent.Pre event)
    {
        var damageSource = event.getSource();

        if (isFireOrMagmaDamage(damageSource) && !witherEffect.isActive())
        {
            witherEffect.tryActivate();
        }

        if (isVulnerable(damageSource))
        {
            event.setNewDamage(event.getNewDamage() * 1.2f);
        }
        else if (isFreezeDamage(damageSource))
        {
            event.setNewDamage(event.getNewDamage() * 2f);
        }
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
    public void onHeavyTick()
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
            if (regenerationEffect.isAvailable() && !regenerationEffect.isAnyActive() && player.getPlayerEntity().getHealth() < player.getPlayerEntity().getMaxHealth())
            {
                blindnessEffect.tryActivate();
                regenerationEffect.tryActivate();
            }
        }

        // FOR TESTS
//        if (pressedKeyDescription.equals(KeyRegistry.GLOWING.getName()))
//        {
//            switchArmorDamage();
//        }
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
    public int getPriority()
    {
        return 5;
    }

    private boolean isVulnerable(DamageSource damageSource)
    {
        var vulnerabilities = TagKey.create(Registries.DAMAGE_TYPE, createResourceLocation(Main.MODID, "lapis_armor_vulnerabilities"));
        return damageSource.is(vulnerabilities);
    }

    private boolean isFireOrMagmaDamage(DamageSource damageSource)
    {
        boolean isDamagedByInFire = damageSource.is(DamageTypes.IN_FIRE);
        boolean isDamagedByOnFire = damageSource.is(DamageTypes.ON_FIRE);
        boolean isDamagedByMagma = damageSource.is(DamageTypes.HOT_FLOOR);
        return isDamagedByInFire || isDamagedByOnFire || isDamagedByMagma;
    }
}
