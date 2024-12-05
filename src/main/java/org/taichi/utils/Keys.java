package org.taichi.utils;

import net.minecraft.network.chat.Component;
import org.taichi.ModConstants;

public final class Keys {

    public static Component translatable(String key) {
        return Component.translatable(ModConstants.MOD_ID + "." + key);
    }

}
