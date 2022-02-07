package com.pekar.angelblock.events.player;

import com.pekar.angelblock.events.armor.IArmor;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;

import java.util.Collection;

public interface IPlayer
{
    Iterable<IArmor> getArmorTypesUsed();

    boolean isArmorElementPutOn(String armorElementName);
    boolean isFullArmorSetPutOn(Collection<String> armorNames);
    boolean isAllArmorElementsPutOn(String ... armorNames);
    boolean isAnyArmorElementPutOn(Collection<String> armorNames);
    void updateArmorUsed();

    boolean isEffectActive(MobEffect effect);
    void setEffect(MobEffect effect, int amplifier);
    void setEffect(MobEffect effect, int amplifier, boolean showIcon);
    void setEffect(MobEffect effect, int duration, int amplifier);
    void setEffect(MobEffect effect, int duration, int amplifier, boolean showIcon);
    void clearEffect(MobEffect effect);

    String getPlayerName();
    Player getEntity();

    boolean isOverworld();
    boolean isNether();
    boolean isEnd();

    void updateEntity(Player entity);
    void sendMessage(String message);
}
