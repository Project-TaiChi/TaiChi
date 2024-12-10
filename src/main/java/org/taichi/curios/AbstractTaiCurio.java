package org.taichi.curios;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.taichi.attachments.EntityCurioEffectAttachment;
import org.taichi.init.TaiAttachments;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.List;

public class AbstractTaiCurio implements ICurio {

    private final ItemStack stack;
    private final List<ICuriosEffect> effects;

    public AbstractTaiCurio(ItemStack stack, List<ICuriosEffect> effects) {
        this.stack = stack;
        this.effects = effects;
    }


    @Override
    public ItemStack getStack() {
        return stack;
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack) {

        if (!(slotContext.entity() instanceof ServerPlayer player)) {
            return;
        }
        EntityCurioEffectAttachment data = player.getData(TaiAttachments.ENTITY_EFFECT);
        for (ICuriosEffect effect : effects) {
            data.addEffect(effect, slotContext, stack);
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack) {
        if (!(slotContext.entity() instanceof ServerPlayer player)) {
            return;
        }
        EntityCurioEffectAttachment data = player.getData(TaiAttachments.ENTITY_EFFECT);
        for (ICuriosEffect effect : effects) {
            data.removeEffect(effect, slotContext, stack);
        }
    }
}
