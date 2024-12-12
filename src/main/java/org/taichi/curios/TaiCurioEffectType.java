package org.taichi.curios;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.util.Lazy;
import org.taichi.init.TaiCurioEffects;
import top.theillusivec4.curios.api.SlotContext;

import java.util.Objects;

public abstract class TaiCurioEffectType<T extends TaiCurioEffectContext> {
    private final boolean persist;
    private final boolean unique;

    public TaiCurioEffectType(boolean persist, boolean unique) {
        this.persist = persist;
        this.unique = unique;
    }

    public abstract T create(ItemStack stack, SlotContext curioContext);

    public boolean persist() {
        return persist;
    }

    public boolean unique() {
        return unique;
    }


    public void effectAdded(LivingEntity entity, ItemStack stack, T context) {
    }

    public void effectRemoved(LivingEntity entity, ItemStack stack, T context) {
    }

    private final Lazy<String> translationKey = Lazy.of(() -> {
        ResourceLocation location = TaiCurioEffects.REGISTRY_CURIOS_EFFECT.getKey(this);
        Objects.requireNonNull(location);
        return location.getNamespace() + ".curio_effects." + location.getPath();
    });

    public String getTranslationKey() {
        return translationKey.get();
    }


    public Component getEffectTooltip(TaiCurioEffectModifier<T> modifier) {
        if (modifier == null) {
            return Component.translatable(translationKey.get());
        }
        return Component.translatable(translationKey.get(), modifier.getDesc());
    }

}
