package com.pekar.angelblock.tools;

import com.pekar.angelblock.events.cleaners.Cleaner;
import com.pekar.angelblock.events.cleaners.TrackedBlock;
import com.pekar.angelblock.tooltip.ITooltipProvider;
import com.pekar.angelblock.utils.Utils;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

import java.util.List;

public abstract class ModSword extends Item implements IModTool, ITooltipProvider
{
    private static final int[] dx = { 3, -3, 2, 2, -2, -2, 0, 0, 1, 1, -1, -1 };
    private static final int[] dz = { 0, 0, 1, -1, 1, -1, 3, -3, 2, -2, 2, -2 };
    private static final int CactusLifeTime = 1200;
    private static final int WebLifeTime = 1200;
    private static final int TimeThreshold = 600;
    protected final Utils utils = new Utils();
    private final ModToolMaterial material;

    public ModSword(ModToolMaterial material, float attackDamage, float attackSpeed, Properties properties)
    {
        super(material.getVanillaMaterial().applySwordProperties(properties, attackDamage, attackSpeed));
        this.material = material;
    }

    public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
        return !player.isCreative();
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
    public String getMaterialName()
    {
        if (getMaterial() instanceof ModToolMaterial toolMaterial)
        {
            return toolMaterial.getName();
        }

        return "";
    }

    @Override
    public int getCriticalDurability()
    {
        return 3;
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
        return !hasCriticalDamage(stack) && ItemAbilities.DEFAULT_SWORD_ACTIONS.contains(itemAbility);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker)
    {
        if (!hasCriticalDamage(stack) && attacker instanceof ServerPlayer player && player.getFoodData().getFoodLevel() > 0)
            additionalActionOnHurtEnemy(stack, target, player);

        return true;
    }

    @Override
    public void postHurtEnemy(ItemStack itemStack, LivingEntity target, LivingEntity attacker)
    {
        itemStack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
    }

    protected void additionalActionOnHurtEnemy(ItemStack stack, LivingEntity target, ServerPlayer player)
    {
    }

    protected final void setEffectAround(Player player, InteractionHand interactionHand, Level level, BlockPos pos)
    {
        if (player.getFoodData().getFoodLevel() <= 0) return;

        var blockState = level.getBlockState(pos);

        final int posX = pos.getX(), posZ = pos.getZ();
        final int posY = blockState.is(BlockTags.REPLACEABLE)
                ? level.getBlockState(pos.below()).is(BlockTags.REPLACEABLE) ? pos.below(2).getY() : pos.below().getY()
                : pos.getY();

        for (int dx = -4; dx <= 4; dx++)
            for (int dz = -4; dz <= 4; dz++)
            {
                if (Math.abs(dx) + Math.abs(dz) < 3) continue;
                BlockPos blockPos = new BlockPos(posX + dx, posY, posZ + dz);
                processBlock(player, interactionHand, level, blockPos);
            }

        damageProperHandItemIfSurvivalIgnoreClient(player, interactionHand, level);
        causePlayerMultiEffectExhaustion(player);
    }

    protected final void setEffectAhead(Player player, InteractionHand interactionHand, Level level, BlockPos pos)
    {
        if (player.getFoodData().getFoodLevel() <= 0) return;

        var blockState = level.getBlockState(pos);

        final int posX = pos.getX(), posZ = pos.getZ();
        final int posY = blockState.is(BlockTags.REPLACEABLE)
                ? level.getBlockState(pos.below()).is(BlockTags.REPLACEABLE) ? pos.below(2).getY() : pos.below().getY()
                : pos.getY();

        final BlockPos playerPos = player.blockPosition();
        final int playerPosX = playerPos.getX(), playerPosZ = playerPos.getZ();
        int a1, a2, k, n, b1, b2;
        final int effectLength = 9, effectWidth = 4;
        final int effectHalfWidth = effectWidth / 2;

        if (Math.abs(posX - playerPosX) < 2)
        {
            a1 = -effectHalfWidth; a2 = effectHalfWidth; k = 0; n = 0;

            if (posZ > playerPosZ)
            {
                // south
                b1 = 0; b2 = effectLength;
            }
            else
            {
                // north
                b1 = -effectLength; b2 = 0;
            }
        }
        else if (posX > playerPosX)
        {
            a1 = 0; a2 = effectLength;
            if (Math.abs(posZ - playerPosZ) < 2)
            {
                // east
                b1 = -effectHalfWidth; b2 = effectHalfWidth; k = 0; n = 0;
            }
            else if (posZ > playerPosZ)
            {
                // south-east
                b1 = 0; b2 = effectLength; k = -1; n = 0;
            }
            else
            {
                // north-east
                b1 = -effectLength; b2 = 0; k = 0; n = 1;
            }
        }
        else
        {
            a1 = -effectLength; a2 = 0;
            if (Math.abs(posZ - playerPosZ) < 2)
            {
                // west
                b1 = -effectHalfWidth; b2 = effectHalfWidth; k = 0; n = 0;
            }
            else if (posZ > playerPosZ)
            {
                // south-west
                b1 = 0; b2 = effectLength; n = 0; k = 1;
            }
            else
            {
                // north-west
                b1 = -effectLength; b2 = 0; n = -1; k = 0;
            }
        }

        for (int dx = a1; dx <= a2; dx++)
            for (int dz = b1 + dx * n; dz <= b2 + dx * k; dz++)
            {
                BlockPos blockPos = new BlockPos(posX + dx, posY, posZ + dz);
                processBlock(player, interactionHand, level, blockPos);
            }

        damageProperHandItemIfSurvivalIgnoreClient(player, interactionHand, level);
        causePlayerMultiEffectExhaustion(player);
    }

