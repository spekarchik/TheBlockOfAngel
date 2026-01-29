package com.pekar.angelblock.blocks.tile_entities;

import com.pekar.angelblock.Main;
import com.pekar.angelblock.blocks.tile_entities.monsters.IMonster;
import com.pekar.angelblock.blocks.tile_entities.monsters.Monsters;
import com.pekar.angelblock.utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AngelBlockEntity extends DespawnMonsterBlockEntity<AngelBlockEntity>
{
    private static final String MonsterFilterTagName = Main.MODID + ":MonsterFilter";

    private final Set<IMonster> monstersToIgnore = new HashSet<>();
    private final Map<Item, IMonster> monstersByActionItem = new HashMap<>();
    private final Map<Integer, IMonster> monstersById = new HashMap<>();

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
        return entity instanceof Enemy && monstersToIgnore.stream().noneMatch(m -> m.belongs((LivingEntity) entity));
    }

    @Override
    protected void loadModTag(ValueInput input)
    {
        monstersToIgnore.clear();

        var array = input.getIntArray(MonsterFilterTagName).orElse(new int[]{});
        for (var monsterId : array)
        {
            monstersToIgnore.add(monstersById.get(monsterId));
        }
    }

    @Override
    protected void saveModTag(ValueOutput output)
    {
        var array = new int[monstersToIgnore.size()];

        int i = 0;
        for (var monster : monstersToIgnore)
        {
            array[i++] = monster.getId();
        }

        output.putIntArray(MonsterFilterTagName, array);
    }

    @Override
    protected void saveModTag(CompoundTag tag)
    {
        var array = new int[monstersToIgnore.size()];

        int i = 0;
        for (var monster : monstersToIgnore)
        {
            array[i++] = monster.getId();
        }

        tag.putIntArray(MonsterFilterTagName, array);
    }

    private void addToMonsterMap(IMonster monster)
    {
        monstersByActionItem.put(monster.getActionItem(), monster);
        monstersById.put(monster.getId(), monster);
    }
}
