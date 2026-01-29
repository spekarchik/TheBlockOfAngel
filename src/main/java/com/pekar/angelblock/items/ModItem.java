package com.pekar.angelblock.items;

import com.pekar.angelblock.utils.Utils;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;

public class ModItem extends Item
{
    protected final Utils utils = new Utils();

    public ModItem(Properties properties)
    {
        super(properties);
    }

    protected InteractionResult sidedSuccess(boolean isClientSide)
    {
        return isClientSide ? InteractionResult.SUCCESS : InteractionResult.SUCCESS_SERVER;
    }
}