    protected void causePlayerSingleEffectExhaustion(Player player)
    {
        utils.player.causePlayerExhaustion(player, 1);
    }

    protected void causePlayerMultiEffectExhaustion(Player player)
    {
        utils.player.causePlayerExhaustion(player, 4);
    }

    protected void processBlock(Player player, InteractionHand interactionHand, Level level, BlockPos pos)
    {
        // nothing by default
    }

    protected boolean allowsApplyEffect(Level level, BlockPos pos)
    {
        var blockState = level.getBlockState(pos);
        if (level.isEmptyBlock(pos) || !blockState.isSolidRender()) return false;

        var posAbove = pos.above();
        if (level.isEmptyBlock(posAbove)) return true;

        var blockStateAbove = level.getBlockState(posAbove);
        var blockStateAbove2 = level.getBlockState(posAbove.above());
        return blockStateAbove.is(BlockTags.REPLACEABLE) && !blockStateAbove.is(Blocks.WATER) && !blockStateAbove.is(Blocks.LAVA)
                && !blockStateAbove2.is(Blocks.WATER) && !blockStateAbove2.is(Blocks.LAVA);
    }

    private BlockPos calculateCorrectYPosForPlacement(Level level, BlockPos pos)
    {
        if (allowsApplyEffect(level, pos)) return pos;
        else if (allowsApplyEffect(level, pos.below())) return pos.below();
        else if (allowsApplyEffect(level, pos.above())) return pos.above();

        return null;
    }

