package org.taichi.curios;


import net.minecraft.network.chat.Component;
import org.taichi.TaiChiMod;

import java.util.function.Consumer;

public interface TaiCurioEffectModifier<T extends TaiCurioEffectContext> extends Consumer<T> {
    Component getDesc();

    static String translationKey(String name) {
        return TaiChiMod.MOD_ID + ".curio_modifier." + name;
    }
}
