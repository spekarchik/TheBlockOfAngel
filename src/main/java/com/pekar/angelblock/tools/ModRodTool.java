package com.pekar.angelblock.tools;

import com.pekar.angelblock.utils.Utils;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;

public abstract class ModRodTool extends TieredItem implements IModTool
{
    protected final Utils utils = new Utils();

    public ModRodTool(Tier material, Properties properties)
    {
        super(material, properties);
    }

    @Override
    public TieredItem getTool()
    {
        return this;
    }
}
