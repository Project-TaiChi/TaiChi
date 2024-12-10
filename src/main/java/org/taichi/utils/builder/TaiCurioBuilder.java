package org.taichi.utils.builder;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.util.Lazy;
import org.taichi.curios.AbstractTaiCurio;
import org.taichi.curios.ICuriosEffect;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class TaiCurioBuilder  {
    private List<Supplier<? extends ICuriosEffect>> effects;


    public TaiCurioBuilder() {
        effects = new ArrayList<>();
    }


    public TaiCurioBuilder withEffect(Supplier<? extends ICuriosEffect> effect) {
        this.effects.add(effect);
        return this;
    }


    Function<ItemStack, ICurio> build() {
        Lazy<List<ICuriosEffect>> lazyEffects = Lazy.of(() -> effects.stream().map(Supplier::get).collect(Collectors.toList()));
        return stack -> new AbstractTaiCurio(stack, lazyEffects.get());
    }
}
