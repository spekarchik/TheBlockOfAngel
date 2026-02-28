package com.pekar.angelblock.events.player;

import com.pekar.angelblock.armor.ModHumanoidArmor;
import com.pekar.angelblock.armor.PlayerArmorType;
import com.pekar.angelblock.events.armor.IPlayerArmor;
import com.pekar.angelblock.events.mob.Mob;
import com.pekar.angelblock.network.packets.HoldingAngelRodPacket;
import com.pekar.angelblock.tools.ToolRegistry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public class Player extends Mob implements IPlayer
{
    private net.minecraft.world.entity.player.Player entity;
    private final Map<PlayerArmorType, IPlayerArmor> armorControllers = new EnumMap<>(PlayerArmorType.class);
    private final EnumSet<PlayerArmorType> armorInUse = EnumSet.noneOf(PlayerArmorType.class);

    public Player(net.minecraft.world.entity.player.Player entity)
    {
        this.entity = entity;
    }

    @Override
    public Iterable<IPlayerArmor> getArmorTypesUsed()
    {
        return armorInUse.stream().map(armorControllers::get).toList();
    }

    @Override
    public void updateArmorUsed()
    {
        armorInUse.clear();

        for (var armor : getSlotArmorItems())
        {
            var armorType = armor.getArmorType();
            if (armorInUse.contains(armorType)) continue;
            var armorController = armorControllers.get(armorType);
            if (armorController == null)
            {
                armorController = armorType.createController(this);
                armorControllers.put(armorType, armorController);
            }
            armorInUse.add(armorType);
        }
    }

    @Override
    public boolean isArmorElementPutOn(IPlayerArmor armor, EquipmentSlot equipmentSlot)
    {
        var itemStack = getPlayerEntity().getItemBySlot(equipmentSlot);
        if (itemStack.isEmpty()) return false;
        var item = itemStack.getItem();
        if (!(item instanceof ModHumanoidArmor armorItem) || armorItem.isBroken(itemStack)) return false;

        return armor.getArmorType() == armorItem.getArmorType();
    }

    @Override
    public boolean isFullArmorSetPutOn(IPlayerArmor armor)
    {
        var armorStacks = (List<ItemStack>) getPlayerEntity().getArmorSlots();
        return armorStacks.stream().allMatch(s -> !s.isEmpty() && s.getItem() instanceof ModHumanoidArmor a
                && !a.isBroken(s) && a.getArmorType() == armor.getArmorType());
    }

    @Override
    public boolean isAllArmorElementsPutOn(IPlayerArmor armor, EquipmentSlot... equipmentSlots)
    {
        for (var slot : equipmentSlots)
        {
            var itemStack = getPlayerEntity().getItemBySlot(slot);
            if (itemStack.isEmpty()) return false;
            var item = itemStack.getItem();
            if (!(item instanceof ModHumanoidArmor armorItem) || armorItem.isBroken(itemStack)) return false;

            if (armor.getArmorType() != armorItem.getArmorType()) return false;
        }

        return true;
    }

    @Override
    public boolean isAnyArmorElementPutOn(IPlayerArmor armor)
    {
        for (var itemStack : getPlayerEntity().getArmorSlots())
        {
            if (itemStack.isEmpty()) continue;
            var item = itemStack.getItem();
            if (!(item instanceof ModHumanoidArmor armorItem) || armorItem.isBroken(itemStack)) continue;

            if (armor.getArmorType() == armorItem.getArmorType()) return true;
        }

        return false;
    }

    @Override
    public boolean isAnyArmorElementInclBrokenPutOn(IPlayerArmor armor)
    {
        for (var itemStack : getPlayerEntity().getArmorSlots())
        {
            if (itemStack.isEmpty()) continue;
            var item = itemStack.getItem();
            if (!(item instanceof ModHumanoidArmor armorItem)) continue;

            if (armor.getArmorType() == armorItem.getArmorType()) return true;
        }

        return false;
    }

    @Override
    public boolean isHelmetModifiedWithDetector(IPlayerArmor armor)
    {
        var itemStack = getPlayerEntity().getItemBySlot(EquipmentSlot.HEAD);
        var item = itemStack.getItem();
        if (!(item instanceof ModHumanoidArmor armorItem) || armorItem.isBroken(itemStack)) return false;
        if (areDifferentType(armor, armorItem)) return false;
        return armorItem.isModifiedWithDetector();
    }

    @Override
    public boolean isHelmetModifiedWithNightVision(IPlayerArmor armor)
    {
        var itemStack = getPlayerEntity().getItemBySlot(EquipmentSlot.HEAD);
        var item = itemStack.getItem();
        if (!(item instanceof ModHumanoidArmor armorItem) || armorItem.isBroken(itemStack)) return false;
        if (areDifferentType(armor, armorItem)) return false;
        return armorItem.isModifiedWithNightVision();
    }

    @Override
    public boolean areLeggingsModifiedWithHealthRegenerator(IPlayerArmor armor)
    {
        var itemStack = getPlayerEntity().getItemBySlot(EquipmentSlot.LEGS);
        var item = itemStack.getItem();
        if (!(item instanceof ModHumanoidArmor armorItem) || armorItem.isBroken(itemStack)) return false;
        if (areDifferentType(armor, armorItem)) return false;
        return armorItem.isModifiedWithHealthRegenerator();
    }

    @Override
    public boolean areBootsModifiedWithJumpBooster(IPlayerArmor armor)
    {
        var itemStack = getPlayerEntity().getItemBySlot(EquipmentSlot.FEET);
        var item = itemStack.getItem();
        if (!(item instanceof ModHumanoidArmor armorItem) || armorItem.isBroken(itemStack)) return false;
        if (areDifferentType(armor, armorItem)) return false;
        return armorItem.isModifiedWithJumpBooster();
    }

    @Override
    public boolean isChestPlateModifiedWithStrengthBooster(IPlayerArmor armor)
    {
        var itemStack = getPlayerEntity().getItemBySlot(EquipmentSlot.CHEST);
        var item = itemStack.getItem();
        if (!(item instanceof ModHumanoidArmor armorItem) || armorItem.isBroken(itemStack)) return false;
        if (areDifferentType(armor, armorItem)) return false;
        return armorItem.isModifiedWithStrengthBooster();
    }

    @Override
    public boolean isChestPlateModifiedWithSlowFalling(IPlayerArmor armor)
    {
        var itemStack = getPlayerEntity().getItemBySlot(EquipmentSlot.CHEST);
        var item = itemStack.getItem();
        if (!(item instanceof ModHumanoidArmor armorItem) || armorItem.isBroken(itemStack)) return false;
        if (areDifferentType(armor, armorItem)) return false;
        return armorItem.isModifiedWithSlowFalling();
    }

    @Override
    public boolean isChestPlateModifiedWithLuck(IPlayerArmor armor)
    {
        var itemStack = getPlayerEntity().getItemBySlot(EquipmentSlot.CHEST);
        var item = itemStack.getItem();
        if (!(item instanceof ModHumanoidArmor armorItem) || armorItem.isBroken(itemStack)) return false;
        if (areDifferentType(armor, armorItem)) return false;
        return armorItem.isModifiedWithLuck();
    }

    @Override
    public boolean areBootsModifiedWithSeaPower(IPlayerArmor armor)
    {
        var itemStack = getPlayerEntity().getItemBySlot(EquipmentSlot.FEET);
        var item = itemStack.getItem();
        if (!(item instanceof ModHumanoidArmor armorItem) || armorItem.isBroken(itemStack)) return false;
        if (areDifferentType(armor, armorItem)) return false;
        return armorItem.isModifiedWithSeaPower();
    }

    private boolean areDifferentType(IPlayerArmor armor, ModHumanoidArmor model)
    {
        return armor.getArmorType() != model.getArmorType();
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

    private Collection<ModHumanoidArmor> getSlotArmorItems()
    {
        Iterable<ItemStack> itemStacks = entity.getArmorSlots();
        var armorItems = new ArrayList<ModHumanoidArmor>();

        for (ItemStack itemStack : itemStacks)
        {
            var item = itemStack.getItem();

            if (!(item instanceof ModHumanoidArmor armorItem)) continue;

            armorItems.add(armorItem);
        }

        return armorItems;
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
