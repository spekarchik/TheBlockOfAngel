package com.pekar.angelblock.events;

import com.pekar.angelblock.events.animal.IAnimal;
import com.pekar.angelblock.events.armor.IAnimalArmorEvents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.nautilus.AbstractNautilus;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityTravelToDimensionEvent;
import net.neoforged.neoforge.event.entity.living.*;

public class AnimalEvents implements IEventHandler
{
    private final IAnimalManager animalManager = AnimalManager.instance();

    @SubscribeEvent
    public void onEntityTravelToDimensionEvent(EntityTravelToDimensionEvent event)
    {
        var entity = event.getEntity();
        IAnimal animal = animalManager.getAnimalByUUID(entity.getUUID());
        if (animal == null) return;

        for (IAnimalArmorEvents armor : animal.getArmorTypesUsed())
        {
            armor.onEntityTravelToDimensionEvent(event);
        }
    }

    @SubscribeEvent
    public void onLivingHurtEvent(LivingIncomingDamageEvent event)
    {
        var entity = event.getEntity();
        if (entity instanceof Mob mob)
        {
            var damageSource = event.getSource();
            var directEntity = damageSource.getDirectEntity();
            if (damageSource.is(DamageTypes.PLAYER_EXPLOSION) && (directEntity == null || directEntity instanceof Player))
            {
                var attacker = damageSource.getEntity();
                if (attacker instanceof Player player && mob.getControllingPassenger() instanceof Player passenger && player.getUUID().equals(passenger.getUUID()))
                {
                    event.setCanceled(true);
                }
            }

            if (damageSource.is(DamageTypes.HOT_FLOOR)
                    && entity instanceof AbstractNautilus nautilus
                    && nautilus.getBodyArmorItem().is(Items.NETHERITE_NAUTILUS_ARMOR))
            {
                event.setCanceled(true);

                if (entity.level() instanceof ServerLevel serverLevel)
                {
                    var managedNautilus = AnimalManager.instance().getAnimalByUUID(nautilus.getUUID());
                    if (managedNautilus == null)
                    {
                        AnimalManager.instance().addAnimal(nautilus);
                        managedNautilus = AnimalManager.instance().getAnimalByUUID(nautilus.getUUID());
                    }

                    if (managedNautilus != null)
                    {
                        if (managedNautilus.every(20))
                        {
                            var pos = nautilus.blockPosition();
                            serverLevel.sendParticles(
                                    ParticleTypes.END_ROD,
                                    pos.getX(), pos.getY(), pos.getZ(),
                                    10,
                                    0.5, 0.5, 0.5,
                                    0.1
                            );

                            serverLevel.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
                        }
                    }
                }
            }
        }

        var animal = animalManager.getAnimalByUUID(entity.getUUID());
        if (animal == null) return;

        for (var armor : animal.getArmorTypesUsed())
        {
            armor.onLivingHurtEvent(event);
        }
    }

    @SubscribeEvent
    public void onLivingDamageEvent(LivingDamageEvent.Pre event)
    {
        var entity = event.getEntity();
        IAnimal animal = animalManager.getAnimalByUUID(entity.getUUID());
        if (animal == null) return;

        for (var armor : animal.getArmorTypesUsed())
        {
            armor.onLivingDamageEvent(event);
        }
    }

    @SubscribeEvent
    public void onArmorHurtEvent(ArmorHurtEvent event)
    {
        var entity = event.getEntity();
        var animal = animalManager.getAnimalByUUID(entity.getUUID());
        if (animal == null) return;

        for (var armor : animal.getArmorTypesUsed())
        {
            armor.onArmorHurtEvent(event);
        }
    }

    @SubscribeEvent
    public void onEffectAdded(MobEffectEvent.Added event)
    {
        var entity = event.getEntity();
        var animal = animalManager.getAnimalByUUID(entity.getUUID());
        if (animal == null) return;

        for (var armor : animal.getArmorTypesUsed())
        {
            armor.onEffectAddedEvent(event);
        }
    }

    @SubscribeEvent
    public void onLivingJumpEvent(LivingEvent.LivingJumpEvent event)
    {
        var animal = animalManager.getAnimalByUUID(event.getEntity().getUUID());
        if (animal == null) return;

        for (var armor : animal.getArmorTypesUsed())
        {
            armor.onLivingJumpEvent(event);
        }
    }

    @SubscribeEvent
    public void onLivingFallEvent(LivingFallEvent event)
    {
        var animal = animalManager.getAnimalByUUID(event.getEntity().getUUID());
        if (animal == null) return;

        for (var armor : animal.getArmorTypesUsed())
        {
            armor.onLivingFallEvent(event);
        }
    }
}
