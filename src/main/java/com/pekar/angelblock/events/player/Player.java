package com.pekar.angelblock.events.player;

import com.pekar.angelblock.armor.ModHumanoidArmor;
import com.pekar.angelblock.events.armor.*;
import com.pekar.angelblock.events.mob.Mob;
import com.pekar.angelblock.network.packets.HoldingAngelRodPacket;
import com.pekar.angelblock.tools.ToolRegistry;
import com.pekar.angelblock.utils.Utils;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Player extends Mob implements IPlayer
{
    private final IPlayerArmor rendelithicArmorModel = new RendelithicArmor(this);
    private final IPlayerArmor diamithicArmorModel = new DiamithicArmor(this);
    private final IPlayerArmor lapisArmorModel = new LapisArmor(this);
    private final IPlayerArmor superArmorModel = new SuperArmor(this);
    private final IPlayerArmor limoniteArmorModel = new LimoniteArmor(this);
    private final IPlayerArmor flyingArmorModel = new FlyingArmor(this);

    private net.minecraft.world.entity.player.Player entity;
    private final Set<IPlayerArmor> armorInUse = ConcurrentHashMap.newKeySet();

    public Player(net.minecraft.world.entity.player.Player entity)
    {
        this.entity = entity;
    }

    @Override
    public Iterable<IPlayerArmor> getArmorTypesUsed()
    {
        return armorInUse;
    }

    @Override
    public boolean isArmorElementPutOn(IArmor armor, EquipmentSlot equipmentSlot)
    {
        var itemStack = getPlayerEntity().getItemBySlot(equipmentSlot);
        if (itemStack.isEmpty()) return false;
        var item = itemStack.getItem();
        if (!(item instanceof ModHumanoidArmor armorItem) || armorItem.isBroken(itemStack)) return false;

        return armor.getFamilyName().equals(armorItem.getArmorFamilyName());
    }

    @Override
    public boolean isFullArmorSetPutOn(IArmor armor)
    {
        var armorNamesPutOn = getSlotArmorNames();
        var armorStacks = Utils.instance.player.getArmorInSlots(getPlayerEntity());
        return armorNamesPutOn.stream().allMatch(x -> x.equals(armor.getFamilyName()))
                && armorStacks.stream().allMatch(s -> !s.isEmpty() && s.getItem() instanceof ModHumanoidArmor a && !a.isBroken(s));
    }

    @Override
    public boolean isAllArmorElementsPutOn(IArmor armor, EquipmentSlot... equipmentSlots)
    {
        for (var slot : equipmentSlots)
        {
            var itemStack = getPlayerEntity().getItemBySlot(slot);
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
        for (var itemStack : Utils.instance.player.getArmorInSlots(getPlayerEntity()))
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
        for (var itemStack : Utils.instance.player.getArmorInSlots(getPlayerEntity()))
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
        var itemStack = getPlayerEntity().getItemBySlot(EquipmentSlot.HEAD);
        var item = itemStack.getItem();
        if (!(item instanceof ModHumanoidArmor armorItem) || armorItem.isBroken(itemStack)) return false;
        if (!areTheSameFamily(armor, armorItem)) return false;
        return armorItem.isModifiedWithDetector();
    }

    @Override
    public boolean isHelmetModifiedWithNightVision(IArmor armor)
    {
        var itemStack = getPlayerEntity().getItemBySlot(EquipmentSlot.HEAD);
        var item = itemStack.getItem();
        if (!(item instanceof ModHumanoidArmor armorItem) || armorItem.isBroken(itemStack)) return false;
        if (!areTheSameFamily(armor, armorItem)) return false;
        return armorItem.isModifiedWithNightVision();
    }

    @Override
    public boolean areLeggingsModifiedWithHealthRegenerator(IArmor armor)
    {
        var itemStack = getPlayerEntity().getItemBySlot(EquipmentSlot.LEGS);
        var item = itemStack.getItem();
        if (!(item instanceof ModHumanoidArmor armorItem) || armorItem.isBroken(itemStack)) return false;
        if (!areTheSameFamily(armor, armorItem)) return false;
        return armorItem.isModifiedWithHealthRegenerator();
    }

    @Override
    public boolean areBootsModifiedWithJumpBooster(IArmor armor)
    {
        var itemStack = getPlayerEntity().getItemBySlot(EquipmentSlot.FEET);
        var item = itemStack.getItem();
        if (!(item instanceof ModHumanoidArmor armorItem) || armorItem.isBroken(itemStack)) return false;
        if (!areTheSameFamily(armor, armorItem)) return false;
        return armorItem.isModifiedWithJumpBooster();
    }

    @Override
    public boolean isChestPlateModifiedWithStrengthBooster(IArmor armor)
    {
        var itemStack = getPlayerEntity().getItemBySlot(EquipmentSlot.CHEST);
        var item = itemStack.getItem();
        if (!(item instanceof ModHumanoidArmor armorItem) || armorItem.isBroken(itemStack)) return false;
        if (!areTheSameFamily(armor, armorItem)) return false;
        return armorItem.isModifiedWithStrengthBooster();
    }

    @Override
    public boolean isChestPlateModifiedWithSlowFalling(IArmor armor)
    {
        var itemStack = getPlayerEntity().getItemBySlot(EquipmentSlot.CHEST);
        var item = itemStack.getItem();
        if (!(item instanceof ModHumanoidArmor armorItem) || armorItem.isBroken(itemStack)) return false;
        if (!areTheSameFamily(armor, armorItem)) return false;
        return armorItem.isModifiedWithSlowFalling();
    }

    @Override
    public boolean isChestPlateModifiedWithLuck(IArmor armor)
    {
        var itemStack = getPlayerEntity().getItemBySlot(EquipmentSlot.CHEST);
        var item = itemStack.getItem();
        if (!(item instanceof ModHumanoidArmor armorItem) || armorItem.isBroken(itemStack)) return false;
        if (!areTheSameFamily(armor, armorItem)) return false;
        return armorItem.isModifiedWithLuck();
    }

    @Override
    public boolean areBootsModifiedWithSeaPower(IArmor armor)
    {
        var itemStack = getPlayerEntity().getItemBySlot(EquipmentSlot.FEET);
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
    public String getPlayerName()
    {
        return entity.getName().getString();
    }

    @Override
    public net.minecraft.world.entity.player.Player getPlayerEntity()
    {
        return entity;
    }

    @Override
    public LivingEntity getEntity()
    {
        return entity;
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
        Iterable<ItemStack> itemStacks = Utils.instance.player.getArmorInSlots(getPlayerEntity());
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
        Iterable<ItemStack> itemStacks = Utils.instance.player.getArmorInSlots(getPlayerEntity());
        var armorItems = new ArrayList<ModHumanoidArmor>();

        for (ItemStack itemStack : itemStacks)
        {
            var item = itemStack.getItem();

            if (!(item instanceof ModHumanoidArmor armorItem)) continue;

            armorItems.add(armorItem);
        }

        return armorItems;
    }

    private IPlayerArmor getArmorModel(ModHumanoidArmor modArmor)
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
