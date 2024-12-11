package org.taichi.utils.builder;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.util.Lazy;
import org.taichi.curios.AbstractTaiCurio;
import org.taichi.curios.TaiCurioEffectContext;
import org.taichi.curios.TaiCurioEffectType;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class TaiCurioBuilder {

    private record EffectEntry(Supplier<? extends TaiCurioEffectType<?>> typeSupplier, Consumer<?> consumer) {

        @SuppressWarnings("unchecked")
        public <T extends TaiCurioEffectContext> AbstractTaiCurio.EffectFactory<T> create() {
            return new AbstractTaiCurio.EffectFactory<>((TaiCurioEffectType<T>) typeSupplier.get(), (Consumer<T>) consumer);
        }
    }

    private final List<EffectEntry> effects;


    public TaiCurioBuilder() {
        effects = new ArrayList<>();
    }

    public TaiCurioBuilder withEffect(Supplier<? extends TaiCurioEffectType<?>> effect) {
        this.effects.add(new EffectEntry(effect, null));
        return this;
    }

    public <T extends TaiCurioEffectContext> TaiCurioBuilder withEffect(Supplier<? extends TaiCurioEffectType<T>> effect, Consumer<T> consumer) {
        this.effects.add(new EffectEntry(effect, consumer));
        return this;
    }


    Function<ItemStack, ICurio> build() {
        Lazy<List<AbstractTaiCurio.EffectFactory<?>>> lazyEffects = Lazy.of(() -> effects.stream().map(EffectEntry::create).collect(Collectors.toList()));
        return stack -> new AbstractTaiCurio(stack, lazyEffects.get());
    }
}
