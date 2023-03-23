package com.pekar.angelblock.blocks.tile_entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AngelBlockEntity extends BlockEntity implements BlockEntityTicker<AngelBlockEntity>
{
    private static final double EFFECTIVE_RADIUS = 70.0;
    private int counter;

    private final NonNullList<Class> monstersToIgnore = NonNullList.create();

    private final HashMap<Item, Class> monsterMap = new HashMap<>();

    private final List<Class> monstersByIndex = new ArrayList<>(monsterMap.size());

    public AngelBlockEntity(BlockPos blockPos, BlockState blockState)
    {
        super(EntityRegistry.ANGEL_BLOCK_ENTITY.get(), blockPos, blockState);

        monsterMap.put(Items.BONE, Skeleton.class);
        monsterMap.put(Items.ROTTEN_FLESH, Zombie.class);
        monsterMap.put(Items.GUNPOWDER, Creeper.class);

        monstersByIndex.add(0, Skeleton.class);
        monstersByIndex.add(1, Zombie.class);
        monstersByIndex.add(2, Creeper.class);
    }

    public void addMonsterToFilter(Item item)
    {
        if (!monsterMap.containsKey(item)) return;

        var monsterClass = monsterMap.get(item);

        if (!monstersToIgnore.contains(monsterClass))
            monstersToIgnore.add(monsterClass);

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

        int[] array = compoundTag.getIntArray("MonsterFilter");
        for (var v : array)
        {
            monstersToIgnore.add(monstersByIndex.get(v));
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
        int[] array = new int[monstersToIgnore.size()];

        for (int i = 0; i < monstersToIgnore.size(); i++)
        {
            int b = monstersByIndex.indexOf(monstersToIgnore.get(i));
            if (b < 0) continue;

            array[i] = b;
        }

        compoundTag.putIntArray("MonsterFilter", array);
    }

    private void onUpdate(Level level, AngelBlockEntity blockEntity)
    {
        if (level.isClientSide()) return;

        var monsters = level.getEntities((Entity)null,
                blockEntity.getRenderBoundingBox().inflate(EFFECTIVE_RADIUS),
                entity -> entity instanceof Enemy && !monstersToIgnore.contains(entity.getClass()));

        for (Entity entity : monsters)
        {
            entity.discard();
        }
    }
}
