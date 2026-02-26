package com.pekar.angelblock.items;

import com.pekar.angelblock.TextStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.List;

public class BlueAxolotlBucket extends ModItemWithHoverText
{
    public BlueAxolotlBucket()
    {
        super(TextStyle.Notice, new Item.Properties().rarity(Rarity.EPIC).stacksTo(1));
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        var level = context.getLevel();
        var player = context.getPlayer();
        var pos = context.getClickedPos().relative(context.getClickedFace());

        if (level instanceof ServerLevel serverLevel && player instanceof ServerPlayer serverPlayer)
        {
            var axolotl = EntityType.AXOLOTL.create(serverLevel);
            if (axolotl != null)
            {
                axolotl.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                axolotl.setVariant(Axolotl.Variant.BLUE);
                axolotl.setBaby(true);
                axolotl.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(new BlockPos(pos)), MobSpawnType.BUCKET, null);
                var result = serverLevel.addFreshEntity(axolotl);

                if (result)
                {
                    level.playSound(null, pos, SoundEvents.BUCKET_EMPTY_AXOLOTL, SoundSource.BLOCKS);
                    level.setBlock(pos, Blocks.WATER.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
                    player.setItemInHand(context.getHand(), new ItemStack(Items.BUCKET));
                }
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    public int getMaxStackSize(ItemStack stack)
    {
        return 1;
    }

    @Override
    public int getDefaultMaxStackSize()
    {
        return 1;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag tooltipFlag)
    {
        if (!utils.text.showExtendedDescription(components)) return;

        components.add(getDisplayName().withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
    }
}
