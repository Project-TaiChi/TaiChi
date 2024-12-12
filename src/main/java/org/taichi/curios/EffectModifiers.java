package org.taichi.curios;

import org.taichi.curios.modifier.DamageAwareEffectModifier;

public class EffectModifiers {

    public static DamageAwareEffectModifier damageType() {
        return new DamageAwareEffectModifier();
    }
}
