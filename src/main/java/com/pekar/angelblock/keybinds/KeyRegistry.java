package com.pekar.angelblock.keybinds;

import com.mojang.blaze3d.platform.InputConstants;
import com.pekar.angelblock.Main;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.ClientRegistry;

public class KeyRegistry
{
    private static final String category = Main.MODNAME;
    public static final KeyMapping NIGHT_VISION = registerKey("night_vision", category, InputConstants.KEY_V);
    public static final KeyMapping JUMP_BOOST = registerKey("jump_boost", category, InputConstants.KEY_J);
    public static final KeyMapping GLOWING = registerKey("glowing", category, InputConstants.KEY_G);
    public static final KeyMapping REGENERATION = registerKey("regen", category, InputConstants.KEY_H);
    public static final KeyMapping LEVITATION = registerKey("levitation", category, InputConstants.KEY_TAB);
    public static final KeyMapping SWORD_EFFECT = registerKey("sword_effects", category, InputConstants.KEY_M);

    public static void registerKeys()
    {
    }

    private static KeyMapping registerKey(String name, String category, int keycode)
    {
        String fullName = "key." + Main.MODID + "." + name;
        var key = new KeyMapping(fullName, keycode, category);
        ClientRegistry.registerKeyBinding(key);
        return key;
    }
}
