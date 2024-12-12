package org.taichi.client;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import org.taichi.curios.type.SimpleEffect;
import org.taichi.init.TaiCurioEffects;

import java.util.HashSet;

@EventBusSubscriber
public class SimpleEffectHandler {

    private static boolean hasMoonNightVersion = false;
    private static boolean hasDoubleJump = false;

    private static final HashSet<SimpleEffect> effects = new HashSet<>();

    public static boolean hasMoonNightVersion() {
        return hasMoonNightVersion;
    }

    public static boolean hasDoubleJump() {
        return hasDoubleJump;
    }

    public static void handle(SimpleEffect effect, boolean isAdd) {
        if (isAdd) {
            effects.add(effect);
        } else {
            effects.remove(effect);
        }

        if (effect == TaiCurioEffects.MOON_LIGHT_VISION.get()) {
            hasMoonNightVersion = isAdd;
        } else if (effect == TaiCurioEffects.DOUBLE_JUMP.get()) {
            hasDoubleJump = isAdd;
        }
    }

    @SubscribeEvent
    public static void onClientDisconnected(ClientPlayerNetworkEvent.LoggingOut event) {
        hasDoubleJump = false;
        hasMoonNightVersion = false;
        effects.clear();
    }

    public static boolean hasEffect(SimpleEffect effect) {
        return effects.contains(effect);
    }

}
