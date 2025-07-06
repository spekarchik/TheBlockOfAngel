package com.pekar.angelblock.blocks.tile_entities;

import com.pekar.angelblock.blocks.BlockRegistry;
import com.pekar.angelblock.blocks.tile_entities.monsters.IMonster;
import com.pekar.angelblock.blocks.tile_entities.monsters.Monsters;
import com.pekar.angelblock.events.ILivingDeathEventHandler;
import com.pekar.angelblock.events.PlayerInteractionEvents;
import com.pekar.angelblock.utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DevilBlockEntity extends BlockEntity implements ILivingDeathEventHandler, BlockEntityTicker<DevilBlockEntity>
{
    private static final int EFFECTIVE_RADIUS = 70;
    private static final int SQR_EFFECTIVE_RADIUS = EFFECTIVE_RADIUS * EFFECTIVE_RADIUS;
    private static final int MONSTER_SPAWN_RADIUS = 30;
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

            Utils.instance.sound.playSoundByBlock(player, getBlockPos(), SoundEvents.DRIPSTONE_BLOCK_PLACE);

            if (level.isClientSide() || !(getLevel() instanceof ServerLevel serverLevel))
                return true;

            interactionHandItemStack.setCount(interactionHandItemStack.getCount() - 1);

            var chunk = level.getChunk(pos);
            var entityType = monster.getEntityType();
            var entity = entityType.spawn(serverLevel, interactionHandItemStack, player, pos, MobSpawnType.SPAWNER, true, true);
            if (entity != null)
                chunk.addEntity(entity);

            return true;
        }

        return false;
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
        if (level.isClientSide() || level.getGameTime() % 20 != 0) return;

        final int horizRadius = 18;
        final int vertRadius = 5;
        for (int i = 0; i < 5; i++)
        {
            int dx = random.nextInt((horizRadius << 1) + 1) - horizRadius;
            int x = pos.getX() + dx;
            int radiusDecrement = Math.max(Math.abs(dx) - horizRadius / 2, 0);
            int newRadius = horizRadius - radiusDecrement;
            int z = pos.getZ() + random.nextInt((newRadius << 1) + 1) - newRadius;
            int y = pos.getY() + random.nextInt((vertRadius << 1) + 1) - vertRadius;

            var targetPos = new BlockPos(x, y, z);
            var targetState = level.getBlockState(targetPos);
            var abovePos = targetPos.above();
            var aboveState = level.getBlockState(abovePos);

            if (targetState.is(BlockTags.LOGS))
            {
                for (int j = 0; j < 3; j++)
                {
                    int lx = targetPos.getX() + random.nextInt(11) - 5;
                    int ly = targetPos.getY() + random.nextInt(8) - 2;
                    int lz = targetPos.getZ() + random.nextInt(11) - 5;
                    BlockPos leafPos = new BlockPos(lx, ly, lz);
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

                for (int j = 0; j < 3; j++)
                {
                    int lx = targetPos.getX() + random.nextInt(11) - 5;
                    int ly = targetPos.getY() + random.nextInt(9) - 4;
                    int lz = targetPos.getZ() + random.nextInt(11) - 5;
                    BlockPos leafPos = new BlockPos(lx, ly, lz);
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

    private boolean isPlant(BlockState blockState)
    {
        return blockState.getBlock() instanceof BushBlock || blockState.is(Blocks.CACTUS) || blockState.is(Blocks.SUGAR_CANE);
    }
}
