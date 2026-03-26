package com.pekar.angelblock.blocks.tile_entities;

import com.pekar.angelblock.Main;
import com.pekar.angelblock.blocks.AngelBlock;
import com.pekar.angelblock.blocks.tile_entities.monsters.IMonster;
import com.pekar.angelblock.blocks.tile_entities.monsters.Monsters;
import com.pekar.angelblock.events.scheduler.base.IScheduledTask;
import com.pekar.angelblock.events.scheduler.base.ScheduledTask;
import com.pekar.angelblock.utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AngelBlockEntity extends DespawnMonsterBlockEntity<AngelBlockEntity>
{
    private static final int HOSTILE_LEVEL_WARMING_UP_TIME_TICKS = 24000;
    private static final int PEACEFUL_LEVEL_WORMING_UP_TIME_TICKS = 240;
    private static final String MonsterFilterTagName = Main.MODID + ":MonsterFilter";
    private static final String WormTicksLeftTagName = Main.MODID + ":WormTicksLeft";

    private final Set<IMonster> monstersToIgnore = new HashSet<>();
    private final Map<Item, IMonster> monstersByActionItem = new HashMap<>();
    private final Map<Byte, IMonster> monstersById = new HashMap<>();
    private IScheduledTask wormingUpTask;
    private int wormTicksLeft;

    public AngelBlockEntity(BlockPos blockPos, BlockState blockState)
    {
        super(EntityRegistry.ANGEL_BLOCK_ENTITY.get(), blockPos, blockState);

        addToMonsterMap(Monsters.Skeleton);
        addToMonsterMap(Monsters.Zombie);
        addToMonsterMap(Monsters.Creeper);
        addToMonsterMap(Monsters.Enderman);
        addToMonsterMap(Monsters.Witch);
        addToMonsterMap(Monsters.Spider);
        addToMonsterMap(Monsters.CaveSpider);
        addToMonsterMap(Monsters.Vindicator);
        addToMonsterMap(Monsters.Slime);
        addToMonsterMap(Monsters.MagmaCube);
        addToMonsterMap(Monsters.WitherSkeleton);
        addToMonsterMap(Monsters.Guardian);
        addToMonsterMap(Monsters.ElderGuardian);
        addToMonsterMap(Monsters.Shulker);
        addToMonsterMap(Monsters.EnderDragon);
        addToMonsterMap(Monsters.Wither);
        addToMonsterMap(Monsters.Blaze);
        addToMonsterMap(Monsters.Ghast);
        addToMonsterMap(Monsters.Hoglin);
        addToMonsterMap(Monsters.Phantom);
        addToMonsterMap(Monsters.PiglinBrute);
        addToMonsterMap(Monsters.Warden);
        addToMonsterMap(Monsters.ZombieVillager);
        addToMonsterMap(Monsters.Evoker);
        addToMonsterMap(Monsters.Ravager);
        addToMonsterMap(Monsters.Piglin);
        addToMonsterMap(Monsters.Breeze);
        addToMonsterMap(Monsters.Creaking);
        //addToMonsterMap(Monsters.Husk);
        //addToMonsterMap(Monsters.Pillager);
    }

    public boolean addMonsterToFilter(Item item, Player player)
    {
        if (!monstersByActionItem.containsKey(item)) return false;

        var monster = monstersByActionItem.get(item);
        if (monstersToIgnore.contains(monster))
            monstersToIgnore.remove(monster);
        else
            monstersToIgnore.add(monster);

        if (player instanceof ServerPlayer serverPlayer)
        {
            setChanged();
        }

        Utils.instance.sound.playSoundByBlock(player, getBlockPos(), SoundEvents.DRIPSTONE_BLOCK_PLACE);

        return true;
    }

    public boolean resetFilter(Player player)
    {
        if (monstersInFilter() == 0) return false;

        monstersToIgnore.clear();

        if (player instanceof ServerPlayer serverPlayer)
        {
            setChanged();
        }

        Utils.instance.sound.playSoundByBlock(player, getBlockPos(), SoundEvents.MAGMA_CUBE_DEATH_SMALL);

        return true;
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState blockState, AngelBlockEntity entity)
    {
        if (level == null || level.isClientSide()) return;

        if (wormingUpTask != null)
        {
            wormTicksLeft = wormingUpTask.getCounter();
            if (wormTicksLeft % 100 == 0) setChanged();
            wormingUpTask.decrementOrExecute();
        }

        super.tick(level, pos, blockState, entity);
    }

    public void startWormingUp()
    {
        int wormingUpTicks = level != null && level.getLevelData().getDifficulty() == Difficulty.PEACEFUL ? PEACEFUL_LEVEL_WORMING_UP_TIME_TICKS : HOSTILE_LEVEL_WARMING_UP_TIME_TICKS;
        wormingUpTask = new ScheduledTask<>(wormingUpTicks, this, this::onWormedUp);
    }

    private void continueWormingUp()
    {
        if (wormingUpTask == null)
        {
            wormingUpTask = new ScheduledTask<>(wormTicksLeft, this, this::onWormedUp);
        }
    }

    private void onWormedUp(AngelBlockEntity angelBlockEntity)
    {
        var blockState = angelBlockEntity.getBlockState();
        if (blockState.getBlock() instanceof AngelBlock angelBlock)
        {
            var level = angelBlockEntity.getLevel();
            if (level == null) return;
            angelBlock.setBlockStateForWarmingUp(level, angelBlockEntity.getBlockPos(), false);
            wormingUpTask = null;
            wormTicksLeft = 0;
            setChanged();
        }
    }

    public int monstersInFilter()
    {
        return monstersToIgnore.size();
    }

    @Override
    protected double getEffectiveRadius()
    {
        return 70.0;
    }

    @Override
    protected boolean needToDespawnEntity(Entity entity)
    {
        return wormingUpTask == null && entity instanceof Enemy && monstersToIgnore.stream().noneMatch(m -> m.belongs((LivingEntity) entity));
    }

    @Override
    protected void loadModTag(CompoundTag tag)
    {
        monstersToIgnore.clear();

        var array = tag.getByteArray(MonsterFilterTagName).orElse(new byte[]{});
        for (var monsterId : array)
        {
            monstersToIgnore.add(monstersById.get(monsterId));
        }

        wormTicksLeft = tag.getInt(WormTicksLeftTagName).orElse(0);
        if (wormTicksLeft > 0)
        {
            continueWormingUp();
        }
    }

    @Override
    protected void saveModTag(CompoundTag tag)
    {
        var array = new byte[monstersToIgnore.size()];

        int i = 0;
        for (var monster : monstersToIgnore)
        {
            array[i++] = monster.getId();
        }

        tag.putByteArray(MonsterFilterTagName, array);
        tag.putInt(WormTicksLeftTagName, wormTicksLeft);
    }

    private void addToMonsterMap(IMonster monster)
    {
        monstersByActionItem.put(monster.getActionItem(), monster);
        monstersById.put(monster.getId(), monster);
    }
}
