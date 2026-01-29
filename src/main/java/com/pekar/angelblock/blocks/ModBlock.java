package com.pekar.angelblock.blocks;

import com.pekar.angelblock.utils.Utils;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.Block;

public class ModBlock extends Block
{
    protected final Utils utils = new Utils();

    public ModBlock(Properties properties)
    {
        super(properties);
    }

    protected InteractionResult getInteractionSidedSuccess(boolean isClientSide)
    {
        return isClientSide ? InteractionResult.SUCCESS : InteractionResult.SUCCESS_SERVER;
    }
}
