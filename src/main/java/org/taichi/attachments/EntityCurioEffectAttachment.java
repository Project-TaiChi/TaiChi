package org.taichi.attachments;

import com.google.common.collect.HashMultimap;
import net.minecraft.Util;
import net.minecraft.world.item.ItemStack;
import org.taichi.curios.IContextCurioEffect;
import org.taichi.curios.ICuriosEffect;
import org.taichi.init.TaiCurioEffects;
import org.taichi.utils.EquippedCurio;
import top.theillusivec4.curios.api.SlotContext;

import java.util.HashMap;
import java.util.Set;

public class EntityCurioEffectAttachment {

//    public static final Codec<EntityEffectAttachment> CODEC = Codec.unit(() -> new EntityEffectAttachment());

    public EntityCurioEffectAttachment() {
    }


    private final HashMultimap<String, EquippedCurio> effects = HashMultimap.create();
    private final HashMap<String, Object> effectContexts = new HashMap<>();


    public Set<EquippedCurio> findCurios(String effect) {
        return effects.get(effect);
    }

    public Object getEffectContext(String effect) {
        return effectContexts.get(effect);
    }


    public void addEffect(ICuriosEffect effect, SlotContext slotContext, ItemStack stack) {
        EquippedCurio equippedCurio = new EquippedCurio(stack, slotContext.identifier(), slotContext.index());
        String key = Util.getRegisteredName(TaiCurioEffects.REGISTRY_CURIOS_EFFECT, effect);
        effects.put(key, equippedCurio);

        if (effect instanceof IContextCurioEffect<?> contextCurioEffect) {
            effectContexts.put(key, contextCurioEffect.createContext(slotContext, stack));
        }

        effect.effectAdded(slotContext.entity(), stack);
    }

    public void removeEffect(ICuriosEffect effect, SlotContext slotContext, ItemStack stack) {
        String key = Util.getRegisteredName(TaiCurioEffects.REGISTRY_CURIOS_EFFECT, effect);

        Set<EquippedCurio> curios = findCurios(key);

        for (EquippedCurio curio : curios) {
            if (curio.slotId() == slotContext.index() && curio.slotType().equals(slotContext.identifier())) {
                effects.remove(key, curio);
                break;
            }
        }
        if (findCurios(key).isEmpty()) {
            effectContexts.remove(key);
        }

        effect.effectRemoved(slotContext.entity(), stack);
    }


}
