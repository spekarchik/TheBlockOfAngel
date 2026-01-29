package com.pekar.angelblock.tools;

import net.minecraft.world.entity.player.Player;

public abstract class WorkRod extends ModRod
{
    public WorkRod(ModToolMaterial material, Properties properties)
    {
        super(material, false, properties);
    }

    protected void causePlayerExhaustion(Player player)
    {
        utils.player.causePlayerExhaustion(player, 2);
    }
}
