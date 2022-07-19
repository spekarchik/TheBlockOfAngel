package com.pekar.angelblock.events.player;

import com.pekar.angelblock.armor.ModArmor;
import com.pekar.angelblock.events.armor.*;
import com.pekar.angelblock.potions.PotionUtils;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Player implements IPlayer
{
    private net.minecraft.world.entity.player.Player entity;
    private final Set<IArmor> armorInUse = new HashSet<>();
    private final Set<IArmor> allArmor = new HashSet<>();

    public Player(net.minecraft.world.entity.player.Player entity)
    {
        this.entity = entity;
        registerArmor();
    }

    @Override
    public synchronized Iterable<IArmor> getArmorTypesUsed()
    {
        return armorInUse;
    }

    @Override
    public boolean isArmorElementPutOn(String armorElementName)
    {
        Collection<String> armorNames = getSlotArmorNames();
        return armorNames.contains(armorElementName);
    }

    @Override
    public boolean isFullArmorSetPutOn(Collection<String> armorNames)
    {
        Collection<String> armorNamesPutOn = getSlotArmorNames();
        return armorNamesPutOn.containsAll(armorNames);
    }

    @Override
    public boolean isAllArmorElementsPutOn(String... armorNames)
    {
        Collection<String> armorNamesPutOn = getSlotArmorNames();
        return Arrays.stream(armorNames).allMatch(x -> armorNamesPutOn.contains(x));
    }

    @Override
    public boolean isAnyArmorElementPutOn(Collection<String> armorNames)
    {
        Collection<String> armorNamesPutOn = getSlotArmorNames();
        return armorNamesPutOn.stream().anyMatch(x -> armorNames.contains(x));
    }

    @Override
    public synchronized void updateArmorUsed()
    {
        for (IArmor armor : allArmor)
        {
            if (!isAnyArmorElementPutOn(armor.getArmorElementNames()))
            {
                armorInUse.remove(armor);
            }
            else
            {
                armorInUse.add(armor);
            }
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
        return entity.level.dimension().location().equals(Level.OVERWORLD.location());
    }

    @Override
    public boolean isNether()
    {
        return entity.level.dimension().location().equals(Level.NETHER.location());
    }

    @Override
    public boolean isEnd()
    {
        return entity.level.dimension().location().equals(Level.END.location());
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

            if (!(item instanceof ModArmor)) continue;

            var armorItem = (ModArmor) item;

            String name = armorItem.getArmorItemName();
            armorNames.add(name);
        }
        return armorNames;
    }

    private void registerArmor()
    {
        allArmor.add(new RendelithicArmor(this));
        allArmor.add(new DiamithicArmor(this));
        allArmor.add(new LapisArmor(this));
        allArmor.add(new SuperArmor(this));
        allArmor.add(new LimoniteArmor(this));
        allArmor.add(new FlyingArmor(this));
        // TODO
    }
}
