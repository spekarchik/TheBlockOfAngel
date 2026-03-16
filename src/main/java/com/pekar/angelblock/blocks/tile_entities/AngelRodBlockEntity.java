package com.pekar.angelblock.blocks.tile_entities;

import com.pekar.angelblock.Main;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

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

    protected void loadModTag(ValueInput input)
    {
        damage = input.getIntOr(DamageTagName, 0);
        isBroken = input.getBooleanOr(IsBrokenTagName, false);
    }

    protected void saveModTag(ValueOutput output)
    {
        output.putInt(DamageTagName, damage);
        output.putBoolean(IsBrokenTagName, isBroken);
    }

    protected void saveModTag(CompoundTag tag)
    {
        tag.putInt(DamageTagName, damage);
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
