package com.pekar.angelblock.network.packets;

import com.pekar.angelblock.network.ClientToServerPacket;
import com.pekar.angelblock.network.Packet;
import com.pekar.angelblock.potions.PotionRegistry;
import com.pekar.angelblock.potions.PotionUtils;
import com.pekar.angelblock.tools.IModTool;
import com.pekar.angelblock.tools.ModSword;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;

public class ToolsModeChangePacket extends ClientToServerPacket
{
    @Override
    public void onReceive(ServerPlayer player)
    {
        boolean isAdvancedModeActive = player.hasEffect(PotionRegistry.TOOL_ADVANCED_MODE_EFFECT);
        boolean isSwordExplosionModeActive = player.hasEffect(PotionRegistry.SWORD_EXPLOSION_MODE_EFFECT);
        boolean isSwordFireModeActive = player.hasEffect(PotionRegistry.SWORD_FIRE_MODE_EFFECT);
        boolean isSwordWebModeActive = player.hasEffect(PotionRegistry.SWORD_WEB_MODE_EFFECT);
        boolean isRodMagneticModeActive = player.hasEffect(PotionRegistry.ROD_MAGNETIC_MODE_EFFECT);

        var heldItem = player.getMainHandItem().getItem();

        if (heldItem instanceof IModTool tool)
        {
            if (tool.isEnhancedTool())
            {
                if (!isAdvancedModeActive)
                {
                    var effect = new MobEffectInstance(PotionRegistry.TOOL_ADVANCED_MODE_EFFECT,
                            PotionUtils.DURATION_UNLIMITED, 0, false, true);
                    player.addEffect(effect);
                }
                else
                {
                    player.removeEffect(PotionRegistry.TOOL_ADVANCED_MODE_EFFECT);
                }
                return;
            }
            else if (tool.isEnhancedWeapon())
            {
                var sword = (ModSword)tool;

                if (isSwordWebModeActive)
                {
                    player.removeEffect(PotionRegistry.SWORD_WEB_MODE_EFFECT);

                    if (sword.hasExplosionMode())
                    {
                        var effect = new MobEffectInstance(PotionRegistry.SWORD_EXPLOSION_MODE_EFFECT,
                                PotionUtils.DURATION_UNLIMITED, 0, false, true);
                        player.addEffect(effect);
                    }
                }
                else if (isSwordExplosionModeActive)
                {
                    player.removeEffect(PotionRegistry.SWORD_EXPLOSION_MODE_EFFECT);

                    if (sword.hasFireMode())
                    {
                        var effect = new MobEffectInstance(PotionRegistry.SWORD_FIRE_MODE_EFFECT,
                                PotionUtils.DURATION_UNLIMITED, 0, false, true);
                        player.addEffect(effect);
                    }
                }
                else if (isSwordFireModeActive)
                {
                    player.removeEffect(PotionRegistry.SWORD_FIRE_MODE_EFFECT);
                }
                else if (sword.hasWebMode())
                {
                    var effect = new MobEffectInstance(PotionRegistry.SWORD_WEB_MODE_EFFECT,
                            PotionUtils.DURATION_UNLIMITED, 0, false, true);
                    player.addEffect(effect);
                }
                else if (sword.hasExplosionMode())
                {
                    var effect = new MobEffectInstance(PotionRegistry.SWORD_EXPLOSION_MODE_EFFECT,
                            PotionUtils.DURATION_UNLIMITED, 0, false, true);
                    player.addEffect(effect);
                }
                else if (sword.hasFireMode())
                {
                    var effect = new MobEffectInstance(PotionRegistry.SWORD_FIRE_MODE_EFFECT,
                            PotionUtils.DURATION_UNLIMITED, 0, false, true);
                    player.addEffect(effect);
                }

                return;
            }
            else if (tool.isEnhancedRod())
            {
                if (!isRodMagneticModeActive)
                {
                    var effect = new MobEffectInstance(PotionRegistry.ROD_MAGNETIC_MODE_EFFECT,
                            PotionUtils.DURATION_UNLIMITED, 0, false, true);
                    player.addEffect(effect);
                }
                else
                {
                    player.removeEffect(PotionRegistry.ROD_MAGNETIC_MODE_EFFECT);
                }
                return;
            }
        }

        if (isAdvancedModeActive)
            player.removeEffect(PotionRegistry.TOOL_ADVANCED_MODE_EFFECT);

        if (isSwordExplosionModeActive)
            player.removeEffect(PotionRegistry.SWORD_EXPLOSION_MODE_EFFECT);

        if (isSwordFireModeActive)
            player.removeEffect(PotionRegistry.SWORD_FIRE_MODE_EFFECT);

        if (isSwordWebModeActive)
            player.removeEffect(PotionRegistry.SWORD_WEB_MODE_EFFECT);

        if (isRodMagneticModeActive)
            player.removeEffect(PotionRegistry.ROD_MAGNETIC_MODE_EFFECT);
    }

    @Override
    public int getPacketId()
    {
        return Packets.ToolsModeChangePacketId;
    }

    @Override
    public Packet decode(FriendlyByteBuf buffer)
    {
        return new ToolsModeChangePacket();
    }
}
