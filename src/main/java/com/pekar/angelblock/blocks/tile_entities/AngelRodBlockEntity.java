package com.pekar.angelblock.blocks.tile_entities;

import com.pekar.angelblock.Main;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.block.state.BlockState;

public class AngelRodBlockEntity extends DespawnMonsterBlockEntity<AngelRodBlockEntity>
{
    private static final String DamageTagName = Main.MODID + ":AngelRodDamage";
    private static final String IsBrokenTagName = Main.MODID + ":AngelRodIsBroken";
    private int damage;
    private boolean isBroken;

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
        return !isBroken && entity instanceof Enemy;
    }

    protected void loadModTag(CompoundTag tag)
    {
        damage = tag.getIntOr(DamageTagName, 0);
        isBroken = tag.getBooleanOr(IsBrokenTagName, false);
    }

    protected void saveModTag(CompoundTag tag)
    {
        tag.putInt(DamageTagName, damage);
        tag.putBoolean(IsBrokenTagName, isBroken);
    }

    public void setDamage(int damage, boolean isBroken)
    {
        this.damage = damage;
        this.isBroken = isBroken;
        var tag = new CompoundTag();
        saveModTag(tag);
    }

    public int getDamage()
    {
        //var tag = new CompoundTag();
        //saveModTag(tag);
        return damage;
    }
}
