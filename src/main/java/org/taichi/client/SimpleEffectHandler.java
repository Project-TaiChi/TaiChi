package org.taichi.client;

import org.taichi.curios.type.SimpleEffect;
import org.taichi.init.TaiCurioEffects;

import java.util.HashSet;

public class SimpleEffectHandler {

    private static boolean hasMoonNightVersion = false;

    private static final HashSet<SimpleEffect> effects = new HashSet<>();

    public static boolean hasMoonNightVersion() {
        return hasMoonNightVersion;
    }

    public static void handle(SimpleEffect effect, boolean isAdd) {
        if(isAdd) {
            effects.add(effect);
        } else {
            effects.remove(effect);
        }

        if(effect == TaiCurioEffects.MOON_LIGHT_VISION.get()) {
            hasMoonNightVersion = isAdd;
        }
    }
}
