package com.pekar.angelblock.blocks.tile_entities;

import com.pekar.angelblock.blocks.BlockRegistry;
import com.pekar.angelblock.blocks.tile_entities.monsters.IMonster;
import com.pekar.angelblock.blocks.tile_entities.monsters.Monsters;
import com.pekar.angelblock.events.ILivingDeathEventHandler;
import com.pekar.angelblock.events.PlayerInteractionEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DevilBlockEntity extends BlockEntity implements ILivingDeathEventHandler, BlockEntityTicker<DevilBlockEntity>
{
    private static final int EFFECTIVE_RADIUS = 70;
    private static final int SQR_EFFECTIVE_RADIUS = EFFECTIVE_RADIUS * EFFECTIVE_RADIUS;
    private static final int MONSTER_SPAWN_RADIUS = 30;
    private static final int CORRUPTION_RADIUS = 18;
    private static final int SQR_CORRUPTION_RADIUS = CORRUPTION_RADIUS * CORRUPTION_RADIUS;
    private static final int AFFECTED_HORIZONTAL_RADIUS = 1;
    private static final double AFFECT_VILLAGERS_RADIUS = 1.5;
    private static final double SQR_AFFECT_VILLAGERS_RADIUS = AFFECT_VILLAGERS_RADIUS * AFFECT_VILLAGERS_RADIUS;
    private static final Random random = new Random();

    private final Map<Item, IMonster> monstersByActionItem = new HashMap<>();

    public DevilBlockEntity(BlockPos blockPos, BlockState blockState)
    {
        super(EntityRegistry.DEVIL_BLOCK_ENTITY.get(), blockPos, blockState);
        activate();

        addToMonsterMap(Monsters.Skeleton);
        addToMonsterMap(Monsters.Zombie);
        addToMonsterMap(Monsters.Creeper);
        addToMonsterMap(Monsters.Enderman);
        addToMonsterMap(Monsters.Witch);
        addToMonsterMap(Monsters.Spider);
        addToMonsterMap(Monsters.Slime);
        addToMonsterMap(Monsters.MagmaCube);
        addToMonsterMap(Monsters.Guardian);
        addToMonsterMap(Monsters.Blaze);
        addToMonsterMap(Monsters.Ghast);
        addToMonsterMap(Monsters.Hoglin);
        addToMonsterMap(Monsters.PiglinBrute);
        addToMonsterMap(Monsters.WitherSkeleton);
        addToMonsterMap(Monsters.ZombieVillager);
        addToMonsterMap(Monsters.CaveSpider);
        addToMonsterMap(Monsters.Phantom);
        addToMonsterMap(Monsters.Shulker);
        addToMonsterMap(Monsters.Vindicator);
        addToMonsterMap(Monsters.Evoker);
        addToMonsterMap(Monsters.Ravager);
        addToMonsterMap(Monsters.Piglin);
        addToMonsterMap(Monsters.Husk);
        addToMonsterMap(Monsters.Pillager);
        addToMonsterMap(Monsters.Breeze);
        addToMonsterMap(Monsters.Creaking);
        addToMonsterMap(Monsters.Illusioner);
    }

    public void activate()
    {
//        PlayerManager.instance().sendMessage("activate");
        PlayerInteractionEvents.subscribeLivingDeath(this);
    }

    public void dispose()
    {
//        PlayerManager.instance().sendMessage("dispose");
        PlayerInteractionEvents.unsubscribeLivingDeath(this);
    }

    public boolean spawnMonster(Item item, Player player, ItemStack interactionHandItemStack)
    {
        if (!monstersByActionItem.containsKey(item) || level == null)
            return false;

        for (int i = 0; i < 5; i++)
        {
            int shiftX = level.random.nextIntBetweenInclusive(-MONSTER_SPAWN_RADIUS, MONSTER_SPAWN_RADIUS);
            int shiftZ = level.random.nextIntBetweenInclusive(-MONSTER_SPAWN_RADIUS, MONSTER_SPAWN_RADIUS);

            var startPos = getBlockPos().offset(shiftX, 0, shiftZ);

            BlockPos pos = null;
            final int minY = getBlockPos().getY() - MONSTER_SPAWN_RADIUS;
            var monster = monstersByActionItem.get(item);

            for (var p = startPos.above(MONSTER_SPAWN_RADIUS); p.getY() > minY; p = p.below())
            {
                if (!monster.getSpawnStrategy().canSpawnAtPos(level, p, player)) continue;

                pos = p;
                break;
            }

            if (pos == null) continue;

            if (level.isClientSide() || !(getLevel() instanceof ServerLevel serverLevel))
                return true;

            if (!player.isCreative())
                interactionHandItemStack.shrink(1);

            var chunk = level.getChunk(pos);
            var entityType = monster.getEntityType();
            var entity = entityType.spawn(serverLevel, interactionHandItemStack, player, pos, EntitySpawnReason.SPAWNER, true, true);
            if (entity != null)
            {
                chunk.addEntity(entity);
                playSpawnEffects(serverLevel, entity);
            }

            return true;
        }

        return false;
    }

    private void playSpawnEffects(ServerLevel level, Entity entity)
    {
        level.sendParticles(
                ParticleTypes.PORTAL,
                entity.getX(), entity.getY() + entity.getBbHeight() / 2, entity.getZ(),
                50, 0.5, 1, 0.5, 0.1
        );
        level.playSound(
                null, entity.blockPosition(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.HOSTILE,
                1.0F, 1.0F
        );
    }

    @Override
    public void onLivingDeathEvent(LivingDeathEvent event)
    {
        LivingEntity entity = event.getEntity();
//        PlayerManager.instance().sendMessage("living death");
        if (!(entity instanceof Enemy)) return;
        var blockEntityLevel = getLevel();
        if (blockEntityLevel == null || !entity.level().dimension().equals(blockEntityLevel.dimension())) return;

        var pos = getPosition();
        double distance = entity.distanceToSqr(pos.getX(), pos.getY(), pos.getZ());

        if (distance < SQR_EFFECTIVE_RADIUS)
        {
            entity.setHealth(1.0F);
            event.setCanceled(true);
        }
    }

    @Override
    public BlockPos getPosition()
    {
        return getBlockPos();
    }

    private void addToMonsterMap(IMonster monster)
    {
        monstersByActionItem.put(monster.getActionItem(), monster);
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState blockState, DevilBlockEntity devilBlockEntity)
    {
        if (level.getDifficulty() == Difficulty.PEACEFUL) return;
        if (level.isClientSide() || level.getGameTime() % 20 != 0) return;

        final int vertRadius = 10;
        for (int i = 0; i < 5; i++)
        {
            int dx = random.nextInt((CORRUPTION_RADIUS << 1) + 1) - CORRUPTION_RADIUS;
            int x = pos.getX() + dx;
            int radiusDecrement = Math.max(Math.abs(dx) - CORRUPTION_RADIUS / 2, 0);
            int newRadius = CORRUPTION_RADIUS - radiusDecrement;
            int z = pos.getZ() + random.nextInt((newRadius << 1) + 1) - newRadius;
            int y = pos.getY() + random.nextInt((vertRadius << 1) + 1) - vertRadius;

            var targetPos = new BlockPos(x, y, z);
            affectNearbyEntities((ServerLevel)level, pos, targetPos);

            if (pos.distSqr(targetPos) > SQR_CORRUPTION_RADIUS) continue;

            var targetState = level.getBlockState(targetPos);
            var abovePos = targetPos.above();
            var aboveState = level.getBlockState(abovePos);

            if (targetState.is(BlockTags.LOGS))
            {
                for (int j = 0; j < 4; j++)
                {
                    int lx = targetPos.getX() + random.nextInt(11) - 5;
                    int ly = targetPos.getY() + random.nextInt(8) - 2;
                    int lz = targetPos.getZ() + random.nextInt(11) - 5;
                    BlockPos leafPos = new BlockPos(lx, ly, lz);
                    if (pos.distSqr(leafPos) > SQR_CORRUPTION_RADIUS) continue;

                    BlockState leafState = level.getBlockState(leafPos);

                    if (leafState.is(BlockTags.LEAVES))
                    {
                        level.destroyBlock(leafPos, true);
                    }
                }
            }
            else if (targetState.is(BlockTags.LEAVES))
            {
                level.destroyBlock(targetPos, true);

                for (int j = 0; j < 4; j++)
                {
                    int lx = targetPos.getX() + random.nextInt(11) - 5;
                    int ly = targetPos.getY() + random.nextInt(9) - 4;
                    int lz = targetPos.getZ() + random.nextInt(11) - 5;
                    BlockPos leafPos = new BlockPos(lx, ly, lz);
                    if (pos.distSqr(leafPos) > SQR_CORRUPTION_RADIUS) continue;

                    BlockState leafState = level.getBlockState(leafPos);

                    if (leafState.is(BlockTags.LEAVES))
                    {
                        level.destroyBlock(leafPos, true);
                    }
                }
            }
            else if (targetState.is(Blocks.DIAMOND_ORE) || targetState.is(Blocks.DEEPSLATE_DIAMOND_ORE))
            {
                level.setBlock(targetPos, BlockRegistry.GREEN_DIAMOND_ORE.get().defaultBlockState(), Block.UPDATE_ALL);
            }
            else if (targetState.is(Blocks.OBSIDIAN))
            {
                boolean isCrying = random.nextBoolean();
                if (isCrying)
                    level.setBlock(targetPos, Blocks.CRYING_OBSIDIAN.defaultBlockState(), Block.UPDATE_ALL);
                else
                    level.setBlock(targetPos, BlockRegistry.CRACKED_OBSIDIAN.get().defaultBlockState(), Block.UPDATE_ALL);
            }
            else if (targetState.is(BlockTags.TERRACOTTA))
            {
                level.setBlock(targetPos, Blocks.BLACK_TERRACOTTA.defaultBlockState(), Block.UPDATE_ALL);
            }
            else if (isPlant(targetState))
            {
                if (i == 0)
                    level.destroyBlock(targetPos, true);
            }
            else if (aboveState.isAir() || aboveState.is(Blocks.WATER) || isPlant(aboveState))
            {
                if (i == 0)
                {
                    if (targetState.is(Blocks.GRASS_BLOCK) || targetState.is(Blocks.PODZOL))
                    {
                        level.setBlock(targetPos, Blocks.MYCELIUM.defaultBlockState(), Block.UPDATE_ALL);
                    }
                    else if (targetState.is(Blocks.FARMLAND) || targetState.is(Blocks.DIRT_PATH) || targetState.is(Blocks.DIRT))
                    {
                        level.setBlock(targetPos, Blocks.MUD.defaultBlockState(), Block.UPDATE_ALL);
                    }
                    else if (targetState.is(Blocks.STONE))
                    {
                        boolean isMossy = random.nextBoolean();
                        if (isMossy)
                            level.setBlock(targetPos, Blocks.MOSSY_COBBLESTONE.defaultBlockState(), Block.UPDATE_ALL);
                        else
                            level.setBlock(targetPos, Blocks.INFESTED_STONE.defaultBlockState(), Block.UPDATE_ALL);
                    }
                    else if (targetState.is(Blocks.COBBLESTONE))
                    {
                        boolean isMossy = random.nextBoolean();
                        if (isMossy)
                            level.setBlock(targetPos, Blocks.MOSSY_COBBLESTONE.defaultBlockState(), Block.UPDATE_ALL);
                        else
                            level.setBlock(targetPos, Blocks.INFESTED_COBBLESTONE.defaultBlockState(), Block.UPDATE_ALL);
                    }
                    else if (targetState.is(Blocks.DEEPSLATE))
                    {
                        level.setBlock(targetPos, Blocks.INFESTED_DEEPSLATE.defaultBlockState(), Block.UPDATE_ALL);
                    }
                    else if (targetState.is(Blocks.SAND))
                    {
                        if (aboveState.is(Blocks.WATER))
                            level.setBlock(targetPos, Blocks.CLAY.defaultBlockState(), Block.UPDATE_ALL);
                        else
                        {
                            boolean isEndstone = random.nextBoolean();
                            if (isEndstone)
                                level.setBlock(targetPos, Blocks.END_STONE.defaultBlockState(), Block.UPDATE_ALL);
                            else
                                level.setBlock(targetPos, Blocks.TERRACOTTA.defaultBlockState(), Block.UPDATE_ALL);
                        }
                    }
                    else if (targetState.is(Blocks.END_STONE))
                    {
                        level.setBlock(targetPos, BlockRegistry.CRACKED_ENDSTONE.get().defaultBlockState(), Block.UPDATE_ALL);
                    }
                    else if (targetState.is(BlockTags.ICE))
                    {
                        level.setBlock(targetPos, Blocks.WATER.defaultBlockState(), Block.UPDATE_ALL);
                    }
                }

                if (targetState.is(Blocks.CLAY))
                {
                    level.setBlock(targetPos, Blocks.MUD.defaultBlockState(), Block.UPDATE_ALL);
                }
            }
        }
    }

    private void affectNearbyEntities(ServerLevel level, BlockPos devilPos, BlockPos targetPos)
    {
        double centerX = targetPos.getX() + 0.5;
        double centerZ = targetPos.getZ() + 0.5;
        var searchArea = new AABB(
                centerX - AFFECTED_HORIZONTAL_RADIUS, level.getMinY(), centerZ - AFFECTED_HORIZONTAL_RADIUS,
                centerX + AFFECTED_HORIZONTAL_RADIUS, level.getMaxY(), centerZ + AFFECTED_HORIZONTAL_RADIUS
        );

        var entities = level.getEntities((Entity)null, searchArea, entity ->
        {
            if (entity.distanceToSqr(devilPos.getX(), devilPos.getY(), devilPos.getZ()) > SQR_CORRUPTION_RADIUS) return false;

            if (entity instanceof ServerPlayer player)
            {
                return player.gameMode() == GameType.SURVIVAL;
            }

            if (entity.distanceToSqr(targetPos.getX(), targetPos.getY(), targetPos.getZ()) > SQR_AFFECT_VILLAGERS_RADIUS) return false;

            return entity instanceof Villager || entity instanceof AbstractPiglin;
        });

        for (var entity : entities)
        {
            if (entity instanceof ServerPlayer player)
            {
                player.addEffect(new MobEffectInstance(MobEffects.NAUSEA, 100, 0, false, false, true));
            }
            else if (entity instanceof Villager villager)
            {
                transformVillager(level, villager);
            }
            else if (entity instanceof AbstractPiglin piglin)
            {
                var conversionSound = piglin instanceof PiglinBrute
                        ? SoundEvents.PIGLIN_BRUTE_CONVERTED_TO_ZOMBIFIED
                        : SoundEvents.PIGLIN_CONVERTED_TO_ZOMBIFIED;
                piglin.makeSound(conversionSound);
                piglin.convertTo(EntityType.ZOMBIFIED_PIGLIN, ConversionParams.single(piglin, true, true), zombifiedPiglin -> {});
            }
        }
    }

    private void transformVillager(ServerLevel level, Villager villager)
    {
        if (villager.getVillagerData().profession().is(VillagerProfession.NITWIT)) return;

        if (random.nextBoolean())
        {
            villager.setVillagerData(villager.getVillagerData().withProfession(level.registryAccess(), VillagerProfession.NITWIT));
            villager.refreshBrain(level);
            level.playSound(null, villager.blockPosition(), SoundEvents.VILLAGER_NO, SoundSource.AMBIENT, 1.2F, 0.7F);
            return;
        }

        villager.convertTo(EntityType.ZOMBIE_VILLAGER, ConversionParams.single(villager, true, true), zombieVillager ->
        {
            zombieVillager.finalizeSpawn(
                    level,
                    level.getCurrentDifficultyAt(zombieVillager.blockPosition()),
                    EntitySpawnReason.CONVERSION,
                    new Zombie.ZombieGroupData(false, true)
            );
            zombieVillager.setVillagerData(villager.getVillagerData());
            zombieVillager.setGossips(villager.getGossips().copy());
            zombieVillager.setTradeOffers(villager.getOffers().copy());
            zombieVillager.setVillagerXp(villager.getVillagerXp());
            playVillagerTransformationSound(level, zombieVillager);
        });
    }

    private void playVillagerTransformationSound(ServerLevel level, LivingEntity entity)
    {
        if (!entity.isSilent())
            level.levelEvent(null, LevelEvent.SOUND_ZOMBIE_INFECTED, entity.blockPosition(), 0);
    }

    private boolean isPlant(BlockState blockState)
    {
        var block = blockState.getBlock();
        return block instanceof VegetationBlock || blockState.is(Blocks.CACTUS) || blockState.is(Blocks.SUGAR_CANE) || blockState.is(Blocks.BAMBOO);
    }
}
