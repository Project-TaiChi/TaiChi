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


    public static boolean hasMoonNightVersion() {
        return hasMoonNightVersion;
    }

    public static boolean hasDoubleJump() {
        return hasDoubleJump;
    }

    public static void handle(SimpleEffect effect, boolean isAdd) {

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
    }

}
