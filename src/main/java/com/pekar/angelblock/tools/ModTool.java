package com.pekar.angelblock.tools;

import com.pekar.angelblock.utils.Utils;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;

public abstract class ModTool extends TieredItem implements IModTool
{
    protected final Utils utils = new Utils();

    public ModTool(Tier material, Properties properties)
    {
        super(material, properties);
    }

    @Override
    public boolean isEnhancedTool()
    {
        return false;
    }

    @Override
    public boolean isEnhancedWeapon()
    {
        return false;
    }

    @Override
    public boolean isEnhancedRod()
    {
        return false;
    }

    @Override
    public TieredItem getTool()
    {
        return this;
    }
}
