package com.pekar.angelblock.mixins;

import com.pekar.angelblock.items.ItemRegistry;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.SmithingMenu;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemCombinerMenu.class)
public abstract class ItemCombinerMenuMixin
{
    // quickMoveStack is inherited by SmithingMenu, so inject into its declaring class.
    @Inject(method = "quickMoveStack", at = @At("HEAD"), cancellable = true)
    private void angelblock$preventQuickMoveDowngrade(Player player, int index, CallbackInfoReturnable<ItemStack> cir)
    {
        if ((Object)this instanceof SmithingMenu menu && index == menu.getResultSlot()
                && menu.getSlot(SmithingMenu.TEMPLATE_SLOT).getItem().is(ItemRegistry.DOWNGRADE_KIT))
        {
            cir.setReturnValue(ItemStack.EMPTY);
        }
    }
}
