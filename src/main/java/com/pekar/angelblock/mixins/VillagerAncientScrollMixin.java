package com.pekar.angelblock.mixins;

import com.pekar.angelblock.events.VillagerAncientScrollEvents;
import com.pekar.angelblock.items.ItemRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Villager.class)
public abstract class VillagerAncientScrollMixin extends AbstractVillager
{
    protected VillagerAncientScrollMixin(EntityType<? extends AbstractVillager> entityType, Level level)
    {
        super(entityType, level);
    }

    @Inject(method = "wantsToPickUp", at = @At("HEAD"), cancellable = true)
    private void wantsToPickUpAncientScroll(ServerLevel level, ItemStack stack, CallbackInfoReturnable<Boolean> cir)
    {
        if (stack.is(ItemRegistry.ANCIENT_SCROLL.get()))
        {
            cir.setReturnValue(VillagerAncientScrollEvents.canStartReading((Villager)(Object)this));
        }
    }

    @Inject(method = "pickUpItem", at = @At("HEAD"), cancellable = true)
    private void pickUpAncientScroll(ServerLevel level, ItemEntity itemEntity, CallbackInfo ci)
    {
        ItemStack droppedStack = itemEntity.getItem();
        Villager villager = (Villager)(Object)this;
        if (!droppedStack.is(ItemRegistry.ANCIENT_SCROLL.get())) return;

        // Never let an ancient scroll fall through to Villager's normal inventory pickup.
        // Its state can change between the item sensor choosing the scroll and this collision
        // callback (for example, another behavior can temporarily occupy its main hand).
        ci.cancel();
        if (!VillagerAncientScrollEvents.canStartReading(villager)) return;

        ItemStack scroll = droppedStack.copyWithCount(1);
        this.onItemPickup(itemEntity);
        this.take(itemEntity, 1);

        droppedStack.shrink(1);
        if (droppedStack.isEmpty())
        {
            itemEntity.discard();
        }

        this.setItemSlot(EquipmentSlot.MAINHAND, scroll);
        VillagerAncientScrollEvents.beginReading(villager, scroll);
    }
}
