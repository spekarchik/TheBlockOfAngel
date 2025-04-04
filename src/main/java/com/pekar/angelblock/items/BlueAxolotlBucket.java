package com.pekar.angelblock.items;

import com.pekar.angelblock.text.TextStyle;
import com.pekar.angelblock.network.packets.PlaySoundPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;

import java.util.List;

public class BlueAxolotlBucket extends ModItemWithHoverText
{
    public BlueAxolotlBucket(Properties properties)
    {
        super(TextStyle.Notice, properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        var level = context.getLevel();
        var player = context.getPlayer();
        var pos = context.getClickedPos().relative(context.getClickedFace());

        if (level instanceof ServerLevel serverLevel && player instanceof ServerPlayer serverPlayer)
        {
            var axolotl = EntityType.AXOLOTL.create(serverLevel, EntitySpawnReason.BUCKET);
            if (axolotl != null)
            {
                axolotl.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                axolotl.setVariant(Axolotl.Variant.BLUE);
                axolotl.setBaby(true);
                axolotl.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(new BlockPos(pos)), EntitySpawnReason.BUCKET, null);
                var result = serverLevel.addFreshEntity(axolotl);

                if (result)
                {
                    new PlaySoundPacket(SoundEvents.BUCKET_EMPTY_AXOLOTL, 1.0F).sendToPlayer(serverPlayer);
                    level.setBlock(pos, Blocks.WATER.defaultBlockState(), 11);
                    player.setItemInHand(context.getHand(), new ItemStack(Items.BUCKET));
                }
            }
        }

        return sidedSuccess(level.isClientSide());
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
