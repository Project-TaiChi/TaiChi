package org.taichi.attachments;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import net.minecraft.Util;
import net.minecraft.world.item.ItemStack;
import org.taichi.curios.TaiCurioEffectContext;
import org.taichi.curios.TaiCurioEffectType;
import org.taichi.init.TaiCurioEffects;
import top.theillusivec4.curios.api.SlotContext;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class EntityCurioEffectAttachment {

//    public static final Codec<EntityEffectAttachment> CODEC = Codec.unit(() -> new EntityEffectAttachment());

    public EntityCurioEffectAttachment() {
    }


    private final HashMultimap<String, TaiCurioEffectContext> effects = HashMultimap.create();


    @SuppressWarnings("unchecked")
    public <T extends TaiCurioEffectContext> List<T> findEffects(TaiCurioEffectType<T> effect) {
        String key = Util.getRegisteredName(TaiCurioEffects.REGISTRY_CURIOS_EFFECT, effect);
        return ImmutableList.copyOf((Set<T>) effects.get(key));
    }

    @SuppressWarnings("unchecked")
    public <T extends TaiCurioEffectContext> T findFirstEffect(TaiCurioEffectType<T> effect) {
        String key = Util.getRegisteredName(TaiCurioEffects.REGISTRY_CURIOS_EFFECT, effect);
        return (T) effects.get(key).stream().filter(TaiCurioEffectContext::isActivated).findAny().orElse(null);
    }


    public <T extends TaiCurioEffectContext> void addEffect(TaiCurioEffectType<T> effect, SlotContext slotContext, ItemStack stack, @Nullable Consumer<T> modifier) {

        T context = effect.create(stack, slotContext);
        if (modifier != null) {
            modifier.accept(context);
        }
        String key = Util.getRegisteredName(TaiCurioEffects.REGISTRY_CURIOS_EFFECT, effect);


        effects.put(key, context);


        if (effect.unique() && effects.containsKey(key)) {
            context.setActivated(false);
            return;
        }

        effect.effectAdded(slotContext.entity(), stack, context);
    }

    @SuppressWarnings("unchecked")
    public <T extends TaiCurioEffectContext> void removeEffect(TaiCurioEffectType<T> effect, SlotContext slotContext, ItemStack stack) {
        String key = Util.getRegisteredName(TaiCurioEffects.REGISTRY_CURIOS_EFFECT, effect);

        Set<TaiCurioEffectContext> effectContexts = effects.get(key);
        for (TaiCurioEffectContext ctx : effectContexts) {
            if (ctx.getSlotContext().index() == slotContext.index() && ctx.getSlotContext().identifier().equals(slotContext.identifier())) {
                effects.remove(key, ctx);
                effect.effectRemoved(slotContext.entity(), stack, (T) ctx);
                break;
            }
        }

        if (effect.unique()) {
            TaiCurioEffectContext nextEffect = effects.get(key).stream().findAny().orElse(null);
            if (nextEffect != null) {
                nextEffect.setActivated(true);
                effect.effectAdded(slotContext.entity(), stack, (T) nextEffect);
            }
        }
    }


}
