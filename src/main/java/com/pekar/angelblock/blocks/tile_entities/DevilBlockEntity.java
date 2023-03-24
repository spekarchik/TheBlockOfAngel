package com.pekar.angelblock.blocks.tile_entities;

import com.pekar.angelblock.blocks.tile_entities.monsters.IMonster;
import com.pekar.angelblock.blocks.tile_entities.monsters.Monsters;
import com.pekar.angelblock.events.ILivingDeathEventHandler;
import com.pekar.angelblock.events.PlayerInteractionEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import java.util.HashMap;
import java.util.Map;

public class DevilBlockEntity extends BlockEntity implements ILivingDeathEventHandler
{
    private static final int EFFECTIVE_RADIUS = 70;
    private static final int SQR_EFFECTIVE_RADIUS = EFFECTIVE_RADIUS * EFFECTIVE_RADIUS;
    private static final int MONSTER_SPAWN_RADIUS = 30;

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
        addToMonsterMap(Monsters.Piglin);
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

            for (var p = startPos.above(MONSTER_SPAWN_RADIUS); p.getY() > minY; p = p.below())
            {
                if (!level.getBlockState(p).isSolidRender(level, p))
                    continue;

                boolean isEmptyAbove = level.getBlockState(p.above()).isAir() && level.getBlockState(p.above(2)).isAir() && level.getBlockState(p.above(3)).isAir();
                if (!isEmptyAbove) continue;

                pos = p;
                break;
            }

            if (pos == null) continue;

            if (level.isClientSide() || !(getLevel() instanceof ServerLevel serverLevel))
                return true;

            interactionHandItemStack.setCount(interactionHandItemStack.getCount() - 1);

            var chunk = level.getChunk(pos);
            var entityType = monstersByActionItem.get(item).getEntityType();
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

        var pos = getPosition();
        double distance = entity.distanceToSqr(pos.getX(), pos.getY(), pos.getZ());

        if (distance < SQR_EFFECTIVE_RADIUS)
        {
            entity.setHealth(entity.getMaxHealth());
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
}
