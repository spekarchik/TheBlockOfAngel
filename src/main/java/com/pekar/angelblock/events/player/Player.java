package com.pekar.angelblock.events.player;

import com.pekar.angelblock.armor.ModArmor;
import com.pekar.angelblock.events.armor.*;
import com.pekar.angelblock.potions.PotionUtils;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.*;

public class Player implements IPlayer
{
    private final IArmor rendelithicArmorModel = new RendelithicArmor(this);
    private final IArmor diamithicArmorModel = new DiamithicArmor(this);
    private final IArmor lapisArmorModel = new LapisArmor(this);
    private final IArmor superArmorModel = new SuperArmor(this);
    private final IArmor limoniteArmorModel = new LimoniteArmor(this);
    private final IArmor flyingArmorModel = new FlyingArmor(this);

    private net.minecraft.world.entity.player.Player entity;
    private final Set<EquipmentSlot> equipmentSlots = new HashSet<>();
    private final Set<IArmor> armorInUse = new HashSet<>();

    public Player(net.minecraft.world.entity.player.Player entity)
    {
        this.entity = entity;
        equipmentSlots.add(EquipmentSlot.FEET);
        equipmentSlots.add(EquipmentSlot.LEGS);
        equipmentSlots.add(EquipmentSlot.CHEST);
        equipmentSlots.add(EquipmentSlot.HEAD);
    }

    @Override
    public synchronized Iterable<IArmor> getArmorTypesUsed()
    {
        return armorInUse;
    }

    @Override
    public boolean isArmorElementPutOn(IArmor armor, EquipmentSlot equipmentSlot)
    {
        var itemStack = getEntity().getItemBySlot(equipmentSlot);
        if (itemStack.isEmpty()) return false;
        var item = itemStack.getItem();
        if (!(item instanceof ModArmor armorItem)) return false;

        return armor.getModelName().equals(armorItem.getArmorModelName());
    }

    @Override
    public boolean isFullArmorSetPutOn(IArmor armor)
    {
        var armorNamesPutOn = getSlotArmorNames();
        return armorNamesPutOn.stream().allMatch(x -> x.equals(armor.getModelName()));
    }

    @Override
    public boolean isAllArmorElementsPutOn(IArmor armor, EquipmentSlot... equipmentSlots)
    {
        for (var slot : equipmentSlots)
        {
            var itemStack = getEntity().getItemBySlot(slot);
            if (itemStack.isEmpty()) return false;
            var item = itemStack.getItem();
            if (!(item instanceof ModArmor armorItem)) return false;

            if (!armor.getModelName().equals(armorItem.getArmorModelName())) return false;
        }

        return true;
    }

    @Override
    public boolean isAnyArmorElementPutOn(IArmor armor)
    {
        for (var slot : equipmentSlots)
        {
            var itemStack = getEntity().getItemBySlot(slot);
            if (itemStack.isEmpty()) continue;
            var item = itemStack.getItem();
            if (!(item instanceof ModArmor armorItem)) continue;

            if (armor.getModelName().equals(armorItem.getArmorModelName())) return true;
        }

        return false;
    }

    @Override
    public boolean isArmorModifiedWithDetector(IArmor armor)
    {
        var item = getEntity().getItemBySlot(EquipmentSlot.HEAD).getItem();
        if (!(item instanceof ModArmor armorItem)) return false;
        if (!armorItem.getArmorModelName().equals(armor.getModelName())) return false;
        return armorItem.isModifiedWithDetector();
    }

    @Override
    public boolean isArmorModifiedWithHealthRegenerator(IArmor armor)
    {
        var item = getEntity().getItemBySlot(EquipmentSlot.LEGS).getItem();
        if (!(item instanceof ModArmor armorItem)) return false;
        if (!armorItem.getArmorModelName().equals(armor.getModelName())) return false;
        return armorItem.isModifiedWithHealthRegenerator();
    }

    @Override
    public boolean areBootsModifiedWithStrengthBooster(IArmor armor)
    {
        var item = getEntity().getItemBySlot(EquipmentSlot.FEET).getItem();
        if (!(item instanceof ModArmor armorItem)) return false;
        if (!armorItem.getArmorModelName().equals(armor.getModelName())) return false;
        return armorItem.isModifiedWithStrengthBooster();
    }

    @Override
    public boolean isChestPlateModifiedWithStrengthBooster(IArmor armor)
    {
        var item = getEntity().getItemBySlot(EquipmentSlot.CHEST).getItem();
        if (!(item instanceof ModArmor armorItem)) return false;
        if (!armorItem.getArmorModelName().equals(armor.getModelName())) return false;
        return armorItem.isModifiedWithStrengthBooster();
    }

