package org.taichi.curios;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.taichi.attachments.EntityCurioEffectAttachment;
import org.taichi.init.TaiAttachments;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.List;

public class AbstractTaiCurio implements ICurio {

    public record EffectEntry<T extends TaiCurioEffectContext>(TaiCurioEffectType<T> effect,
                                                               TaiCurioEffectModifier<T> modifier) {
        public Component getTooltip() {
            return effect.getEffectTooltip(modifier);
        }
    }

    private final ItemStack stack;
    private final List<EffectEntry<?>> effects;

    public AbstractTaiCurio(ItemStack stack, List<EffectEntry<?>> effects) {
        this.stack = stack;
        this.effects = effects;
    }


    @Override
    public ItemStack getStack() {
        return stack;
    }

    private <T extends TaiCurioEffectContext> void addEffectToEntity(EntityCurioEffectAttachment data, EffectEntry<T> entry, SlotContext slotContext, ItemStack prevStack) {
        data.addEffect(entry.effect(), slotContext, stack, entry.modifier());
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack) {

        if (!(slotContext.entity() instanceof ServerPlayer player)) {
            return;
        }
        EntityCurioEffectAttachment data = player.getData(TaiAttachments.ENTITY_EFFECT);
        for (EffectEntry<?> effect : effects) {
            addEffectToEntity(data, effect, slotContext, prevStack);
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack) {
        if (!(slotContext.entity() instanceof ServerPlayer player)) {
            return;
        }
        EntityCurioEffectAttachment data = player.getData(TaiAttachments.ENTITY_EFFECT);
        for (EffectEntry<?> effect : effects) {
            data.removeEffect(effect.effect(), slotContext, stack);
        }
    }

    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, Item.TooltipContext context) {
        tooltips.addAll(effects.stream().map(EffectEntry::getTooltip).toList());
        return tooltips;
    }
}
