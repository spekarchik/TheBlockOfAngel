package com.pekar.angelblock.keybinds;

import com.mojang.blaze3d.platform.InputConstants;
import com.pekar.angelblock.Main;
import net.minecraft.client.KeyMapping;

public class KeyRegistry
{
    public static final KeyMapping NIGHT_VISION = createKeyMapping("night_vision", InputConstants.KEY_V);
    public static final KeyMapping JUMP_BOOST = createKeyMapping("jump_boost", InputConstants.KEY_C);
    public static final KeyMapping SUPER_JUMP = createKeyMapping("super_jump", InputConstants.KEY_X);
    public static final KeyMapping GLOWING = createKeyMapping("glowing", InputConstants.KEY_G);
    public static final KeyMapping REGENERATION = createKeyMapping("regen", InputConstants.KEY_H);
    public static final KeyMapping LEVITATION = createKeyMapping("levitation", InputConstants.KEY_TAB);
    public static final KeyMapping SWORD_EFFECT = createKeyMapping("sword_effects", InputConstants.KEY_R);

    private static KeyMapping createKeyMapping(String name, int keycode)
    {
        String fullName = "key." + Main.MODID + "." + name;
        return new KeyMapping(fullName, keycode, KeyMapping.Category.GAMEPLAY);
    }
}
