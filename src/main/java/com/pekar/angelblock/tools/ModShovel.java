package com.pekar.angelblock.tools;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.pekar.angelblock.network.packets.PlaySoundPacket;
import com.pekar.angelblock.network.packets.SoundType;
import com.pekar.angelblock.tooltip.ITooltip;
import com.pekar.angelblock.tooltip.TextStyle;
import com.pekar.angelblock.utils.Utils;
import com.pekar.angelblock.tools.properties.DefaultMaterialProperties;
import com.pekar.angelblock.tools.properties.IMaterialProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

import java.util.Map;

public class ModShovel extends ModTool implements IModToolEnhanceable
{
    protected final IMaterialProperties materialProperties;
    protected final Utils utils = new Utils();
    private final ModToolMaterial material;
    protected static final Map<Block, BlockState> FLATTENABLES;

    static
    {
        FLATTENABLES = Maps.newHashMap((new ImmutableMap.Builder()).put(Blocks.GRASS_BLOCK, Blocks.DIRT_PATH.defaultBlockState()).put(Blocks.DIRT, Blocks.DIRT_PATH.defaultBlockState()).put(Blocks.PODZOL, Blocks.DIRT_PATH.defaultBlockState()).put(Blocks.COARSE_DIRT, Blocks.DIRT_PATH.defaultBlockState()).put(Blocks.MYCELIUM, Blocks.DIRT_PATH.defaultBlockState()).put(Blocks.ROOTED_DIRT, Blocks.DIRT_PATH.defaultBlockState()).build());
    }

    public static ModShovel createPrimary(ModToolMaterial material, float attackDamage, float attackSpeed, Properties properties)
    {
        return new ModShovel(material, attackDamage, attackSpeed, properties, new DefaultMaterialProperties());
    }

    public ModShovel(ModToolMaterial material, float attackDamage, float attackSpeed, Properties properties, IMaterialProperties materialProperties)
    {
        super(material, BlockTags.MINEABLE_WITH_SHOVEL, attackDamage, attackSpeed, properties);
        this.materialProperties = materialProperties;
        this.material = material;
        FLATTENABLES.put(Blocks.FARMLAND, Blocks.DIRT_PATH.defaultBlockState());
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        var player = context.getPlayer();
        var level = player.level();

        var pos = context.getClickedPos();
        BlockState blockState = level.getBlockState(pos);
        var block = blockState.getBlock();

        if (FLATTENABLES.containsKey(block) || (block instanceof CampfireBlock && blockState.getValue(CampfireBlock.LIT)))
        {
            var result = useOnBasic(context); // to prevent putting a block it needs both: return SUCCESS on client side AND return CONSUME on server side
            if (result == InteractionResult.PASS)
            {
                onBlockProcessing(player, level, pos, pos, context.getHorizontalDirection());
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    public boolean isTool()
    {
        return true;
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
        return !hasCriticalDamage(stack) && ItemAbilities.DEFAULT_SHOVEL_ACTIONS.contains(itemAbility);
    }

    @Override
    public void addTooltip(ItemStack stack, TooltipContext context, ITooltip tooltip, TooltipFlag flag)
    {
        if (!utils.text.showExtendedDescription(tooltip)) return;

        for (int i = 0; i <= 3; i++)
        {
            tooltip.addLine(getDescriptionId(), i).styledAs(TextStyle.DarkGray, i == 2).apply();
        }
    }

    @Override
    public final IMaterialProperties getMaterialProperties()
    {
        return materialProperties;
    }

    protected boolean onBlockProcessing(Player player, Level level, BlockPos originalPos, BlockPos pos, Direction facing)
    {
        if (!level.isEmptyBlock(pos.above())) return false;

        var blockState = level.getBlockState(pos);
        Block block = blockState.getBlock();

        if (FLATTENABLES.containsKey(block))
        {
            if (!level.isClientSide)
            {
                BlockState newBlockState = Blocks.DIRT_PATH.defaultBlockState();
                level.setBlock(pos, newBlockState, Block.UPDATE_ALL_IMMEDIATE);
                level.playSound(player, pos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS);

                damageMainHandItemIfSurvivalIgnoreClient(player, level);
            }
            else
            {
                level.playPlayerSound(SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1F, 1F);
            }

            return true;
        }

        return false;
    }

    private InteractionResult useOnBasic(UseOnContext context)
    {
        Level level = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        BlockState blockstate = level.getBlockState(blockpos);
        if (context.getClickedFace() == Direction.DOWN)
        {
            return InteractionResult.PASS;
        }
        else
        {
            Player player = context.getPlayer();
            BlockState blockstate1 = blockstate.getToolModifiedState(context, ItemAbilities.SHOVEL_FLATTEN, false);
            BlockState blockstate2 = null;
            if (blockstate1 != null && level.getBlockState(blockpos.above()).isAir())
            {
                level.playSound(player, blockpos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
                blockstate2 = blockstate1;
            }
            else if ((blockstate2 = blockstate.getToolModifiedState(context, ItemAbilities.SHOVEL_DOUSE, false)) != null && !level.isClientSide())
            {
                level.levelEvent((Player) null, 1009, blockpos, 0);
            }

            if (blockstate2 != null)
            {
                if (!level.isClientSide)
                {
                    level.setBlock(blockpos, blockstate2, 11);
                    level.gameEvent(GameEvent.BLOCK_CHANGE, blockpos, GameEvent.Context.of(player, blockstate2));
                    if (player != null)
                    {
                        context.getItemInHand().hurtAndBreak(1, player, LivingEntity.getSlotForHand(context.getHand()));
                    }
                }

                return InteractionResult.SUCCESS;
            }
            else
            {
                return InteractionResult.PASS;
            }
        }
    }
}
