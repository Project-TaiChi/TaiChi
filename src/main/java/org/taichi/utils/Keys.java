package org.taichi.utils;

import net.minecraft.network.chat.Component;
import org.taichi.TaiChiMod;

public final class Keys {

    public static Component translatable(String key) {
        return Component.translatable(TaiChiMod.MOD_ID + "." + key);
    }

}
