package org.taichi.init;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;
import org.taichi.TaiChiMod;
import org.taichi.curios.TaiCurioEffectType;
import org.taichi.curios.type.AttackerReaction;
import org.taichi.curios.type.CuringOnDying;
import org.taichi.curios.type.SimpleEffect;

public class TaiCurioEffects {
    public static final ResourceKey<Registry<TaiCurioEffectType<?>>> KEY_CURIOS_EFFECT = ResourceKey.createRegistryKey(TaiChiMod.loc("curio_effects"));
    public static final Registry<TaiCurioEffectType<?>> REGISTRY_CURIOS_EFFECT = new RegistryBuilder<>(KEY_CURIOS_EFFECT)
            .sync(true)
            .defaultKey(TaiChiMod.loc("unknown"))
            .create();


    public static final DeferredRegister<TaiCurioEffectType<?>> CURIOS_EFFECTS = DeferredRegister.create(REGISTRY_CURIOS_EFFECT, TaiChiMod.MOD_ID);


    public static DeferredHolder<TaiCurioEffectType<?>, AttackerReaction> ATTACKER_REACTION = CURIOS_EFFECTS.register("attacker_reaction", AttackerReaction::new);
    public static DeferredHolder<TaiCurioEffectType<?>, CuringOnDying> CURING_ON_DYING = CURIOS_EFFECTS.register("curing_on_dying", CuringOnDying::new);


    public static DeferredHolder<TaiCurioEffectType<?>, SimpleEffect> MOON_LIGHT_VISION = CURIOS_EFFECTS.register("moon_light_vision", () -> new SimpleEffect(false, false, true));

//    public static DeferredHolder<T>


    public static void init(IEventBus modbus) {
        CURIOS_EFFECTS.register(modbus);
        modbus.addListener(TaiCurioEffects::registerRegistries);
    }

    private static void registerRegistries(NewRegistryEvent event) {
        event.register(REGISTRY_CURIOS_EFFECT);
    }
}
