package com.pekar.angelblock.tools;

import com.pekar.angelblock.utils.Utils;
import com.pekar.angelblock.tools.properties.DefaultMaterialProperties;
import com.pekar.angelblock.tools.properties.IMaterialProperties;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbility;

import java.util.List;

public class ModShovel extends ShovelItem implements IModToolEnhanceable
{
    protected final IMaterialProperties materialProperties;
    protected final Utils utils = new Utils();

    public static ModShovel createPrimary(Tier material, float attackDamage, float attackSpeed, Properties properties)
    {
        return new ModShovel(material, attackDamage, attackSpeed, properties, new DefaultMaterialProperties());
    }

    public ModShovel(Tier material, float attackDamage, float attackSpeed, Properties properties, IMaterialProperties materialProperties)
    {
        super(material, properties.attributes(ShovelItem.createAttributes(material, attackDamage, attackSpeed)));
        this.materialProperties = materialProperties;
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
            return super.useOn(context); // to prevent putting a block it needs both: return SUCCESS on client side AND return CONSUME on server side
        }

        return InteractionResult.PASS;
    }

    @Override
    public boolean isTool()
    {
        return true;
    }

    @Override
    public final TieredItem getTool()
    {
        return this;
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
    public float getDestroySpeed(ItemStack stack, BlockState state)
    {
        if (hasCriticalDamage(stack)) return 1F;
        return super.getDestroySpeed(stack, state);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility)
    {
        return !hasCriticalDamage(stack) && super.canPerformAction(stack, itemAbility);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {
        for (int i = 0; i <= 0; i++)
        {
            tooltipComponents.add(getDescription(i, false));
        }
    }

    @Override
    public final IMaterialProperties getMaterialProperties()
    {
        return materialProperties;
    }
}
