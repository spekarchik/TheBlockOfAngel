package com.pekar.angelblock.tools;

import com.pekar.angelblock.network.packets.PlaySoundPacket;
import com.pekar.angelblock.network.packets.SoundType;
import com.pekar.angelblock.potions.PotionRegistry;
import com.pekar.angelblock.tools.properties.LimoniteMaterialProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.InfestedBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class LimonitePickaxe extends EnhancedPickaxe
{
    public LimonitePickaxe(Tier material, int attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage, attackSpeed, properties, new LimoniteMaterialProperties());
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
    protected void mineAdditionalBlocks(Level level, BlockPos pos, LivingEntity entityLiving)
    {
        if (!isEnhanced() || !entityLiving.hasEffect(PotionRegistry.TOOL_ADVANCED_MODE_EFFECT))
            return;

        BlockState blockState = level.getBlockState(pos);
        var block = blockState.getBlock();

        if (!isToolEffective(entityLiving, pos)) return;

        float initialHardness = block.defaultDestroyTime();

        if (initialHardness != 0.0F)
        {
            int increment = 1;
            while (canProceed(entityLiving, pos.above(increment)))
            {
                onBlockMining(level, blockState, initialHardness, pos.above(increment++), entityLiving);
            }

            increment = 1;
            while (canProceed(entityLiving, pos.below(increment)))
            {
                onBlockMining(level, blockState, initialHardness, pos.below(increment++), entityLiving);
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {
        for (int i = 0; i <= 6; i++)
        {
            tooltipComponents.add(getDescription(i, i == 1 || i == 5, false, i == 3));
        }
    }

    private boolean canProceed(LivingEntity entityLiving, BlockPos pos)
    {
        return !entityLiving.level().isEmptyBlock(pos) && isToolEffective(entityLiving, pos);
    }
}