    @Override
    public boolean isArmorModifiedWithLevitation(IArmor armor)
    {
        var item = getEntity().getItemBySlot(EquipmentSlot.CHEST).getItem();
        if (!(item instanceof ModArmor armorItem)) return false;
        if (!armorItem.getArmorModelName().equals(armor.getModelName())) return false;
        return armorItem.isModifiedWithLevitation();
    }

    @Override
    public boolean isChestPlateModifiedWithSeaPower(IArmor armor)
    {
        var item = getEntity().getItemBySlot(EquipmentSlot.CHEST).getItem();
        if (!(item instanceof ModArmor armorItem)) return false;
        if (!armorItem.getArmorModelName().equals(armor.getModelName())) return false;
        return armorItem.isModifiedWithSeaPower();
    }

    @Override
    public boolean areBootsModifiedWithSeaPower(IArmor armor)
    {
        var item = getEntity().getItemBySlot(EquipmentSlot.FEET).getItem();
        if (!(item instanceof ModArmor armorItem)) return false;
        if (!armorItem.getArmorModelName().equals(armor.getModelName())) return false;
        return armorItem.isModifiedWithSeaPower();
    }

    @Override
    public synchronized void updateArmorUsed()
    {
        armorInUse.clear();

        for (var armor : getSlotArmorItems())
        {
            armorInUse.add(getArmorModel(armor));
        }
    }

    @Override
    public boolean isEffectActive(MobEffect effect)
    {
        return entity.hasEffect(effect);
    }

    @Override
    public void setEffect(MobEffect effect, int amplifier)
    {
        setEffect(effect, PotionUtils.DURATION_UNLIMITED, amplifier);
    }

    @Override
    public void setEffect(MobEffect effect, int amplifier, boolean showIcon)
    {
        setEffect(effect, PotionUtils.DURATION_UNLIMITED, amplifier, showIcon);
    }

    @Override
    public void setEffect(MobEffect effect, int duration, int amplifier)
    {
        entity.addEffect(new MobEffectInstance(effect, duration, amplifier, false /*ambient*/, false /*visible*/, false /*showIcon*/));
    }

    @Override
    public void setEffect(MobEffect effect, int duration, int amplifier, boolean showIcon)
    {
        entity.addEffect(new MobEffectInstance(effect, duration, amplifier, false /*ambient*/, false /*visible*/, showIcon /*showIcon*/));
    }

    @Override
    public void clearEffect(MobEffect effect)
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
        return entity.level().dimension().location().equals(Level.OVERWORLD.location());
    }

    @Override
    public boolean isNether()
    {
        return entity.level().dimension().location().equals(Level.NETHER.location());
    }

    @Override
    public boolean isEnd()
    {
        return entity.level().dimension().location().equals(Level.END.location());
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

    private Collection<String> getSlotArmorNames()
    {
        Iterable<ItemStack> itemStacks = entity.getArmorSlots();
        Set<String> armorNames = new HashSet<>();

        for (ItemStack itemStack : itemStacks)
        {
            var item = itemStack.getItem();

            if (item instanceof ModArmor armorItem)
            {
                String name = armorItem.getArmorModelName();
                armorNames.add(name);
            }
            else
            {
                armorNames.add(item.getName(itemStack).getString());
            }
        }

        return armorNames;
    }

    private Collection<ModArmor> getSlotArmorItems()
    {
        Iterable<ItemStack> itemStacks = entity.getArmorSlots();
        var armorItems = new ArrayList<ModArmor>();

        for (ItemStack itemStack : itemStacks)
        {
            var item = itemStack.getItem();

            if (!(item instanceof ModArmor armorItem)) continue;

            armorItems.add(armorItem);
        }

        return armorItems;
    }

    private IArmor getArmorModel(ModArmor modArmor)
    {
        var modelName = modArmor.getArmorModelName();

        if (modelName.equals(rendelithicArmorModel.getModelName()))
        {
            return rendelithicArmorModel;
        }
        else if (modelName.equals(diamithicArmorModel.getModelName()))
        {
            return diamithicArmorModel;
        }
        else if (modelName.equals(lapisArmorModel.getModelName()))
        {
            return lapisArmorModel;
        }
        else if (modelName.equals(superArmorModel.getModelName()))
        {
            return superArmorModel;
        }
        else if (modelName.equals(limoniteArmorModel.getModelName()))
        {
            return limoniteArmorModel;
        }
        else if (modelName.equals(flyingArmorModel.getModelName()))
        {
            return flyingArmorModel;
        }
        else
        {
            return null;
        }
    }
}
