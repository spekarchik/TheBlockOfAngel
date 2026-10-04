package com.pekar.angelblock.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.pekar.angelblock.items.ItemRegistry;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.SmithingMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemCombinerMenu.class)
public abstract class ItemCombinerMenuMixin
{
    // Vanilla keeps a copy of the clicked stack before transferring it. The stack
    // passed to onTake is the remainder, which is normally empty for smithing.
    @WrapOperation(method = "quickMoveStack", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/inventory/Slot;onTake(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;)V"))
    private void angelblock$takeDowngradeResult(Slot slot, Player player, ItemStack remainder,
            Operation<Void> original, @Local(ordinal = 0) ItemStack clicked)
    {
        if ((Object)this instanceof SmithingMenu menu && slot == menu.getSlot(menu.getResultSlot())
                && menu.getSlot(SmithingMenu.TEMPLATE_SLOT).getItem().is(ItemRegistry.DOWNGRADE_KIT))
        {
            // Use the actual transferred output so refunds can distinguish retained
            // modifiers/materials from removed ones. Failed transfers never reach here.
            original.call(slot, player, clicked.copyWithCount(clicked.getCount() - remainder.getCount()));
        }
        else
        {
            original.call(slot, player, remainder);
        }
    }
}
