package com.pekar.angelblock.blocks.tile_entities;

import com.pekar.angelblock.blocks.tile_entities.monsters.Creeper;
import com.pekar.angelblock.blocks.tile_entities.monsters.IMonster;
import com.pekar.angelblock.blocks.tile_entities.monsters.Skeleton;
import com.pekar.angelblock.blocks.tile_entities.monsters.Zombie;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;

public class AngelBlockEntity extends BlockEntity implements BlockEntityTicker<AngelBlockEntity>
{
    private static final double EFFECTIVE_RADIUS = 70.0;
    private int counter;
    private final Set<IMonster> monstersToIgnore = new HashSet<>();
    private final Map<Item, IMonster> monsterMap = new HashMap<>();
    private final Map<Byte, IMonster> monstersById = new HashMap<>();

    public AngelBlockEntity(BlockPos blockPos, BlockState blockState)
    {
        super(EntityRegistry.ANGEL_BLOCK_ENTITY.get(), blockPos, blockState);

        addToMonsterMap(new Skeleton((byte) 0));
        addToMonsterMap(new Zombie((byte) 1));
        addToMonsterMap(new Creeper((byte) 2));
    }

    public void addMonsterToFilter(Item item)
    {
        if (!monsterMap.containsKey(item)) return;

        var monster = monsterMap.get(item);
        monstersToIgnore.add(monster);

        setChanged();
    }

    public void resetFilter()
    {
        monstersToIgnore.clear();

        setChanged();
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState blockState, AngelBlockEntity entity)
    {
        if (++counter > 40)
        {
            onUpdate(level, entity);
            counter = 0;
        }
    }

    @Override
    public void load(CompoundTag compoundTag)
    {
        super.load(compoundTag);
        monstersToIgnore.clear();

        byte[] array = compoundTag.getByteArray("MonsterFilter");
        for (var monsterId : array)
        {
            monstersToIgnore.add(monstersById.get(monsterId));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag)
    {
        super.saveAdditional(compoundTag);

        saveMonsterFilter(compoundTag);
    }

    @Override
    public CompoundTag getUpdateTag()
    {
        var compoundTag = new CompoundTag();
        saveMonsterFilter(compoundTag);
        return compoundTag;
    }

    private void saveMonsterFilter(CompoundTag compoundTag)
    {
        byte[] array = new byte[monstersToIgnore.size()];

        int i = 0;
        for (var monster : monstersToIgnore)
        {
            array[i++] = monster.getId();
        }

        compoundTag.putByteArray("MonsterFilter", array);
    }

    private void onUpdate(Level level, AngelBlockEntity blockEntity)
    {
        if (level.isClientSide()) return;

        var monsters = level.getEntities((LivingEntity)null,
                blockEntity.getRenderBoundingBox().inflate(EFFECTIVE_RADIUS),
                entity -> entity instanceof Enemy && monstersToIgnore.stream().noneMatch(m -> m.belongs((LivingEntity) entity)));

        for (Entity entity : monsters)
        {
            entity.discard();
        }
    }

    private void addToMonsterMap(IMonster monster)
    {
        monsterMap.put(monster.getActionItem(), monster);
        monstersById.put(monster.getId(), monster);
    }
}
