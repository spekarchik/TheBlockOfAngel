package com.pekar.angelblock.events.player;

import com.pekar.angelblock.armor.ModHumanoidArmor;
import com.pekar.angelblock.events.armor.*;
import com.pekar.angelblock.events.effect.ITemporaryBaseArmorEffect;
import com.pekar.angelblock.network.packets.HoldingAngelRodPacket;
import com.pekar.angelblock.tools.ToolRegistry;
import com.pekar.angelblock.utils.Utils;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Player implements IPlayer
{
    private final IArmor rendelithicArmorModel = new RendelithicArmor(this);
    private final IArmor diamithicArmorModel = new DiamithicArmor(this);
    private final IArmor lapisArmorModel = new LapisArmor(this);
    private final IArmor superArmorModel = new SuperArmor(this);
    private final IArmor limoniteArmorModel = new LimoniteArmor(this);
    private final IArmor flyingArmorModel = new FlyingArmor(this);

    private net.minecraft.world.entity.player.Player entity;
    private final Set<IArmor> armorInUse = ConcurrentHashMap.newKeySet();

    public Player(net.minecraft.world.entity.player.Player entity)
    {
        this.entity = entity;
    }

    @Override
    public Iterable<IArmor> getArmorTypesUsed()
    {
        return armorInUse;
    }

    @Override
    public boolean isArmorElementPutOn(IArmor armor, EquipmentSlot equipmentSlot)
    {
        var itemStack = getEntity().getItemBySlot(equipmentSlot);
        if (itemStack.isEmpty()) return false;
        var item = itemStack.getItem();
        if (!(item instanceof ModHumanoidArmor armorItem) || armorItem.isBroken(itemStack)) return false;

        return armor.getFamilyName().equals(armorItem.getArmorFamilyName());
    }

    @Override
    public boolean isFullArmorSetPutOn(IArmor armor)
    {
        var armorNamesPutOn = getSlotArmorNames();
        var armorStacks = Utils.instance.player.getArmorInSlots(getEntity());
        return armorNamesPutOn.stream().allMatch(x -> x.equals(armor.getFamilyName()))
                && armorStacks.stream().allMatch(s -> !s.isEmpty() && s.getItem() instanceof ModHumanoidArmor a && !a.isBroken(s));
    }

    @Override
    public boolean isAllArmorElementsPutOn(IArmor armor, EquipmentSlot... equipmentSlots)
    {
        for (var slot : equipmentSlots)
        {
            var itemStack = getEntity().getItemBySlot(slot);
            if (itemStack.isEmpty()) return false;
            var item = itemStack.getItem();
            if (!(item instanceof ModHumanoidArmor armorItem) || armorItem.isBroken(itemStack)) return false;

            if (!armor.getFamilyName().equals(armorItem.getArmorFamilyName())) return false;
        }

        return true;
    }

    @Override
    public boolean isAnyArmorElementPutOn(IArmor armor)
    {
        for (var itemStack : Utils.instance.player.getArmorInSlots(getEntity()))
        {
            if (itemStack.isEmpty()) continue;
            var item = itemStack.getItem();
            if (!(item instanceof ModHumanoidArmor armorItem) || armorItem.isBroken(itemStack)) continue;

            if (armor.getFamilyName().equals(armorItem.getArmorFamilyName())) return true;
        }

        return false;
    }

    @Override
    public boolean isAnyArmorElementInclBrokenPutOn(IArmor armor)
    {
        for (var itemStack : Utils.instance.player.getArmorInSlots(getEntity()))
        {
            if (itemStack.isEmpty()) continue;
            var item = itemStack.getItem();
            if (!(item instanceof ModHumanoidArmor armorItem)) continue;

            if (armor.getFamilyName().equals(armorItem.getArmorFamilyName())) return true;
        }

        return false;
    }

    @Override
    public boolean isHelmetModifiedWithDetector(IArmor armor)
    {
        var itemStack = getEntity().getItemBySlot(EquipmentSlot.HEAD);
        var item = itemStack.getItem();
        if (!(item instanceof ModHumanoidArmor armorItem) || armorItem.isBroken(itemStack)) return false;
        if (!areTheSameFamily(armor, armorItem)) return false;
        return armorItem.isModifiedWithDetector();
    }

    @Override
    public boolean isHelmetModifiedWithNightVision(IArmor armor)
    {
        var itemStack = getEntity().getItemBySlot(EquipmentSlot.HEAD);
        var item = itemStack.getItem();
        if (!(item instanceof ModHumanoidArmor armorItem) || armorItem.isBroken(itemStack)) return false;
        if (!areTheSameFamily(armor, armorItem)) return false;
        return armorItem.isModifiedWithNightVision();
    }

    @Override
    public boolean areLeggingsModifiedWithHealthRegenerator(IArmor armor)
    {
        var itemStack = getEntity().getItemBySlot(EquipmentSlot.LEGS);
        var item = itemStack.getItem();
        if (!(item instanceof ModHumanoidArmor armorItem) || armorItem.isBroken(itemStack)) return false;
        if (!areTheSameFamily(armor, armorItem)) return false;
        return armorItem.isModifiedWithHealthRegenerator();
    }

    @Override
    public boolean areBootsModifiedWithJumpBooster(IArmor armor)
    {
        var itemStack = getEntity().getItemBySlot(EquipmentSlot.FEET);
        var item = itemStack.getItem();
        if (!(item instanceof ModHumanoidArmor armorItem) || armorItem.isBroken(itemStack)) return false;
        if (!areTheSameFamily(armor, armorItem)) return false;
        return armorItem.isModifiedWithJumpBooster();
    }

    @Override
    public boolean isChestPlateModifiedWithStrengthBooster(IArmor armor)
    {
        var itemStack = getEntity().getItemBySlot(EquipmentSlot.CHEST);
        var item = itemStack.getItem();
        if (!(item instanceof ModHumanoidArmor armorItem) || armorItem.isBroken(itemStack)) return false;
        if (!areTheSameFamily(armor, armorItem)) return false;
        return armorItem.isModifiedWithStrengthBooster();
    }

    @Override
    public boolean isChestPlateModifiedWithSlowFalling(IArmor armor)
    {
        var itemStack = getEntity().getItemBySlot(EquipmentSlot.CHEST);
        var item = itemStack.getItem();
        if (!(item instanceof ModHumanoidArmor armorItem) || armorItem.isBroken(itemStack)) return false;
        if (!areTheSameFamily(armor, armorItem)) return false;
        return armorItem.isModifiedWithSlowFalling();
    }

    @Override
    public boolean isChestPlateModifiedWithLuck(IArmor armor)
    {
        var itemStack = getEntity().getItemBySlot(EquipmentSlot.CHEST);
        var item = itemStack.getItem();
        if (!(item instanceof ModHumanoidArmor armorItem) || armorItem.isBroken(itemStack)) return false;
        if (!areTheSameFamily(armor, armorItem)) return false;
        return armorItem.isModifiedWithLuck();
    }

    @Override
    public boolean areBootsModifiedWithSeaPower(IArmor armor)
    {
        var itemStack = getEntity().getItemBySlot(EquipmentSlot.FEET);
        var item = itemStack.getItem();
        if (!(item instanceof ModHumanoidArmor armorItem) || armorItem.isBroken(itemStack)) return false;
        if (!areTheSameFamily(armor, armorItem)) return false;
        return armorItem.isModifiedWithSeaPower();
    }

    private boolean areTheSameFamily(IArmor armor, ModHumanoidArmor model)
    {
        return model.getArmorFamilyName().equals(armor.getFamilyName());
    }

    @Override
    public void updateArmorUsed()
    {
        armorInUse.clear();

        for (var armor : getSlotArmorItems())
        {
            var armorModel = getArmorModel(armor);
            if (armorModel != null)
                armorInUse.add(armorModel);
        }
    }

    @Override
    public boolean isEffectActive(Holder<MobEffect> effect)
    {
        return entity.hasEffect(effect);
    }

    @Override
    public boolean hasArmorEffect(Holder<MobEffect> effect)
    {
        var effectInstance = entity.getEffect(effect);
        if (effectInstance instanceof ModMobEffectInstance modMobEffectInstance && modMobEffectInstance.isMagicItemEffect())
            return false;

        return effectInstance != null && (!effectInstance.isVisible() || effectInstance.isInfiniteDuration());
    }

    @Override
    public boolean hasAnotherEffect(Holder<MobEffect> effect)
    {
        var effectInstance = entity.getEffect(effect);
        return effectInstance != null && effectInstance.isVisible();
    }

    @Override
    public IModMobEffectInstance setEffect(Holder<MobEffect> effect, int amplifier)
    {
        return setEffect(effect, MobEffectInstance.INFINITE_DURATION, amplifier);
    }

    @Override
    public IModMobEffectInstance setEffect(Holder<MobEffect> effect, int amplifier, boolean showIcon)
    {
        return setEffect(effect, MobEffectInstance.INFINITE_DURATION, amplifier, showIcon);
    }

    @Override
    public IModMobEffectInstance setEffect(Holder<MobEffect> effect, int duration, int amplifier)
    {
        return setEffect(effect, duration, amplifier, false);
    }

    @Override
    public IModMobEffectInstance setEffect(Holder<MobEffect> effect, int duration, int amplifier, boolean showIcon)
    {
        var effectInstance = new ModMobEffectInstance(effect, duration, amplifier, false /*ambient*/, false /*visible*/, showIcon, false);
        entity.addEffect(effectInstance);
        return effectInstance;
    }

    @Override
    public IModMobEffectInstance setMagicItemEffect(Holder<MobEffect> effect, int duration, int amplifier, boolean showIcon)
    {
        var effectInstance = new ModMobEffectInstance(effect, duration, amplifier, true /*ambient*/, true /*visible*/, showIcon, true);
        entity.addEffect(effectInstance);
        return effectInstance;
    }

    @Override
    public IModMobEffectInstance setEffect(ITemporaryBaseArmorEffect armorEffect, int duration, int amplifier)
    {
        return setEffect(armorEffect, duration, amplifier, false);
    }

    @Override
    public IModMobEffectInstance setEffect(ITemporaryBaseArmorEffect armorEffect, int duration, int amplifier, boolean showIcon)
    {
        var effectInstance = new ModMobEffectInstance(armorEffect.getEffect(), duration, amplifier, false /*ambient*/, false /*visible*/, showIcon /*showIcon*/,
                false, armorEffect::onDurationEnd);
        entity.addEffect(effectInstance);
        return effectInstance;
    }

    @Override
    public MobEffectInstance getEffectInstance(Holder<MobEffect> effect)
    {
        return getEntity().getEffect(effect);
    }

    @Override
    public void clearEffect(Holder<MobEffect> effect)
    {
        entity.removeEffect(effect);
    }

    @Override
    public String getPlayerName()
    {
        return entity.getName().getString();
    }

    @Override
    public net.minecraft.world.entity.player.Player getEntity()
    {
        return entity;
    }

    @Override
    public boolean isOverworld()
    {
        return entity.level().dimension().identifier().equals(Level.OVERWORLD.identifier());
    }

    @Override
    public boolean isNether()
    {
        return entity.level().dimension().identifier().equals(Level.NETHER.identifier());
    }

    @Override
    public boolean isEnd()
    {
        return entity.level().dimension().identifier().equals(Level.END.identifier());
    }

    @Override
    public void updateEntity(net.minecraft.world.entity.player.Player entity)
    {
        this.entity = entity;
    }

    @Override
    public void sendMessage(String message)
    {
        System.out.println(message);
        //entity.asChatSender().write(new FriendlyByteBuf(new EmptyByteBuf()));
    }

    @Override
    public void onClientTick()
    {
        if (generatePacketIfHoldsAngel(entity.getMainHandItem())) return;
        generatePacketIfHoldsAngel(entity.getOffhandItem());
    }

    private Collection<String> getSlotArmorNames()
    {
        Iterable<ItemStack> itemStacks = Utils.instance.player.getArmorInSlots(getEntity());
        Set<String> armorNames = new HashSet<>();

        for (ItemStack itemStack : itemStacks)
        {
            var item = itemStack.getItem();

            if (item instanceof ModHumanoidArmor armorItem)
            {
                String name = armorItem.getArmorFamilyName();
                armorNames.add(name);
            }
            else
            {
                armorNames.add(item.getName(itemStack).getString());
            }
        }

        return armorNames;
    }

    private Collection<ModHumanoidArmor> getSlotArmorItems()
    {
        Iterable<ItemStack> itemStacks = Utils.instance.player.getArmorInSlots(getEntity());
        var armorItems = new ArrayList<ModHumanoidArmor>();

        for (ItemStack itemStack : itemStacks)
        {
            var item = itemStack.getItem();

            if (!(item instanceof ModHumanoidArmor armorItem)) continue;

            armorItems.add(armorItem);
        }

        return armorItems;
    }

    private IArmor getArmorModel(ModHumanoidArmor modArmor)
    {
        var modelName = modArmor.getArmorFamilyName();

        if (modelName.equals(rendelithicArmorModel.getFamilyName()))
        {
            return rendelithicArmorModel;
        }
        else if (modelName.equals(diamithicArmorModel.getFamilyName()))
        {
            return diamithicArmorModel;
        }
        else if (modelName.equals(lapisArmorModel.getFamilyName()))
        {
            return lapisArmorModel;
        }
        else if (modelName.equals(superArmorModel.getFamilyName()))
        {
            return superArmorModel;
        }
        else if (modelName.equals(limoniteArmorModel.getFamilyName()))
        {
            return limoniteArmorModel;
        }
        else if (modelName.equals(flyingArmorModel.getFamilyName()))
        {
            return flyingArmorModel;
        }
        else
        {
            return null;
        }
    }

    private boolean generatePacketIfHoldsAngel(ItemStack handItemStack)
    {
        if (handItemStack.isEmpty()) return false;

        var item = handItemStack.getItem();
        if (item != ToolRegistry.ANGEL_ROD.get()) return false;

        new HoldingAngelRodPacket().sendToServer();
        return true;
    }
}