    protected final void trySetFire(Level level, BlockPos pos)
    {
        var correctPos = calculateCorrectYPosForPlacement(level, pos);
        if (correctPos == null) return;

        if (!level.isClientSide)
        {
            level.setBlock(correctPos.above(), Blocks.FIRE.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
        }
    }

    protected final void setWeb(Player player, Level level, BlockPos pos)
    {
        if (level.isClientSide) return;
        var correctPos = calculateCorrectYPosForPlacement(level, pos.below());
        if (correctPos == null) return;

        level.setBlock(correctPos.above(), Blocks.COBWEB.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
        int increment = Utils.random.nextInt(TimeThreshold);
        var targetToRemove = new TrackedBlock(Blocks.COBWEB, correctPos.above(), player, WebLifeTime + increment, true);
        Cleaner.add(targetToRemove);

        // no need to call `damageProperHandItemIfSurvivalIgnoreClient(player, interactionHand, level);`
    }

    protected void explode(Player player, InteractionHand interactionHand, Level level, BlockPos pos)
    {
        if (player.getFoodData().getFoodLevel() <= 0) return;

        level.explode(player, pos.getX(), pos.getY() + 0.5, pos.getZ(), 0.8f, false /*fire*/, Level.ExplosionInteraction.NONE /*influences on explosion particles?*/);
        level.explode(player, pos.getX() + 0.8, pos.getY() + 0.2, pos.getZ() + 0.8, 0.8f, Level.ExplosionInteraction.NONE);
        level.explode(player, pos.getX() + 0.8, pos.getY() + 0.7, pos.getZ() - 0.8, 0.8f, Level.ExplosionInteraction.NONE);
        level.explode(player, pos.getX() - 0.8, pos.getY() + 0.4, pos.getZ() + 0.8, 0.8f, Level.ExplosionInteraction.NONE);
        level.explode(player, pos.getX() - 0.8, pos.getY() + 1.5, pos.getZ() - 0.8, 0.8f, Level.ExplosionInteraction.NONE);

        damageProperHandItemIfSurvivalIgnoreClient(player, interactionHand, level);
        causePlayerSingleEffectExhaustion(player);
    }

    protected final void plantCacti(Player player, Level level, BlockPos pos, InteractionHand interactionHand, Direction facing)
    {
        if (player.getFoodData().getFoodLevel() <= 0) return;

        final int posX = pos.getX(), posY = pos.getY(), posZ = pos.getZ();

        boolean succeeded = false;
        for (int i = 0; i < dx.length; i++)
        {
            BlockPos blockPos = new BlockPos(posX + dx[i], posY, posZ + dz[i]);
            if (tryPlantCactus(player, level, blockPos, interactionHand, facing))
                succeeded = true;
        }

        if (succeeded)
        {
            damageProperHandItemIfSurvivalIgnoreClient(player, interactionHand, level);
            causePlayerMultiEffectExhaustion(player);
        }
    }

    protected final boolean canUseToolEffect(Player player)
    {
        // We need it to prevent to fire a house when you are holding a sword with fire effect and a torch and trying to set the torch on a wall
        var mainHandItemStack = player.getMainHandItem();
        if (hasCriticalDamage(mainHandItemStack)) return false;

        var offHandItemStack = player.getOffhandItem();
        return offHandItemStack.isEmpty() || offHandItemStack.is(Items.TOTEM_OF_UNDYING);
    }

    private boolean tryPlantCactus(Player player, Level level, BlockPos pos, InteractionHand hand, Direction facing)
    {
        BlockState state = level.getBlockState(pos);
        if (!state.is(BlockTags.SAND) || !allowsApplyEffect(level, pos)
                || !level.isEmptyBlock(pos.above(2)) || !level.isEmptyBlock(pos.above(3))
            || !hasEnoughSpace(level, pos.above()))
        {
            return false;
        }

        if (!plantCactus(player, level, pos, hand, facing)) return false;
        level.setBlock(pos.above(2), Blocks.CACTUS.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
        level.setBlock(pos.above(3), Blocks.CACTUS.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);

        int increment = Utils.random.nextInt(TimeThreshold);

        var targetToRemove1 = new TrackedBlock(Blocks.CACTUS, pos.above(3), player, CactusLifeTime + increment, false);
        var targetToRemove2 = new TrackedBlock(Blocks.CACTUS, pos.above(2), player, CactusLifeTime + increment + 15, false);
        var targetToRemove3 = new TrackedBlock(Blocks.CACTUS, pos.above(), player, CactusLifeTime + increment + 30, false);
        Cleaner.add(targetToRemove1);
        Cleaner.add(targetToRemove2);
        Cleaner.add(targetToRemove3);

        return true;
    }

    private boolean hasEnoughSpace(Level level, BlockPos pos)
    {
        return level.isEmptyBlock(pos.east()) && level.isEmptyBlock(pos.west())
                && level.isEmptyBlock(pos.north()) && level.isEmptyBlock(pos.south());
    }

    private boolean plantCactus(Player player, Level level, BlockPos pos, InteractionHand hand, Direction facing)
    {
        ItemStack itemstack = player.getItemInHand(hand);
        BlockState state = level.getBlockState(pos);
        if (facing == Direction.UP
                && (allowsApplyEffect(level, pos))
                && !state.getBlock().canSustainPlant(state, level, pos, Direction.UP, Blocks.CACTUS.defaultBlockState()).isFalse() // Blocks.CACTUS.defaultBlockState() -> age == 0
                )
        {
            level.setBlock(pos.above(), Blocks.CACTUS.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);

            if (player instanceof ServerPlayer serverPlayer)
            {
                CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer) player, pos.above(), itemstack);
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    protected void damageProperHandItemIfSurvivalIgnoreClient(Player player, InteractionHand interactionHand, Level level)
    {
        if (interactionHand == InteractionHand.MAIN_HAND)
            damageMainHandItemIfSurvivalIgnoreClient(player, level);
        else
            damageOffHandItemIfSurvivalIgnoreClient(player, level);
    }

    @Override
    public final void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {
        ITooltipProvider.appendHoverText(this, stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    public boolean isWeapon()
    {
        return true;
    }

    public boolean hasExplosionMode()
    {
        return false;
    }

    public boolean hasFireMode()
    {
        return false;
    }

    public boolean hasWebMode()
    {
        return false;
    }

    @Override
    public MutableComponent getDisplayName(int lineNumber)
    {
        return Component.translatable(getDescriptionId() + ".desc" + lineNumber);
    }
}
