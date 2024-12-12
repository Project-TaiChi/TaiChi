package org.taichi.curios;

import net.minecraft.core.Holder;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
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

    public record MobEffectEntry(Holder<MobEffect> effect, int amplifier) {

    }

    private final ItemStack stack;
    private final List<EffectEntry<?>> effects;
    private final List<MobEffectEntry> mobEffects;

    public AbstractTaiCurio(ItemStack stack, List<EffectEntry<?>> effects, List<MobEffectEntry> mobEffects) {
        this.stack = stack;
        this.effects = effects;
        this.mobEffects = mobEffects;
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

        for (MobEffectEntry entry : mobEffects) {
            player.addEffect(new MobEffectInstance(entry.effect, -1, entry.amplifier, true, true));
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


        for (MobEffectEntry entry : mobEffects) {
            player.removeEffect(entry.effect);
        }
    }

    private Component getMobEffectTooltip(MobEffectEntry entry) {

        MutableComponent mutablecomponent = entry.effect().value().getDisplayName().copy();
        if (entry.amplifier() >= 1 && entry.amplifier() <= 9) {
            mutablecomponent.append(CommonComponents.SPACE).append(Component.translatable("enchantment.level." + (entry.amplifier() + 1)));
        }
        return Component.translatable("tai_chi.tooltip.curio.effect", mutablecomponent);
    }

    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, Item.TooltipContext context) {
        tooltips.addAll(mobEffects.stream().map(this::getMobEffectTooltip).toList());
        tooltips.addAll(effects.stream().map(EffectEntry::getTooltip).toList());
        return tooltips;
    }
}
