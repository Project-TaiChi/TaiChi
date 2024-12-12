package org.taichi.utils.builder;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.util.Lazy;
import org.taichi.curios.AbstractTaiCurio;
import org.taichi.curios.TaiCurioEffectContext;
import org.taichi.curios.TaiCurioEffectModifier;
import org.taichi.curios.TaiCurioEffectType;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class TaiCurioBuilder {


    private final List<Lazy<AbstractTaiCurio.EffectEntry<?>>> effects;
    private final List<AbstractTaiCurio.MobEffectEntry> mobEffects;


    public TaiCurioBuilder() {
        effects = new ArrayList<>();
        mobEffects = new ArrayList<>();
    }


    public TaiCurioBuilder withEffect(Supplier<? extends TaiCurioEffectType<?>> effect) {
        this.effects.add(Lazy.of(() -> new AbstractTaiCurio.EffectEntry<>(effect.get(), null)));
        return this;
    }

    public <T extends TaiCurioEffectContext> TaiCurioBuilder withEffect(Supplier<? extends TaiCurioEffectType<T>> effect, TaiCurioEffectModifier<T> modifier) {
        this.effects.add(Lazy.of(() -> new AbstractTaiCurio.EffectEntry<>(effect.get(), modifier)));
        return this;
    }

    public TaiCurioBuilder withMobEffect(Holder<MobEffect> effect, int amplifier) {
        this.mobEffects.add(new AbstractTaiCurio.MobEffectEntry(effect, amplifier));
        return this;
    }

    Function<ItemStack, ICurio> build() {
        Lazy<List<AbstractTaiCurio.EffectEntry<?>>> lazyEffects = Lazy.of(() -> effects.stream().map(Lazy::get).collect(Collectors.toList()));
        return stack -> new AbstractTaiCurio(stack, lazyEffects.get(), mobEffects);
    }
}
