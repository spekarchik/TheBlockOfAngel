package com.pekar.angelblock.events;

import com.pekar.angelblock.items.ItemRegistry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.Map;
import java.util.WeakHashMap;

/** Handles the timed part of a nitwit reading an ancient scroll. */
public class VillagerAncientScrollEvents implements IEventHandler
{
    public static final int READING_TIME_TICKS = 100;

    private static final int ENCHANT_PARTICLE_INTERVAL = 4;
    private static final int HAPPY_PARTICLE_INTERVAL = 20;
    private static final Map<Villager, ReadingState> READING_STATES = new WeakHashMap<>();

    public static boolean canStartReading(Villager villager)
    {
        return !villager.isBaby()
                && villager.isAlive()
                && villager.getVillagerData().profession().is(VillagerProfession.NITWIT)
                && !READING_STATES.containsKey(villager)
                && villager.getMainHandItem().isEmpty();
    }

    /** Claims the first scroll immediately, before vanilla can process another item entity. */
    public static void beginReading(Villager villager, ItemStack scroll)
    {
        scroll.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);
        READING_STATES.put(villager, new ReadingState(READING_TIME_TICKS, scroll));
        villager.setCanPickUpLoot(false);
    }

    @SubscribeEvent
    public void beforeVillagerTick(EntityTickEvent.Pre event)
    {
        if (!(event.getEntity() instanceof Villager villager)) return;
        if (!(villager.level() instanceof ServerLevel)) return;

        // Villagers normally enable this in their constructor, but old/summoned villagers can
        // have CanPickUpLoot=false persisted in their entity data. Vanilla checks this flag
        // before its nearest-item sensor and before wantsToPickUp is ever called.
        if (canStartReading(villager) && !villager.canPickUpLoot())
        {
            villager.setCanPickUpLoot(true);
        }

        ReadingState readingState = READING_STATES.get(villager);
        if (readingState == null && !isScrollInHand(villager)) return;

        // Vanilla can clear its temporary hand display later in this tick. The reading
        // state, rather than the hand slot, is authoritative, so block every normal item
        // pickup until this reading attempt finishes or is cancelled.
        villager.setCanPickUpLoot(false);

        if (readingState != null && !isScrollInHand(villager))
        {
            if (villager.getMainHandItem().isEmpty())
            {
                // Some vanilla villager behaviors use the main hand as a temporary display
                // slot. Restore our owned scroll if one of them clears that slot.
                villager.setItemSlot(EquipmentSlot.MAINHAND, readingState.scroll());
            }
            else
            {
                cancelReading((ServerLevel)villager.level(), villager);
                return;
            }
        }

        // The ordinary villager brain continues to run, but cannot walk away while reading.
        villager.getNavigation().stop();
        villager.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);

        Vec3 movement = villager.getDeltaMovement();
        villager.setDeltaMovement(0.0D, movement.y, 0.0D);
    }

    @SubscribeEvent
    public void afterVillagerTick(EntityTickEvent.Post event)
    {
        if (!(event.getEntity() instanceof Villager villager)) return;
        if (!(villager.level() instanceof ServerLevel serverLevel)) return;

        ReadingState readingState = READING_STATES.get(villager);
        if (readingState == null)
        {
            if (!isScrollInHand(villager)
                    || !villager.getVillagerData().profession().is(VillagerProfession.NITWIT)) return;

            ItemStack scroll = villager.getMainHandItem();
            beginReading(villager, scroll);
            readingState = READING_STATES.get(villager);
        }

        int ticksRemaining = readingState.ticksRemaining();

        spawnReadingParticles(serverLevel, villager, ticksRemaining);

        if (ticksRemaining <= 1)
        {
            READING_STATES.remove(villager);
            finishReading(serverLevel, villager, readingState.scroll());
        }
        else
        {
            READING_STATES.put(villager, readingState.withTicksRemaining(ticksRemaining - 1));
        }
    }

    @SubscribeEvent
    public void onVillagerHurt(LivingDamageEvent.Post event)
    {
        if (event.getNewDamage() <= 0.0F) return;
        if (!(event.getEntity() instanceof Villager villager)) return;
        if (!(villager.level() instanceof ServerLevel serverLevel)) return;

        cancelReading(serverLevel, villager);
    }

    @SubscribeEvent
    public void onVillagerDeath(LivingDeathEvent event)
    {
        if (!(event.getEntity() instanceof Villager villager)) return;
        if (!(villager.level() instanceof ServerLevel serverLevel)) return;

        cancelReading(serverLevel, villager);
    }

    private static boolean isScrollInHand(Villager villager)
    {
        return villager.getMainHandItem().is(ItemRegistry.ANCIENT_SCROLL.get());
    }

    private static void finishReading(ServerLevel level, Villager villager, ItemStack ownedScroll)
    {
        ItemStack scroll = takeScrollFromHand(villager, ownedScroll);

        villager.setVillagerData(villager.getVillagerData().withProfession(level.registryAccess(), VillagerProfession.NONE));
        villager.refreshBrain(level);
        villager.setCanPickUpLoot(true);

        throwScroll(villager, scroll);
    }

    private static void cancelReading(ServerLevel level, Villager villager)
    {
        ReadingState readingState = READING_STATES.remove(villager);
        if (readingState == null && !isScrollInHand(villager)) return;

        ItemStack ownedScroll = readingState == null ? villager.getMainHandItem() : readingState.scroll();
        villager.setCanPickUpLoot(true);
        throwScroll(villager, takeScrollFromHand(villager, ownedScroll));
    }

    private static ItemStack takeScrollFromHand(Villager villager, ItemStack ownedScroll)
    {
        ItemStack scroll = isScrollInHand(villager) ? villager.getMainHandItem() : ownedScroll;
        scroll.remove(DataComponents.ENCHANTMENT_GLINT_OVERRIDE);
        if (isScrollInHand(villager))
        {
            villager.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        }
        return scroll;
    }

    private static void throwScroll(Villager villager, ItemStack scroll)
    {
        if (scroll.isEmpty()) return;

        Vec3 target = villager.position()
                .add(villager.getLookAngle().multiply(2.0D, 0.0D, 2.0D))
                .add(0.0D, 1.0D, 0.0D);
        BehaviorUtils.throwItem(villager, scroll, target);
    }

    private static void spawnReadingParticles(ServerLevel level, Villager villager, int ticksRemaining)
    {
        if (ticksRemaining % HAPPY_PARTICLE_INTERVAL == 0)
        {
            level.sendParticles(
                    ParticleTypes.HAPPY_VILLAGER,
                    villager.getX(), villager.getY() + villager.getBbHeight() + 0.25D, villager.getZ(),
                    2,
                    0.2D, 0.1D, 0.2D,
                    0.0D
            );
        }

        if (ticksRemaining % ENCHANT_PARTICLE_INTERVAL == 0)
        {
            Vec3 scrollPosition = villager.getEyePosition()
                    .add(villager.getLookAngle().scale(0.35D))
                    .add(0.0D, -0.45D, 0.0D);
            level.sendParticles(
                    ParticleTypes.ENCHANT,
                    scrollPosition.x, scrollPosition.y, scrollPosition.z,
                    3,
                    0.18D, 0.18D, 0.18D,
                    0.05D
            );
        }
    }

    private record ReadingState(int ticksRemaining, ItemStack scroll)
    {
        private ReadingState withTicksRemaining(int ticks)
        {
            return new ReadingState(ticks, scroll);
        }
    }
}
