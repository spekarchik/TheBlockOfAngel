package com.pekar.angelblock.tools;

import com.pekar.angelblock.utils.Utils;
import net.minecraft.world.item.TieredItem;

public abstract class ModRodTool extends TieredItem implements IModTool
{
    protected final Utils utils = new Utils();

    public ModRodTool(ModToolMaterial material, Properties properties)
    {
        super(material, material.isFireResistant() ? properties.fireResistant() : properties);
    }

    @Override
    public TieredItem getTool()
    {
        return this;
    }
}
