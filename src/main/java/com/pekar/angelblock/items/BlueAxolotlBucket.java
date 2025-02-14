package com.pekar.angelblock.items;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;

public class BlueAxolotlBucket extends ModItem
{
    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        var level = context.getLevel();
        var player = context.getPlayer();
        var pos = context.getClickedPos().relative(context.getClickedFace());

        if (!level.isClientSide() && level instanceof ServerLevel serverLevel && player != null)
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
}
