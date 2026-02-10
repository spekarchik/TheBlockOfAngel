package com.pekar.angelblock.tools;

import com.mojang.datafixers.util.Pair;
import com.pekar.angelblock.tools.properties.DefaultMaterialProperties;
import com.pekar.angelblock.tools.properties.IMaterialProperties;
import com.pekar.angelblock.tooltip.ITooltip;
import com.pekar.angelblock.tooltip.TextStyle;
import com.pekar.angelblock.utils.SoundType;
import com.pekar.angelblock.utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class ModHoe extends ModTool implements IModToolEnhanceable
{
    private static final int USE_MAGIC_EXHAUSTION_MULTIPLIER = 16;

    protected final IMaterialProperties materialProperties;
    protected final Utils utils = new Utils();
    private final ModToolMaterial material;

    public static ModHoe createPrimary(ModToolMaterial material, float attackDamage, float attackSpeed, Properties properties)
    {
        return new ModHoe(material, attackDamage, attackSpeed, properties, new DefaultMaterialProperties());
    }

    public ModHoe(ModToolMaterial material, float attackDamage, float attackSpeed, Properties properties, IMaterialProperties materialProperties)
    {
        super(material, BlockTags.MINEABLE_WITH_HOE, attackDamage, attackSpeed, properties);
        this.materialProperties = materialProperties;
        this.material = material;
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        var result = useOnBasic(context);
        if (result == InteractionResult.FAIL) return result;

        var player = context.getPlayer();
        var level = player.level();

//        if (level.isClientSide) return result;

        var pos = context.getClickedPos();
        BlockPos upPos = pos.above();

        if ((level.isWaterAt(upPos) && !level.getBlockState(upPos).getFluidState().isSource()) || ((level.isEmptyBlock(upPos))
                && ((utils.blocks.types.isFarmTypeBlock(level, upPos.north()) && utils.blocks.types.isFarmTypeBlock(level, upPos.south()))
                || (utils.blocks.types.isFarmTypeBlock(level, upPos.east()) && utils.blocks.types.isFarmTypeBlock(level, upPos.west())))))
        {
            boolean isBrokenOrPlayerExhausted = hasCriticalDamage(context.getItemInHand()) || player.getFoodData().getFoodLevel() <= 0;

            if (!isBrokenOrPlayerExhausted)
            {
                if (!level.isClientSide())
                {
                    level.setBlock(upPos, Blocks.WATER.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
                    damageMainHandItemIfSurvivalIgnoreClient(player, level); // pos, not upPos
                    utils.player.causePlayerExhaustion(player, USE_MAGIC_EXHAUSTION_MULTIPLIER);
                }

                utils.sound.playSoundByBlock(player, pos, SoundType.WATER_PLACED);
            }

            return getToolInteractionResult(!isBrokenOrPlayerExhausted, level.isClientSide());
        }
        else
        {
            if (level.isEmptyBlock(upPos) && context.getClickedFace() == Direction.UP)
                return changePodzolToDirt(player, level, pos) ? getToolInteractionResult(true, level.isClientSide()) : result;
        }

        return result;
    }

    @Override
    public void setDamage(ItemStack stack, int damage)
    {
        var modifiedDamage = Mth.clamp(damage, 0, stack.getMaxDamage() - getCriticalDurability());
        stack.set(DataComponents.DAMAGE, modifiedDamage);
    }

    @Override
    public boolean canPerformAction(ItemInstance itemInstance, ItemAbility itemAbility)
    {
        return !hasCriticalDamage(itemInstance) && ItemAbilities.DEFAULT_HOE_ACTIONS.contains(itemAbility);
    }

    @Override
    public void addTooltip(ItemStack stack, TooltipContext context, ITooltip tooltip, TooltipFlag flag)
    {
        if (!utils.text.showExtendedDescription(tooltip, flag)) return;

        for (int i = 0; i <= 5; i++)
        {
            tooltip.addLine(getDescriptionId(), i).styledAs(TextStyle.Header, i == 1).styledAs(TextStyle.DarkGray, i == 4).apply();
        }
    }

    protected boolean changePodzolToDirt(Player player, Level level, BlockPos pos)
    {
        var blockState = level.getBlockState(pos);
        var block = blockState.getBlock();

        if (block == Blocks.PODZOL)
        {
            if (!level.isClientSide())
            {
                setBlockWithClientSound(player, pos, Blocks.DIRT);
                damageMainHandItemIfSurvivalIgnoreClient(player, level);
            }

            utils.sound.playSoundByBlock(player, pos, SoundEvents.HOE_TILL);

            return true;
        }

        return false;
    }

    @Override
    public boolean isTool()
    {
        return true;
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
    public IMaterialProperties getMaterialProperties()
    {
        return materialProperties;
    }

    // copied from HoeItem
    private InteractionResult useOnBasic(UseOnContext context)
    {
        Level level = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        BlockState toolModifiedState = level.getBlockState(blockpos).getToolModifiedState(context, ItemAbilities.HOE_TILL, false);
        Pair<Predicate<UseOnContext>, Consumer<UseOnContext>> pair = toolModifiedState == null ? null : Pair.of((Predicate) (ctx) -> true, changeIntoState(toolModifiedState));
        if (pair == null)
        {
            return InteractionResult.PASS;
        }
        else
        {
            Predicate<UseOnContext> predicate = (Predicate) pair.getFirst();
            Consumer<UseOnContext> consumer = (Consumer) pair.getSecond();
            if (predicate.test(context))
            {
                Player player = context.getPlayer();
                level.playSound(player, blockpos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                if (!level.isClientSide())
                {
                    consumer.accept(context);
                    if (player != null)
                    {
                        var slot = context.getHand() == net.minecraft.world.InteractionHand.MAIN_HAND ? net.minecraft.world.entity.EquipmentSlot.MAINHAND : net.minecraft.world.entity.EquipmentSlot.OFFHAND;
                        context.getItemInHand().hurtAndBreak(1, player, slot);
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

    // copied from HoeItem
    private static Consumer<UseOnContext> changeIntoState(BlockState state)
    {
        return (context) ->
        {
            context.getLevel().setBlock(context.getClickedPos(), state, 11);
            context.getLevel().gameEvent(GameEvent.BLOCK_CHANGE, context.getClickedPos(), GameEvent.Context.of(context.getPlayer(), state));
        };
    }
}
