package com.pekar.angelblock.tools;

import com.pekar.angelblock.blocks.BlockRegistry;
import com.pekar.angelblock.network.packets.PlaySoundPacket;
import com.pekar.angelblock.network.packets.SoundType;
import com.pekar.angelblock.tools.properties.SuperMaterialProperties;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.InfestedBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class SuperPickaxe extends EnhancedPickaxe
{
    public SuperPickaxe(Tier material, int attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage, attackSpeed, properties, new SuperMaterialProperties());
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        var result = super.useOn(context);
        if (result == InteractionResult.FAIL) return result;

        var player = context.getPlayer();
        var level = player.level();

//        if (level.isClientSide) return result;
//        if (!canUseToolEffect(player)) return result;

        var pos = context.getClickedPos();

        BlockState blockState = level.getBlockState(pos);
        Block block = blockState.getBlock();

        if (block == Blocks.OBSIDIAN)
        {
            if (!level.isClientSide())
            {
                level.setBlock(pos, BlockRegistry.CRACKED_OBSIDIAN.get().defaultBlockState(), 11);
            }

            return getToolInteractionResult(true, level.isClientSide());
        }

        if (block instanceof InfestedBlock infestedBlock)
        {
            if (!level.isClientSide())
            {
                level.setBlock(pos, infestedBlock.getHostBlock().defaultBlockState(), 11);
                new PlaySoundPacket(SoundType.INFESTED_BLOCK).sendToPlayer((ServerPlayer) player);
            }

            return getToolInteractionResult(true, level.isClientSide());
        }

        return result;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {
        if (!utils.text.showExtendedDescription(tooltipComponents)) return;

        for (int i = 0; i <= 10; i++)
        {
            tooltipComponents.add(getDescription(i, i == 1 || i == 8, false, i == 6));
        }
    }
}
