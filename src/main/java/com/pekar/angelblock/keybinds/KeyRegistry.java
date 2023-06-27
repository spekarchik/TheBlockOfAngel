package com.pekar.angelblock.keybinds;

import com.mojang.blaze3d.platform.InputConstants;
import com.pekar.angelblock.Main;
import net.minecraft.client.KeyMapping;

public class KeyRegistry
{
    private static final String category = Main.MODNAME;
    public static final KeyMapping NIGHT_VISION = createKeyMapping("night_vision", category, InputConstants.KEY_V);
    public static final KeyMapping JUMP_BOOST = createKeyMapping("jump_boost", category, InputConstants.KEY_J);
    public static final KeyMapping SUPER_JUMP = createKeyMapping("super_jump", category, InputConstants.KEY_X);
    public static final KeyMapping GLOWING = createKeyMapping("glowing", category, InputConstants.KEY_G);
    public static final KeyMapping REGENERATION = createKeyMapping("regen", category, InputConstants.KEY_H);
    public static final KeyMapping LEVITATION = createKeyMapping("levitation", category, InputConstants.KEY_TAB);
    public static final KeyMapping SWORD_EFFECT = createKeyMapping("sword_effects", category, InputConstants.KEY_R);

    private static KeyMapping createKeyMapping(String name, String category, int keycode)
    {
        String fullName = "key." + Main.MODID + "." + name;
        return new KeyMapping(fullName, keycode, category);
    }
}
