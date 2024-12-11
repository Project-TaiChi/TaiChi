package org.taichi.utils;

import net.minecraft.world.entity.LivingEntity;
import org.taichi.capability.TaiCurioEffectHandler;
import org.taichi.curios.TaiCurioEffectContext;
import org.taichi.curios.TaiCurioEffectType;
import org.taichi.init.TaiCapabilities;

import java.util.Collections;
import java.util.List;

public class CapabilityHelper {

    public static <T extends TaiCurioEffectContext> T findEntityEffectContext(LivingEntity entity, TaiCurioEffectType<T> effectType) {
        TaiCurioEffectHandler effect = entity.getCapability(TaiCapabilities.TAI_CURIO_EFFECT_HANDLER);
        if (effect == null)
            return null;

        return effect.findFirstEffect(effectType);
    }
    public static <T extends TaiCurioEffectContext> List<T> findEntityEffectContexts(LivingEntity entity, TaiCurioEffectType<T> effectType) {
        TaiCurioEffectHandler effect = entity.getCapability(TaiCapabilities.TAI_CURIO_EFFECT_HANDLER);
        if (effect == null)
            return Collections.emptyList();

        return effect.findEffects(effectType);
    }
}
