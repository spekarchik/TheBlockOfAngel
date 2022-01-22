package com.pekar.angelblock.keybinds;

import com.mojang.blaze3d.platform.InputConstants;
import com.pekar.angelblock.Main;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.ClientRegistry;

public class KeyRegistry
{
    private static final String category = Main.MODNAME;

    public static void registerKeys()
    {
        registerKey("night_vision", category, InputConstants.KEY_V);
        registerKey("jump_boost", category, InputConstants.KEY_J);
        registerKey("glowing", category, InputConstants.KEY_G);
        registerKey("regen", category, InputConstants.KEY_H);
        registerKey("levitation", category, InputConstants.KEY_TAB);
        registerKey("sword_effects", category, InputConstants.KEY_M);
    }

    private static KeyMapping registerKey(String name, String category, int keycode)
    {
        String fullName = "key." + Main.MODID + "." + name;
        var key = new KeyMapping(fullName, keycode, category);
        ClientRegistry.registerKeyBinding(key);
        return key;
    }
}
