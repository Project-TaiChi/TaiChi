package org.taichi.curios;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.taichi.attachments.EntityCurioEffectAttachment;
import org.taichi.init.TaiAttachments;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class AbstractTaiCurio implements ICurio {

    public record EffectFactory<T extends TaiCurioEffectContext>(TaiCurioEffectType<T> effect, Consumer<T> consumer) {
    }

    private final ItemStack stack;
    private final List<EffectFactory<?>> effects;

    public AbstractTaiCurio(ItemStack stack, List<EffectFactory<?>> effects) {
        this.stack = stack;
        this.effects = effects;
    }


    @Override
    public ItemStack getStack() {
        return stack;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onEquip(SlotContext slotContext, ItemStack prevStack) {

        if (!(slotContext.entity() instanceof ServerPlayer player)) {
            return;
        }
        EntityCurioEffectAttachment data = player.getData(TaiAttachments.ENTITY_EFFECT);
        for (EffectFactory effect : effects) {
            data.addEffect(effect.effect(), slotContext, stack, effect.consumer());
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack) {
        if (!(slotContext.entity() instanceof ServerPlayer player)) {
            return;
        }
        EntityCurioEffectAttachment data = player.getData(TaiAttachments.ENTITY_EFFECT);
        for (EffectFactory<?> effect : effects) {
            data.removeEffect(effect.effect(), slotContext, stack);
        }
    }
}
