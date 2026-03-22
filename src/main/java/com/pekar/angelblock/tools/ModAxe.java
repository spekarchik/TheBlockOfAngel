package com.pekar.angelblock.tools;

import com.pekar.angelblock.tooltip.ITooltip;
import com.pekar.angelblock.tooltip.TextStyle;
import com.pekar.angelblock.utils.Utils;
import com.pekar.angelblock.tools.properties.DefaultMaterialProperties;
import com.pekar.angelblock.tools.properties.IMaterialProperties;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

import javax.annotation.Nullable;
import java.util.Optional;

public class ModAxe extends ModTool implements IModToolEnhanceable
{
    protected final IMaterialProperties materialProperties;
    protected final Utils utils = new Utils();
    private final ModToolMaterial material;

    public static ModAxe createPrimary(ModToolMaterial material, float attackDamage, float attackSpeed, Properties properties)
    {
        return new ModAxe(material, attackDamage, attackSpeed, properties, new DefaultMaterialProperties());
    }

    public ModAxe(ModToolMaterial material, float attackDamage, float attackSpeed, Properties properties, IMaterialProperties materialProperties)
    {
        super(material, BlockTags.MINEABLE_WITH_AXE, attackDamage, attackSpeed, properties);
        this.materialProperties = materialProperties;
        this.material = material;
    }

    // copied from AxeItem
    public InteractionResult useOn(UseOnContext context)
    {
        Level level = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        Player player = context.getPlayer();
        if (playerHasBlockingItemUseIntent(context))
        {
            return InteractionResult.PASS;
        }
        else
        {
            Optional<BlockState> optional = this.evaluateNewBlockState(level, blockpos, player, level.getBlockState(blockpos), context);
            if (optional.isEmpty())
            {
                return InteractionResult.PASS;
            }
            else
            {
                ItemStack itemstack = context.getItemInHand();
                if (player instanceof ServerPlayer)
                {
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, blockpos, itemstack);
                }

                level.setBlock(blockpos, (BlockState) optional.get(), 11);
                level.gameEvent(GameEvent.BLOCK_CHANGE, blockpos, GameEvent.Context.of(player, (BlockState) optional.get()));
                if (player != null)
                {
                    var slot = context.getHand() == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
                    itemstack.hurtAndBreak(1, player, slot);
                }

                return InteractionResult.SUCCESS;
            }
        }
    }

    @Override
    public boolean isTool()
    {
        return true;
    }

    @Override
    public IMaterialProperties getMaterialProperties()
    {
        return materialProperties;
    }

    @Override
    public void setDamage(ItemStack stack, int damage)
    {
        var modifiedDamage = Mth.clamp(damage, 0, stack.getMaxDamage() - getCriticalDurability());
        stack.set(DataComponents.DAMAGE, modifiedDamage);
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state)
    {
        return !hasCriticalDamage(stack) && super.isCorrectToolForDrops(stack, state);
    }

    @Override
    public ModToolMaterial getMaterial()
    {
        return material;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state)
    {
        if (hasCriticalDamage(stack)) return 1F;
        return super.getDestroySpeed(stack, state);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility)
    {
        return !hasCriticalDamage(stack) && ItemAbilities.DEFAULT_AXE_ACTIONS.contains(itemAbility);
    }

    @Override
    public void addTooltip(ItemStack stack, TooltipContext context, ITooltip tooltip, TooltipFlag flag)
    {
        if (!utils.text.showExtendedDescription(tooltip, flag)) return;

        tooltip.ignoreEmptyLines();

        for (int i = 0; i <= 3; i++)
        {
            tooltip.addLine(getDescriptionId(), i).styledAs(TextStyle.DarkGray, i >= 1 && i <= 2).apply();
        }
    }

    private static boolean playerHasBlockingItemUseIntent(UseOnContext context)
    {
        Player player = context.getPlayer();
        return context.getHand().equals(InteractionHand.MAIN_HAND) && player.getOffhandItem().has(DataComponents.BLOCKS_ATTACKS) && !player.isSecondaryUseActive();
    }

    private Optional<BlockState> evaluateNewBlockState(Level level, BlockPos pos, @Nullable Player player, BlockState state, UseOnContext context)
    {
        Optional<BlockState> optional = Optional.ofNullable(state.getToolModifiedState(context, ItemAbilities.AXE_STRIP, false));
        if (optional.isPresent())
        {
            level.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
            return optional;
        }
        else
        {
            Optional<BlockState> optional1 = Optional.ofNullable(state.getToolModifiedState(context, ItemAbilities.AXE_SCRAPE, false));
            if (optional1.isPresent())
            {
                level.playSound(player, pos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.levelEvent(player, 3005, pos, 0);
                return optional1;
            }
            else
            {
                Optional<BlockState> optional2 = Optional.ofNullable(state.getToolModifiedState(context, ItemAbilities.AXE_WAX_OFF, false));
                if (optional2.isPresent())
                {
                    level.playSound(player, pos, SoundEvents.AXE_WAX_OFF, SoundSource.BLOCKS, 1.0F, 1.0F);
                    level.levelEvent(player, 3004, pos, 0);
                    return optional2;
                }
                else
                {
                    return Optional.empty();
                }
            }
        }
    }
}
