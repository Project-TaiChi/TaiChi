package org.taichi.client;


import net.minecraft.world.entity.LivingEntity;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class MoonPendantBehaviorHandler {

    private static boolean _enabled = false;


    public static boolean enabled() {
        return _enabled;
    }

    public static void setEnabled(boolean enabled) {
        MoonPendantBehaviorHandler._enabled = enabled;
    }

    private static HashSet<UUID> _glowingEntities = new HashSet<>();


    public static boolean isGlowing(LivingEntity entity) {
        if (!_enabled) {
            return false;
        }
        // only available on night, use !isDay() to enable effect when on nether when it's fixed time.
        if (!entity.level().isDay()) {
            return false;
        }
        return _glowingEntities.contains(entity.getUUID());
    }


    public static void updateGlowingEntities(List<UUID> glowingEntities) {
        _glowingEntities.clear();
        if (!_enabled) {
            return;
        }
        _glowingEntities.addAll(glowingEntities);
    }
}
