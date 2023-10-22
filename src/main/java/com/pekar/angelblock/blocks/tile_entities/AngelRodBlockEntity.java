package com.pekar.angelblock.blocks.tile_entities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.block.state.BlockState;

public class AngelRodBlockEntity extends DespawnMonsterBlockEntity<AngelRodBlockEntity>
{
    private static final String DamageTagName = "AngelRodDamage";
    private int damage;

    public AngelRodBlockEntity(BlockPos blockPos, BlockState blockState)
    {
        super(EntityRegistry.ANGEL_ROD_BLOCK_ENTITY.get(), blockPos, blockState);
    }

    @Override
    protected double getEffectiveRadius()
    {
        return 20.0;
    }

    @Override
    protected boolean needToDespawnEntity(Entity entity)
    {
        return entity instanceof Enemy;
    }

    @Override
    public void load(CompoundTag compoundTag)
    {
        super.load(compoundTag);
        damage = compoundTag.getInt(DamageTagName);
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag)
    {
        super.saveAdditional(compoundTag);
        compoundTag.putInt(DamageTagName, damage);
    }

    @Override
    public CompoundTag getUpdateTag()
    {
        var tag = new CompoundTag();
        tag.putInt(DamageTagName, damage);
        return tag;
    }

    public void setDamage(int damage)
    {
        this.damage = damage;
        var tag = getUpdateTag();
        tag.putInt(DamageTagName, damage);
        saveAdditional(tag);
    }

    public int getDamage()
    {
        var tag = getUpdateTag();
        load(tag);
        return tag.getInt(DamageTagName);
    }
}
